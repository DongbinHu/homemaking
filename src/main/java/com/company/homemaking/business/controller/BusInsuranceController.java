package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.BusInsurance;
import com.company.homemaking.business.entity.BusWorker;
import com.company.homemaking.business.service.BusInsuranceService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.vo.order.InsuranceQueryVO;
import com.company.homemaking.business.vo.worker.WorkerQueryVO;
import com.company.homemaking.common.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * <p>
 * 保险表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-28
 */
@Api(description="保险合同接口")
@Controller
@RequestMapping("/business/insurance")
public class BusInsuranceController {

    @Autowired
    BusInsuranceService insuranceService;

    //按条件查找保险合同
    @ApiOperation(value = "按条件查找保险合同", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated InsuranceQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusInsurance> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusInsurance> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(vo.getCustomerName())){
            queryWrapper.like(BusInsurance::getCustomerName, vo.getCustomerName());
        }
        if(vo.getOrderCode() != null){
            queryWrapper.eq(BusInsurance::getOrderCode, vo.getOrderCode());
        }
        if(vo.getOrderId() != null){
            queryWrapper.eq(BusInsurance::getFkOrderId, vo.getOrderId());
        }
        if(StringUtils.isNotBlank(vo.getWorkerName())){
            queryWrapper.like(BusInsurance::getWorkerName, vo.getWorkerName());
        }
        queryWrapper.eq(BusInsurance::getIfDelete,false);
        IPage<BusInsurance> itemIPage = insuranceService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",itemIPage.getRecords());
        map.put("total",itemIPage.getTotal());
        map.put("pageNum",itemIPage.getCurrent());
        map.put("pageSize",itemIPage.getSize());
        return JSONResult.ok(map);
    }

}
