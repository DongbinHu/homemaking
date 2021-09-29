package com.company.homemaking.consumer.service.impl;

import com.company.homemaking.consumer.entity.CustomerAddress;
import com.company.homemaking.consumer.dao.CustomerAddressMapper;
import com.company.homemaking.consumer.service.CustomerAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Service
public class CustomerAddressServiceImpl extends ServiceImpl<CustomerAddressMapper, CustomerAddress> implements CustomerAddressService {

}
