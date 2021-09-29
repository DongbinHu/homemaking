package com.company.homemaking.consumer.service.impl;

import com.company.homemaking.common.utils.FastJsonConvert;
import com.company.homemaking.consumer.entity.Customer;
import com.company.homemaking.consumer.dao.CustomerMapper;
import com.company.homemaking.consumer.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.homemaking.consumer.service.JedisClient;
import com.company.homemaking.consumer.vo.user.UserForRedis;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Slf4j
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Autowired
    JedisClient jedisClient;

    @Value("${redisKey.prefix.user_session}")
    private String USER_SESSION;
    @Value("${redisKey.expire_time}")
    private Integer EXPIRE_TIME;
    @Value("${redisKey.custom_time}")
    private Integer CUSTOM_TIME;
    @Override
    public boolean timeDelay(String token) {

        String key = USER_SESSION + token;
        try {

            long ttlTime = jedisClient.ttl(key);
            String user = jedisClient.get(key);
            if (StringUtils.isNotBlank(user)) {

                if (ttlTime < CUSTOM_TIME) {
                    jedisClient.expire(key, CUSTOM_TIME);
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Redis服务出错");
        }
        return false;
    }


    @Override
    public UserForRedis getUserInfo(String token) {

        try {
            String userRedisStr = jedisClient.get(USER_SESSION + token);
            if (StringUtils.isNotBlank(userRedisStr)) {
                return FastJsonConvert.convertJSONToObject(userRedisStr, UserForRedis.class);
            }
        } catch (Exception e) {
            log.error("Redis取数据出错");
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
