package com.lb.config;

import com.lb.service.weixin.IWeixinApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Slf4j  // 使用Lombok注解自动注入日志对象
@Configuration  // 标识该类为配置类
public class Retrofit2Config {

    // 定义微信API的基础URL
    private static final String BASE_URL = "https://api.weixin.qq.com/";

    /**
     * 配置Retrofit客户端
     *
     * @return 配置好的Retrofit实例
     */
    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)  // 设置基础URL
                .addConverterFactory(JacksonConverterFactory.create())  // 添加Jackson转换器工厂
                .build();  // 构建Retrofit实例
    }

    /**
     * 创建微信API服务接口
     *
     * @param retrofit Retrofit实例
     * @return 配置好的微信API服务接口实例
     */
    @Bean
    public IWeixinApiService weixinApiService(Retrofit retrofit) {
        return retrofit.create(IWeixinApiService.class);  // 使用Retrofit创建微信API服务接口实例
    }

}
