package com.company.homemaking.consumer.service.impl;

import com.company.homemaking.consumer.entity.ServiceItem;
import com.company.homemaking.consumer.dao.ServiceItemMapper;
import com.company.homemaking.consumer.service.ServiceItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务项目表 服务实现类
 * </p>
 *
 * @author liubangzi
 * @since 2020-06-01
 */
@Service
public class ServiceItemServiceImpl extends ServiceImpl<ServiceItemMapper, ServiceItem> implements ServiceItemService {

}
