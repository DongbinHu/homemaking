package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.BusSysRole;
import com.company.homemaking.business.entity.BusSysUser;
import com.company.homemaking.business.entity.BusSysUserRoleBind;
import com.company.homemaking.business.service.BusSysRoleService;
import com.company.homemaking.business.service.BusSysUserRoleBindService;
import com.company.homemaking.business.service.BusSysUserService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.validation.service.GroupAdd;
import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.business.vo.sysuser.SysRoleUserListVO;
import com.company.homemaking.business.vo.sysuser.SysUserQueryVO;
import com.company.homemaking.business.vo.sysuser.SysUserVO;
import com.company.homemaking.common.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
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
 * 后台系统用户表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Api(description="系统用户接口")
@Controller
@RequestMapping("/business/sysUser")
public class BusSysUserController {
    @Autowired
    BusSysUserService userService;
    @Autowired
    BusSysUserRoleBindService bindService;
    @Autowired
    BusSysRoleService roleService;

    @ApiOperation(value = "新增用户", notes = "")
    @RequestMapping(value = "/addItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult addItem(@RequestBody @Validated({GroupAdd.class, Default.class}) SysUserVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        LambdaQueryWrapper<BusSysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysUser::getIfDelete,false);
        queryWrapper.eq(BusSysUser::getUserName,vo.getUserName());
        if(userService.getOne(queryWrapper) != null){
            return JSONResult.errorMsg("已存在相同的用户名");
        }
        BusSysUser item = new BusSysUser();
        BeanUtils.copyProperties(vo, item);
        String encodedPassword = new SimpleHash("md5", item.getPassword(), item.getUserName(), 1).toString();
        item.setPassword(encodedPassword);
        boolean result = userService.save(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("添加用户失败");
    }

    @ApiOperation(value = "删除用户", notes = "")
    @RequestMapping(value = "/deleteItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult deleteItem(@RequestParam(name = "id") Integer id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        BusSysUser item = new BusSysUser();
        item.setId(id);
        item.setIfDelete(true);
        boolean result = userService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("删除用户失败");
    }

    @ApiOperation(value = "修改用户", notes = "")
    @RequestMapping(value = "/editItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editItem(@RequestBody @Validated({GroupEdit.class, Default.class}) SysUserVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        LambdaQueryWrapper<BusSysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysUser::getIfDelete,false);
        queryWrapper.eq(BusSysUser::getUserName,vo.getUserName());
        BusSysUser oldUser = userService.getOne(queryWrapper);
        if(oldUser != null && !oldUser.getId().equals(vo.getId())){
            return JSONResult.errorMsg("已存在相同的用户名");
        }
        BusSysUser item = new BusSysUser();
        BeanUtils.copyProperties(vo, item);
        if(StringUtils.isNotBlank(item.getPassword())){
            String encodedPassword = new SimpleHash("md5", item.getPassword(), item.getUserName(), 1).toString();
            item.setPassword(encodedPassword);
        }
        boolean result = userService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("修改用户失败");
    }

    @ApiOperation(value = "查询用户列表", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated SysUserQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusSysUser> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusSysUser> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(vo.getUserName())){
            queryWrapper.like(BusSysUser::getUserName, vo.getUserName());
        }
        if(vo.getStatus() != null){
            queryWrapper.eq(BusSysUser::getStatus, vo.getStatus());
        }
        queryWrapper.eq(BusSysUser::getIfDelete,false);
        queryWrapper.select(BusSysUser.class, info -> !info.getColumn().equals("password") && !info.getColumn().equals("if_delete"));
        IPage<BusSysUser> iPage = userService.page(page, queryWrapper);
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
        BusSysUser item = userService.getById(id);
        SysUserVO vo = new SysUserVO();
        if(item == null || item.getIfDelete()){
            return JSONResult.errorMsg("没有此用户");
        }
        BeanUtils.copyProperties(item, vo);
        vo.setPassword(null);
        return JSONResult.ok(vo);
    }

    @ApiOperation(value = "为用户分配角色", notes = "")
    @RequestMapping(value = "/assignRole", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult assignRole(@RequestBody @Validated SysRoleUserListVO listVO, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        LambdaQueryWrapper<BusSysUserRoleBind> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysUserRoleBind::getUserId,listVO.getUserId());
        queryWrapper.eq(BusSysUserRoleBind::getIfDelete,false);
        BusSysUserRoleBind item = new BusSysUserRoleBind();
        item.setIfDelete(true);
        boolean delResult = true;
        int curRoleNum = bindService.count(queryWrapper);
        if(curRoleNum != 0){
            delResult = bindService.update(item,queryWrapper);
        }
        boolean result = bindService.saveBatch(listVO.getRelationList());
        return delResult && result ? JSONResult.ok() : JSONResult.errorMsg("分配角色失败");
    }

    @ApiOperation(value = "获取所有有效角色列表", notes = "")
    @RequestMapping(value = "/getRoleTree", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getRoleTree() {
        LambdaQueryWrapper<BusSysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysRole::getIfDelete,false);
        List<BusSysRole> list = roleService.list(queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        return JSONResult.ok(map);
    }

    @ApiOperation(value = "根据用户编号获取该用户当前所拥有的角色编号", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", paramType = "form", dataType = "int", required = true)})
    @RequestMapping(value = "/getRoleByUserId", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getRoleTree(@RequestParam(name = "userId") Integer userId) {
        if(userId == null){
            return JSONResult.errorMsg("用户id不能为空");
        }
        LambdaQueryWrapper<BusSysUserRoleBind> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysUserRoleBind::getUserId,userId);
        queryWrapper.eq(BusSysUserRoleBind::getIfDelete,false);
        List<BusSysUserRoleBind> list = bindService.list(queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        return JSONResult.ok(map);
    }
}
