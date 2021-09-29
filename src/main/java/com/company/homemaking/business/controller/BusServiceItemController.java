package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.BusServiceItem;
import com.company.homemaking.business.service.BusServiceItemService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.business.vo.serviceitem.ItemQueryVO;
import com.company.homemaking.business.vo.serviceitem.ItemVO;
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

import javax.validation.groups.Default;
import java.util.HashMap;

/**
 * <p>
 * 服务项目表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-26
 */
@Api(description="服务项目接口")
@Controller
@RequestMapping("/business/serviceItem")
public class BusServiceItemController {

    @Autowired
    BusServiceItemService itemService;

    //添加服务项目
    @ApiOperation(value = "添加服务项目", notes = "")
    @RequestMapping(value = "/addItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult addItem(@RequestBody @Validated ItemVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusServiceItem item = new BusServiceItem();
        BeanUtils.copyProperties(vo, item);
        boolean result = itemService.save(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("添加失败");
    }

    //修改服务项目
    @ApiOperation(value = "修改服务项目", notes = "")
    @RequestMapping(value = "/editItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editItem(@RequestBody @Validated({GroupEdit.class, Default.class}) ItemVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusServiceItem item = new BusServiceItem();
        BeanUtils.copyProperties(vo, item);
        boolean result = itemService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("修改失败");
    }

    //删除服务项目
    @ApiOperation(value = "删除服务项目", notes = "")
    @RequestMapping(value = "/deleteItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult deleteItem(@RequestParam(name = "id") Integer id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        BusServiceItem item = new BusServiceItem();
        item.setId(id);
        item.setIfDelete(true);
        boolean result = itemService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("删除失败");
    }

    //获取服务项目列表
    @ApiOperation(value = "获取服务项目列表", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated ItemQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusServiceItem> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusServiceItem> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(vo.getItemName())){
            queryWrapper.like(BusServiceItem::getName, vo.getItemName());
        }
        if(StringUtils.isNotBlank(vo.getCatType())){
            queryWrapper.eq(BusServiceItem::getCataType, vo.getCatType());
        }
        if(vo.getCatId() != null){
            queryWrapper.eq(BusServiceItem::getSubType, vo.getCatId());
        }
        queryWrapper.eq(BusServiceItem::getIfDelete,false);
        IPage<BusServiceItem> itemIPage = itemService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",itemIPage.getRecords());
        map.put("total",itemIPage.getTotal());
        map.put("pageNum",itemIPage.getCurrent());
        map.put("pageSize",itemIPage.getSize());
        return JSONResult.ok(map);
    }

}
