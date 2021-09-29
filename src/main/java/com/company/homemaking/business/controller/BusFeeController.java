package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.BusFee;
import com.company.homemaking.business.entity.BusSaleProject;
import com.company.homemaking.business.service.BusFeeService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.vo.fee.FeeQueryVO;
import com.company.homemaking.business.vo.saleproject.ProjectQueryVO;
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
 * 收费表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Api(description="收款单接口")
@Controller
@RequestMapping("/business/fee")
public class BusFeeController {

    @Autowired
    BusFeeService feeService;


    //按条件查找收款单
    @ApiOperation(value = "按条件查找收款单", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated FeeQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusFee> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusFee> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(vo.getOrderCode())){
            queryWrapper.eq(BusFee::getOrderCode, vo.getOrderCode());
        }
        queryWrapper.eq(BusFee::getIfDelete,false);
        IPage<BusFee> itemIPage = feeService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",itemIPage.getRecords());
        map.put("total",itemIPage.getTotal());
        map.put("pageNum",itemIPage.getCurrent());
        map.put("pageSize",itemIPage.getSize());
        return JSONResult.ok(map);
    }

}
