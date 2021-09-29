package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.*;
import com.company.homemaking.business.service.BusSaleProjectDetailService;
import com.company.homemaking.business.service.BusSaleProjectService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.business.vo.saleproject.DetailVO;
import com.company.homemaking.business.vo.saleproject.ProjectDetailVO;
import com.company.homemaking.business.vo.saleproject.ProjectQueryVO;
import com.company.homemaking.business.vo.saleproject.ProjectVO;
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
 *  前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Api(description="销售项目接口")
@Controller
@RequestMapping("/business/saleProject")
public class BusSaleProjectController {

    @Autowired
    BusSaleProjectService projectService;
    @Autowired
    BusSaleProjectDetailService detailService;

    //添加销售项目
    @ApiOperation(value = "添加销售项目", notes = "")
    @RequestMapping(value = "/addItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult addItem(@RequestBody @Validated ProjectVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusSaleProject item = new BusSaleProject();
        BeanUtils.copyProperties(vo, item);
        boolean result = projectService.save(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("添加失败");
    }

    //修改服务项目
    @ApiOperation(value = "修改服务项目", notes = "")
    @RequestMapping(value = "/editItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editItem(@RequestBody @Validated({GroupEdit.class, Default.class}) ProjectVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusSaleProject item = new BusSaleProject();
        BeanUtils.copyProperties(vo, item);
        boolean result = projectService.updateById(item);
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
        BusSaleProject item = new BusSaleProject();
        item.setId(id);
        item.setIfDelete(true);
        boolean result = projectService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("删除失败");
    }



    //添加销售项目明细
    @ApiOperation(value = "添加销售项目明细", notes = "")
    @RequestMapping(value = "/detail/addItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult addDetailItem(@RequestBody @Validated DetailVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusSaleProjectDetail item = new BusSaleProjectDetail();
        BeanUtils.copyProperties(vo, item);
        boolean result = detailService.save(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("添加失败");
    }

    //修改服务项目明细
    @ApiOperation(value = "修改服务项目明细", notes = "")
    @RequestMapping(value = "/detail/editItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editDetailItem(@RequestBody @Validated({GroupEdit.class, Default.class}) DetailVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusSaleProjectDetail item = new BusSaleProjectDetail();
        BeanUtils.copyProperties(vo, item);
        boolean result = detailService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("修改失败");
    }

    //删除服务项目明细
    @ApiOperation(value = "删除服务项目明细", notes = "")
    @RequestMapping(value = "/detail/deleteItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult deleteDetailItem(@RequestParam(name = "id") Integer id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        boolean result = detailService.removeById(id);
        return result ? JSONResult.ok() : JSONResult.errorMsg("删除失败");
    }

    //按条件查找销售项目
    @ApiOperation(value = "按条件查找销售项目", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated ProjectQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusSaleProject> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusSaleProject> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(vo.getFkCategoryId())){
            queryWrapper.eq(BusSaleProject::getFkCategoryId, vo.getFkCategoryId());
        }
        if(StringUtils.isNotBlank(vo.getName())){
            queryWrapper.like(BusSaleProject::getName, vo.getName());
        }
        if(StringUtils.isNotBlank(vo.getSaleName())){
            queryWrapper.like(BusSaleProject::getSaleName, vo.getSaleName());
        }
        if(vo.getOwnerType() != null){
            queryWrapper.eq(BusSaleProject::getOwnerType, vo.getOwnerType());
        }
        queryWrapper.eq(BusSaleProject::getIfDelete,false);
        IPage<BusSaleProject> itemIPage = projectService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",itemIPage.getRecords());
        map.put("total",itemIPage.getTotal());
        map.put("pageNum",itemIPage.getCurrent());
        map.put("pageSize",itemIPage.getSize());
        return JSONResult.ok(map);
    }

    //获取销售项目详情
    @ApiOperation(value = "获取销售项目详情", notes = "")
    @RequestMapping(value = "/getItemDetail", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemDetail(@RequestParam(name = "id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        BusSaleProject item = projectService.getById(id);
        ProjectDetailVO vo = new ProjectDetailVO();
        if(item == null || item.getIfDelete()){
            return JSONResult.errorMsg("参数错误");
        }
        //基本信息
        BeanUtils.copyProperties(item, vo);
        map.put("detail",vo);

        //服务项目列表
        LambdaQueryWrapper<BusSaleProjectDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSaleProjectDetail::getFkSaleProjectId,id);
        List<BusSaleProjectDetail> itemList = detailService.list(queryWrapper);
        map.put("itemList",itemList);

        return JSONResult.ok(map);
    }
}
