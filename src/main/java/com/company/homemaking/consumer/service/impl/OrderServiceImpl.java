package com.company.homemaking.consumer.service.impl;

import com.company.homemaking.consumer.entity.Order;
import com.company.homemaking.consumer.dao.OrderMapper;
import com.company.homemaking.consumer.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
