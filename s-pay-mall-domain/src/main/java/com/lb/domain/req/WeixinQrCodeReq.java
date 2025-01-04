package com.lb.domain.req;

import lombok.*;

/**
 * 获取微信登录二维码请求对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeixinQrCodeReq {
    /**
     * 二维码链接的有效时间，单位是秒
     */
    private int expire_seconds;

    /**
     * 二维码操作类型
     */
    private String action_name;

    /**
     * 二维码操作信息
     */
    private ActionInfo action_info;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActionInfo {
        /**
         * 场景值信息
         */
        Scene scene;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Scene {
            /**
             * 场景值ID
             */
            int scene_id;

            /**
             * 场景值描述
             */
            String scene_str;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ActionNameTypeVO {
        QR_SCENE("QR_SCENE", "临时的整型参数值"),
        QR_STR_SCENE("QR_STR_SCENE", "临时的字符串参数值"),
        QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久的整型参数值"),
        QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久的字符串参数值");

        private String code;
        private String info;
    }
}
