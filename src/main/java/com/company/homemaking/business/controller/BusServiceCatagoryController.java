package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.BusServiceCatagory;
import com.company.homemaking.business.service.BusServiceCatagoryService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.business.vo.servicecat.CatChildQueryVO;
import com.company.homemaking.business.vo.servicecat.CatParentQueryVO;
import com.company.homemaking.business.vo.servicecat.CatSelectQueryVO;
import com.company.homemaking.business.vo.servicecat.CategoryVO;
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
import java.util.List;

/**
 * <p>
 * 服务项目分类表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-26
 */

@Api(description="服务项目分类接口")
@Controller
@RequestMapping("/business/serviceCategory")
public class BusServiceCatagoryController {

    @Autowired
    BusServiceCatagoryService catagoryService;


    //获取一级类别列表
    @ApiOperation(value = "获取一级类别列表", notes = "")
    @RequestMapping(value = "/getParentCatList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getGoodsCat1(@RequestBody @Validated CatParentQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if (!validResult.isOK()) {
            return validResult;
        }
        Page<BusServiceCatagory> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusServiceCatagory> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(vo.getCatName())) {
            queryWrapper.like(BusServiceCatagory::getName, vo.getCatName());
        }
        queryWrapper.eq(BusServiceCatagory::getLevel, 0);
        queryWrapper.eq(BusServiceCatagory::getPid, 0);
        queryWrapper.eq(BusServiceCatagory::getIfDelete, false);
        IPage<BusServiceCatagory> itemIPage = catagoryService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", itemIPage.getRecords());
        map.put("total", itemIPage.getTotal());
        map.put("pageNum", itemIPage.getCurrent());
        map.put("pageSize", itemIPage.getSize());
        return JSONResult.ok(map);
    }

    //获取下级类别列表
    @ApiOperation(value = "获取二级列表", notes = "必须传父级类别的id")
    @RequestMapping(value = "/getChildCatList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getChildCat(@RequestBody @Validated CatChildQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if (!validResult.isOK()) {
            return validResult;
        }
        Page<BusServiceCatagory> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusServiceCatagory> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(vo.getCatName())) {
            queryWrapper.like(BusServiceCatagory::getName, vo.getCatName());
        }
        queryWrapper.eq(BusServiceCatagory::getPid, vo.getPid());
        queryWrapper.eq(BusServiceCatagory::getLevel, 1);
        queryWrapper.eq(BusServiceCatagory::getIfDelete, false);
        IPage<BusServiceCatagory> itemIPage = catagoryService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", itemIPage.getRecords());
        map.put("total", itemIPage.getTotal());
        map.put("pageNum", itemIPage.getCurrent());
        map.put("pageSize", itemIPage.getSize());
        return JSONResult.ok(map);
    }

    //获取服务类别下拉选
    @ApiOperation(value = "获取服务类别下拉选", notes = "本接口根据接收的参数返回对应的服务类别，可返回1级类别和2级类别；无论返回哪种，请务必指定catLevel（类别等级：0或1）和pid（父级类别的Id，如果是一级类别，pid请填0）")
    @RequestMapping(value = "/getCatSelect", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getCatSelect(@RequestBody @Validated CatSelectQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if (!validResult.isOK()) {
            return validResult;
        }
        LambdaQueryWrapper<BusServiceCatagory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusServiceCatagory::getPid, vo.getPid());
        queryWrapper.eq(BusServiceCatagory::getLevel, vo.getCatLevel());
        queryWrapper.eq(BusServiceCatagory::getIfDelete, false);
        List<BusServiceCatagory> list = catagoryService.list(queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", list);
        return JSONResult.ok(map);
    }

    //添加类别
    @ApiOperation(value = "添加类别", notes = "本接口可添加一级类别和二级类别，添加一级类别时只需要catName；添加二级类别时需要传输pid（一级类别ID）、catLevel(1)、catName(类别名称)")
    @RequestMapping(value = "/addItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult addItem(@RequestBody CategoryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusServiceCatagory item = new BusServiceCatagory();
        BeanUtils.copyProperties(vo, item);
        boolean result = catagoryService.save(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("添加失败");
    }


    //删除类别
    @ApiOperation(value = "删除类别", notes = "")
    @RequestMapping(value = "/deleteItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult deleteItem(@RequestParam(name = "id") Integer id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        LambdaQueryWrapper<BusServiceCatagory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusServiceCatagory::getPid, id);
        queryWrapper.eq(BusServiceCatagory::getIfDelete, false);
        int count = catagoryService.count(queryWrapper);
        if(count > 0){
            return JSONResult.errorMsg("该类别存在有效下级，不允许删除");
        }
        BusServiceCatagory item = new BusServiceCatagory();
        item.setId(id);
        item.setIfDelete(true);
        boolean result = catagoryService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("删除失败");
    }

    //修改类别
    @ApiOperation(value = "修改类别", notes = "只允许修改类别名称和状态")
    @RequestMapping(value = "/editItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editItem(@RequestBody @Validated({GroupEdit.class, Default.class}) CategoryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        vo.setLevel(null);
        vo.setPid(null);
        BusServiceCatagory item = new BusServiceCatagory();
        BeanUtils.copyProperties(vo, item);
        boolean result = catagoryService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("修改失败");
    }

    //获取类别详情
    @ApiOperation(value = "获取类别详情", notes = "通过类别id获取详情，供修改接口使用")
    @RequestMapping(value = "/category/getCatDetail", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getCatDetail(@RequestParam(name="id") String id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        HashMap<String, Object> map = new HashMap<>();
        BusServiceCatagory item = catagoryService.getById(id);
        CategoryVO detail = new CategoryVO();
        if(item == null || item.getIfDelete()){
            return JSONResult.errorMsg("参数错误");
        }
        //基本信息
        BeanUtils.copyProperties(item, detail);
        map.put("detail", detail);
        return JSONResult.ok(map);
    }
}
