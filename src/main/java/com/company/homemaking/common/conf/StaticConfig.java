package com.company.homemaking.common.conf;

import com.company.homemaking.common.wx.utils.WechatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 胡东斌
 * @create 2020-04-24
 */

@Configuration
public class StaticConfig {
    //获取微信参数
    @Value("${wechat.app_id}")
    private String appId;
    @Value("${wechat.app_secret}")
    private String appSecret;
    @Value("${wechat.template_id}")
    private String templateId;
    @Value("${wechat.public_app_id}")
    private String publicAppId;
    @Value("${wechat.public_app_secret}")
    private String publicAppSecret;
    @Value("${redisKey.prefix.user_session}")
    private String userSession;

    @Bean
    public int initStatic() {
        WechatUtils.setAppId(appId);
        WechatUtils.setAppSecret(appSecret);
        WechatUtils.setTemplateId(templateId);
        WechatUtils.setPublicAppId(publicAppId);
        WechatUtils.setPublicAppSecret(publicAppSecret);
        WechatUtils.setUserSession(userSession);
        return 0;
    }
}