package com.company.homemaking.consumer.service;

import com.company.homemaking.consumer.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.homemaking.consumer.vo.user.UserForRedis;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
public interface CustomerService extends IService<Customer> {


    boolean timeDelay(String token);

    //从redis中获取不完整的userInfo
    UserForRedis getUserInfo(String token);
}
