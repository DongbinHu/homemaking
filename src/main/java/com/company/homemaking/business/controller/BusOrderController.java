package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.*;
import com.company.homemaking.business.service.BusOrderDetailService;
import com.company.homemaking.business.service.BusOrderFeedbackService;
import com.company.homemaking.business.service.BusOrderLogService;
import com.company.homemaking.business.service.BusOrderService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.vo.order.OrderDetailVO;
import com.company.homemaking.business.vo.order.OrderQueryVO;
import com.company.homemaking.common.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-26
 */

@Api(description="订单接口")
@Controller
@RequestMapping("/business/order")
public class BusOrderController {


    @Autowired
    BusOrderService orderService;
    @Autowired
    BusOrderDetailService orderDetailService;
    @Autowired
    BusOrderLogService orderLogService;
    @Autowired
    BusOrderFeedbackService orderFeedbackService;


    //按条件查找订单
    @ApiOperation(value = "按条件查找订单", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated OrderQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusOrder> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusOrder> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(vo.getCustomerName())){
            queryWrapper.like(BusOrder::getCustomerName, vo.getCustomerName());
        }
        if(StringUtils.isNotBlank(vo.getOrderCode())){
            queryWrapper.eq(BusOrder::getOrderCode, vo.getOrderCode());
        }
        if(StringUtils.isNotBlank(vo.getWorkerName())){
            queryWrapper.like(BusOrder::getWorkerName, vo.getWorkerName());
        }
        if(vo.getStatus() != null){
            queryWrapper.eq(BusOrder::getStatus, vo.getStatus());
        }
        queryWrapper.eq(BusOrder::getIfDelete,false);
        queryWrapper.select(BusOrder.class, info -> !info.getColumn().equals("if_delete"));
        IPage<BusOrder> itemIPage = orderService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",itemIPage.getRecords());
        map.put("total",itemIPage.getTotal());
        map.put("pageNum",itemIPage.getCurrent());
        map.put("pageSize",itemIPage.getSize());
        return JSONResult.ok(map);
    }

    //获取订单详情
    @ApiOperation(value = "获取订单详情", notes = "")
    @RequestMapping(value = "/getItemDetail", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemDetail(@RequestParam(name = "id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        BusOrder item = orderService.getById(id);
        OrderDetailVO vo = new OrderDetailVO();
        if(item == null || item.getIfDelete()){
            return JSONResult.errorMsg("没有此订单");
        }
        //订单信息
        BeanUtils.copyProperties(item, vo);
        map.put("detail",vo);

        //订单服务项目列表
        LambdaQueryWrapper<BusOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusOrderDetail::getFkOrderId,id);
        List<BusOrderDetail> services = orderDetailService.list(queryWrapper);
        map.put("serviceList",services);
        //订单日志列表
        LambdaQueryWrapper<BusOrderLog> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(BusOrderLog::getFkOrderId,id);
        List<BusOrderLog> logs = orderLogService.list(queryWrapper2);
        map.put("logs",logs);
        //订单评价列表
        LambdaQueryWrapper<BusOrderFeedback> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(BusOrderFeedback::getFkOrderId,id);
        List<BusOrderFeedback> feedbacks = orderFeedbackService.list(queryWrapper3);
        map.put("feedbacks",feedbacks);

        return JSONResult.ok(map);
    }
}
