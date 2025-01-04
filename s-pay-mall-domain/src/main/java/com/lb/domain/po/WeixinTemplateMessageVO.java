package com.lb.domain.po;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信模板消息
 */
public class WeixinTemplateMessageVO {
    // 接收者的OpenID
    private String touser = "or0Ab6ivwmypESVp_bYuk92T6SvU";
    // 模板ID
    private String template_id = "GLlAM-Q4jdgsktdNd35hnEbHVam2mwsW2YWuxDhpQkU";
    // 模板跳转链接
    private String url = "https://weixin.qq.com";
    // 模板数据
    private Map<String, Map<String, String>> data = new HashMap<>();

    // 构造函数
    public WeixinTemplateMessageVO(String touser, String template_id) {
        this.touser = touser;
        this.template_id = template_id;
    }

    // 添加模板数据
    public void put(TemplateKey key, String value) {
        data.put(key.getCode(), new HashMap<String, String>() {
            private static final long serialVersionUID = 7092338402387318563L;

            {
                put("value", value);
            }
        });
    }

    // 静态方法添加模板数据
    public static void put(Map<String, Map<String, String>> data, TemplateKey key, String value) {
        data.put(key.getCode(), new HashMap<String, String>() {
            private static final long serialVersionUID = 7092338402387318563L;

            {
                put("value", value);
            }
        });
    }

    // 模板键枚举
    public enum TemplateKey {
        USER("user", "用户ID");

        private String code;
        private String desc;

        TemplateKey(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    // 获取接收者的OpenID
    public String getTouser() {
        return touser;
    }

    // 设置接收者的OpenID
    public void setTouser(String touser) {
        this.touser = touser;
    }

    // 获取模板ID
    public String getTemplate_id() {
        return template_id;
    }

    // 设置模板ID
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    // 获取模板跳转链接
    public String getUrl() {
        return url;
    }

    // 设置模板跳转链接
    public void setUrl(String url) {
        this.url = url;
    }

    // 获取模板数据
    public Map<String, Map<String, String>> getData() {
        return data;
    }

    // 设置模板数据
    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }
}