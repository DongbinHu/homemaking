package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.BusCustomer;
import com.company.homemaking.business.service.BusCustomerService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.business.vo.customer.CustomerDetailVO;
import com.company.homemaking.business.vo.customer.CustomerQueryVO;
import com.company.homemaking.business.vo.customer.CustomerVO;
import com.company.homemaking.common.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Api(description="用户接口")
@Controller
@RequestMapping("/business/customer")
public class BusCustomerController {


    @Autowired
    BusCustomerService customerService;

    //添加用户
    @ApiOperation(value = "添加用户", notes = "")
    @RequestMapping(value = "/addItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult addItem(@RequestBody @Validated CustomerVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusCustomer item = new BusCustomer();
        BeanUtils.copyProperties(vo, item);
        String encodedPassword = DigestUtils.md5DigestAsHex(("123456" + item.getPhoneNumber()).getBytes());
        item.setPassword(encodedPassword);
        boolean result = customerService.save(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("添加用户失败");
    }

    //删除用户
    @ApiOperation(value = "删除用户", notes = "")
    @RequestMapping(value = "/deleteItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult deleteItem(@RequestParam(name = "id") Integer id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        BusCustomer item = new BusCustomer();
        item.setId(id);
        item.setIfDelete(true);
        boolean result = customerService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("删除用户失败");
    }

    //修改用户资料、状态
    @ApiOperation(value = "修改用户资料、状态", notes = "")
    @RequestMapping(value = "/editItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editItem(@RequestBody @Validated({GroupEdit.class, Default.class}) CustomerVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusCustomer item = new BusCustomer();
        BeanUtils.copyProperties(vo, item);
        BusCustomer oldItem = customerService.getById(vo.getId());
        if(!oldItem.getPhoneNumber().equals(vo.getPhoneNumber())){
            String encodedPassword = DigestUtils.md5DigestAsHex(("123456" + item.getPhoneNumber()).getBytes());
            item.setPassword(encodedPassword);
        }
        boolean result = customerService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("修改失败");
    }

    //查询用户列表
    @ApiOperation(value = "查询用户列表", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated CustomerQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusCustomer> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusCustomer> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(vo.getPhoneNumber())){
            queryWrapper.like(BusCustomer::getPhoneNumber, vo.getPhoneNumber());
        }
        if(StringUtils.isNotBlank(vo.getTrueName())){
            queryWrapper.like(BusCustomer::getTrueName, vo.getTrueName());
        }
        if(vo.getStatus() != null){
            queryWrapper.eq(BusCustomer::getStatus, vo.getStatus());
        }
        if(vo.getStartDate() != null){
            LocalDateTime endDate = LocalDateTime.now();
            if(vo.getEndDate() != null){
                endDate = vo.getEndDate();
            }
            if(vo.getStartDate().isAfter(endDate)){
                return JSONResult.errorMsg("开始时间不能在结束时间之后");
            }
            queryWrapper.between(BusCustomer::getRegisterDate, vo.getStartDate(),endDate);
        }
        queryWrapper.eq(BusCustomer::getIfDelete,false);
        IPage<BusCustomer> iPage = customerService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",iPage.getRecords());
        map.put("total",iPage.getTotal());
        map.put("pageNum",iPage.getCurrent());
        map.put("pageSize",iPage.getSize());
        return JSONResult.ok(map);
    }

    //获取用户详情
    @ApiOperation(value = "获取用户详情", notes = "")
    @RequestMapping(value = "/getItemDetail", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemDetail(@RequestParam(name = "id") Integer id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        BusCustomer item = customerService.getById(id);
        if(item == null || item.getIfDelete()){
            return JSONResult.errorMsg("没有此用户");
        }
        CustomerDetailVO vo = new CustomerDetailVO();
        BeanUtils.copyProperties(item, vo);
        return JSONResult.ok(vo);
    }

}
