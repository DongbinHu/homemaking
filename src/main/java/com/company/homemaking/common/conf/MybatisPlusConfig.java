package com.company.homemaking.common.conf;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 胡东斌
 * @create 2020-04-03
 */
@Configuration
@MapperScan({"com.vontown.marketing.consumer.dao","com.vontown.marketing.business.dao"})
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
