package com.company.homemaking.business.service.impl;

import com.company.homemaking.business.entity.BusSysUserRoleBind;
import com.company.homemaking.business.dao.BusSysUserRoleBindMapper;
import com.company.homemaking.business.service.BusSysUserRoleBindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.homemaking.business.vo.sysuser.ShiroSysUserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Service
public class BusSysUserRoleBindServiceImpl extends ServiceImpl<BusSysUserRoleBindMapper, BusSysUserRoleBind> implements BusSysUserRoleBindService {

    @Autowired
    private BusSysUserRoleBindMapper bindMapper;


    @Override
    public Set<String> selByUserIdString(Integer userId){
        List<ShiroSysUserRoleVO> roles = bindMapper.selByUserIdRoles(userId);
        Set<String> roleString = new HashSet<>();
        for(ShiroSysUserRoleVO role : roles) {
            roleString.add(role.getRoleName());
        }
        return roleString;
    }
}
