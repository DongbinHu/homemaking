package com.company.homemaking.consumer.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class FilterConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginFilter loginFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginFilter).addPathPatterns("/consumer/**").excludePathPatterns("/consumer/wechat/login","/consumer/index/**","/consumer/pay/notify");
    }
}
