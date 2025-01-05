package com.lb.service.impl;

import com.google.common.cache.Cache;
import com.lb.domain.vo.WeixinTemplateMessageVO;
import com.lb.domain.req.WeixinQrCodeReq;
import com.lb.domain.res.WeixinQrCodeRes;
import com.lb.domain.res.WeixinTokenRes;
import com.lb.service.ILoginService;
import com.lb.service.weixin.IWeixinApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信登录服务实现类
 */
@Service
public class WeixinLoginServiceImpl implements ILoginService {

    /**
     * 从配置文件中读取微信应用的App ID
     */
    @Value("${weixin.config.app-id}")
    private String appid;

    /**
     * 从配置文件中读取微信应用的App Secret
     */
    @Value("${weixin.config.app-secret}")
    private String appSecret;

    /**
     * 从配置文件中读取微信模板消息的模板ID
     */
    @Value("${weixin.config.template_id}")
    private String template_id;

    /**
     * 注入微信访问令牌缓存
     */
    @Resource
    private Cache<String, String> weixinAccessToken;

    /**
     * 注入微信API服务接口
     */
    @Resource
    private IWeixinApiService weixinApiService;

    /**
     * 注入OpenID缓存
     */
    @Resource
    private Cache<String, String> openidToken;

    /**
     * 创建二维码票据
     *
     * @return String 二维码票据
     * @throws Exception 异常
     */
    @Override
    public String createQrCodeTicket() throws Exception {
        // 1. 获取 accessToken
        // 从缓存中获取accessToken，如果不存在则调用API获取
        String accessToken = weixinAccessToken.getIfPresent(appid);
        if (null == accessToken) {
            Call<WeixinTokenRes> call = weixinApiService.getToken("client_credential", appid, appSecret);
            WeixinTokenRes weixinTokenRes = call.execute().body();
            assert weixinTokenRes != null;
            accessToken = weixinTokenRes.getAccess_token();
            weixinAccessToken.put(appid, accessToken);
        }

        // 2. 生成 ticket
        // 构建生成二维码票据的请求参数
        WeixinQrCodeReq weixinQrCodeReq = WeixinQrCodeReq.builder()
                .expire_seconds(2592000)
                .action_name(WeixinQrCodeReq.ActionNameTypeVO.QR_SCENE.getCode())
                .action_info(WeixinQrCodeReq.ActionInfo.builder()
                        .scene(WeixinQrCodeReq.ActionInfo.Scene.builder()
                                .scene_id(100601)
                                .build())
                        .build())
                .build();

        // 调用API生成二维码票据
        Call<WeixinQrCodeRes> call = weixinApiService.createQrCode(accessToken, weixinQrCodeReq);
        WeixinQrCodeRes weixinQrCodeRes = call.execute().body();
        assert null != weixinQrCodeRes;
        return weixinQrCodeRes.getTicket();
    }

    /**
     * 检查登录
     *
     * @param ticket 票据
     * @return String openid
     */
    @Override
    public String checkLogin(String ticket) {
        // 从缓存中获取对应的openid
        return openidToken.getIfPresent(ticket);
    }

    /**
     * 保存登录状态
     *
     * @param ticket 票据
     * @param openid openid
     * @throws IOException IO异常
     */
    @Override
    public void saveLoginState(String ticket, String openid) throws IOException {
        // 将票据和openid存入缓存
        openidToken.put(ticket, openid);

        // 1. 获取 accessToken 【实际业务场景，按需处理下异常】
        // 从缓存中获取accessToken，如果不存在则调用API获取
        String accessToken = weixinAccessToken.getIfPresent(appid);
        if (null == accessToken) {
            Call<WeixinTokenRes> call = weixinApiService.getToken("client_credential", appid, appSecret);
            WeixinTokenRes weixinTokenRes = call.execute().body();
            assert weixinTokenRes != null;
            accessToken = weixinTokenRes.getAccess_token();
            weixinAccessToken.put(appid, accessToken);
        }

        // 2. 发送模板消息
        // 构建模板消息的数据
        Map<String, Map<String, String>> data = new HashMap<>();
        WeixinTemplateMessageVO.put(data, WeixinTemplateMessageVO.TemplateKey.USER, openid);

        // 构建模板消息对象
        WeixinTemplateMessageVO templateMessageDTO = new WeixinTemplateMessageVO(openid, template_id);
        templateMessageDTO.setUrl("https://www.bilibili.com/");
        templateMessageDTO.setData(data);

        // 调用API发送模板消息
        Call<Void> call = weixinApiService.sendMessage(accessToken, templateMessageDTO);
        call.execute();
    }
}
