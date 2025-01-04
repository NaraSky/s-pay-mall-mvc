package com.lb.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GuavaConfig {

    /**
     * 配置微信访问令牌缓存
     *
     * @return 返回一个Cache对象，用于缓存微信访问令牌
     */
    @Bean(name = "weixinAccessToken")
    public Cache<String, String> weixinAccessToken() {
        // 使用Guava的CacheBuilder创建一个新的Cache实例
        // 设置写入后两小时过期
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build(); // 构建并返回Cache对象
    }

    /**
     * 配置用户OpenID缓存
     *
     * @return 返回一个Cache对象，用于缓存用户的OpenID
     */
    @Bean(name = "openidToken")
    public Cache<String, String> openidToken() {
        // 使用Guava的CacheBuilder创建一个新的Cache实例
        // 设置写入后一小时过期
        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build(); // 构建并返回Cache对象
    }

}
