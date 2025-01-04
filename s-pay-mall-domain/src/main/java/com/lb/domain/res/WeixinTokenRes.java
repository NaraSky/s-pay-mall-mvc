package com.lb.domain.res;

import lombok.Data;

/**
 * 获取 Access token DTO 对象
 */
@Data
public class WeixinTokenRes {
    // 访问令牌
    private String access_token;

    // 访问令牌的有效期，单位为秒
    private int expires_in;

    // 错误码
    private String errcode;

    // 错误信息
    private String errmsg;
}
