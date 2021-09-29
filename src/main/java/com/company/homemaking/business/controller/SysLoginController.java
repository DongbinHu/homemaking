package com.company.homemaking.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.company.homemaking.business.entity.BusSysUser;
import com.company.homemaking.business.service.BusSysUserService;
import com.company.homemaking.business.vo.sysuser.SysUserVO;
import com.company.homemaking.common.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author 胡东斌
 * @create 2020/4/8
 */

@Api(description = "后台登录接口")
@Slf4j
@Controller
@RequestMapping("/business/sys")
public class SysLoginController {

    @Autowired
    BusSysUserService userService;
    /**
     * 登陆逻辑
     * @param
     * @return
     */
    @ApiOperation(value = "登录", notes = "必填项：userName,password；")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult login(@RequestBody SysUserVO user){
        if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())){
            return JSONResult.errorMsg("用户名和密码不能为空");
        }
        HashMap<String, Object> map = new HashMap<>();
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        //3.执行登录方法
        try {
            //没发生异常代表登录成功   只要发生异常就代表登陆失败
            subject.login(token);
            LambdaQueryWrapper<BusSysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BusSysUser::getIfDelete,false);
            queryWrapper.eq(BusSysUser::getUserName,token.getUsername());
            BusSysUser userInfo = userService.getOne(queryWrapper);
            BusSysUser loginTimeUser = new BusSysUser();
            loginTimeUser.setId(userInfo.getId());
            loginTimeUser.setLastDate(LocalDateTime.now());
            userService.updateById(loginTimeUser);
            subject.getSession().setAttribute("userInfo", userInfo);
            map.put("token", subject.getSession().getId());
            map.put("userName",userInfo.getUserName());
            map.put("nickname",userInfo.getNickname());
            return JSONResult.ok(map);
        } catch (UnknownAccountException e) {
            //此异常为用户名不存在
            //e.printStackTrace();
            return JSONResult.errorMsg("用户名不存在");
        }catch (IncorrectCredentialsException e){
            //此异常为密码错误
            return JSONResult.errorMsg("密码错误");
        }
    }

    @RequestMapping("/noAuth")
    @ResponseBody
    public JSONResult noAuth() {
        return JSONResult.errorAuthMsg("对不起，你没有这个权限!");
    }

    @RequestMapping(value = "/toLogin", method = {RequestMethod.GET})
    public String toLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult logout() {
        Subject subject = SecurityUtils.getSubject();
        // 当前用户信息
//        TbSysUser user = (TbSysUser) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
        subject.logout();
        return JSONResult.ok();
    }
}
