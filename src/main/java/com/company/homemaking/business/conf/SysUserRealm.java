package com.company.homemaking.business.conf;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.company.homemaking.business.entity.BusSysUser;
import com.company.homemaking.business.service.BusSysUserRoleBindService;
import com.company.homemaking.business.service.BusSysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author 胡东斌
 * @create 2020/4/8
 */
@Component
public class SysUserRealm extends AuthorizingRealm {

    @Autowired
    BusSysUserService userService;
    @Autowired
    BusSysUserRoleBindService bindService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 能进入到这里，表示账号已经通过验证了
        String username = (String) principals.getPrimaryPrincipal();
        LambdaQueryWrapper<BusSysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysUser::getIfDelete,false);
        queryWrapper.eq(BusSysUser::getUserName,username);
        BusSysUser user = userService.getOne(queryWrapper);
        //获取用户所有角色信息
        Set<String> roleString = bindService.selByUserIdString(user.getId());
        // 授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //把通过数据库获取到的角色保存到shiro
        info.setRoles(roleString);
        return info;
    }

    /**
     * 身份认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取登陆的用户名和密码
        UsernamePasswordToken t = (UsernamePasswordToken) token;
        String username = token.getPrincipal().toString();
        String password = new String(t.getPassword());
        //从数据库中获取登陆的用户
        LambdaQueryWrapper<BusSysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusSysUser::getIfDelete,false);
        queryWrapper.eq(BusSysUser::getUserName,username);
        BusSysUser user = userService.getOne(queryWrapper);
        //没找到用户
        if (null == user) {
            throw new UnknownAccountException();
        }
        //把用户输入的密码进行md5和盐加密一起运算1次
        String encodedPassword = new SimpleHash("md5", password, user.getUserName(), 1).toString();
        //上一步加密后的密文和数据库的密文进行判断
        if (!user.getPassword().equals(encodedPassword)) {
            //密码出错
            throw new IncorrectCredentialsException();
        }
        // 认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
        SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(username, password, getName());
        //清缓存中的授权信息，保证每次登陆 都可以重新授权。因为AuthorizingRealm会先检查缓存有没有 授权信息，再调用授权方法
        super.clearCachedAuthorizationInfo(a.getPrincipals());
        return a;
    }

}
