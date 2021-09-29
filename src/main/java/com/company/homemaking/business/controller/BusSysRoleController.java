package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.BusSysRole;
import com.company.homemaking.business.entity.BusSysUserRoleBind;
import com.company.homemaking.business.service.BusSysRoleService;
import com.company.homemaking.business.service.BusSysUserRoleBindService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.business.vo.role.RoleQueryVO;
import com.company.homemaking.business.vo.role.RoleVO;
import com.company.homemaking.common.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * 系统角色表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */

@Api(description="系统角色接口")
@Controller
@RequestMapping("/business/role")
public class BusSysRoleController {
    @Autowired
    BusSysRoleService roleService;
    @Autowired
    BusSysUserRoleBindService bindService;

    @ApiOperation(value = "新增角色", notes = "")
    @RequestMapping(value = "/addItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult addItem(@RequestBody @Validated RoleVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusSysRole item = new BusSysRole();
        BeanUtils.copyProperties(vo, item);
        boolean result = roleService.save(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("添加角色失败");
    }

    @ApiOperation(value = "删除角色", notes = "")
    @RequestMapping(value = "/deleteItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult deleteItem(@RequestParam(name = "id") Integer id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        LambdaQueryWrapper<BusSysUserRoleBind> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysUserRoleBind::getRoleId,id);
        queryWrapper.eq(BusSysUserRoleBind::getIfDelete,false);
        List<BusSysUserRoleBind> list = bindService.list(queryWrapper);
        if(list.size() > 0){
            return JSONResult.errorMsg("有些用户已经分配了该角色，不允许删除");
        }
        BusSysRole item = new BusSysRole();
        item.setId(id);
        item.setIfDelete(true);
        boolean result = roleService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("删除角色失败");
    }

    @ApiOperation(value = "修改角色名称", notes = "")
    @RequestMapping(value = "/editItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editItem(@RequestBody @Validated({GroupEdit.class, Default.class}) RoleVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusSysRole item = new BusSysRole();
        BeanUtils.copyProperties(vo, item);
        boolean result = roleService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("修改角色名称失败");
    }

    @ApiOperation(value = "查询角色列表", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated RoleQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusSysRole> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusSysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysRole::getIfDelete,false);
        IPage<BusSysRole> iPage = roleService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",iPage.getRecords());
        map.put("total",iPage.getTotal());
        map.put("pageNum",iPage.getCurrent());
        map.put("pageSize",iPage.getSize());
        return JSONResult.ok(map);
    }
}
