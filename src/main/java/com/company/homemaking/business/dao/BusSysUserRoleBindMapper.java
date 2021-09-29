package com.company.homemaking.business.dao;

import com.company.homemaking.business.entity.BusSysUserRoleBind;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.homemaking.business.vo.sysuser.ShiroSysUserRoleVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 Mapper 接口
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Component(value = "BusSysUserRoleBindMapper")
public interface BusSysUserRoleBindMapper extends BaseMapper<BusSysUserRoleBind> {

    List<ShiroSysUserRoleVO> selByUserIdRoles(Integer userId);
}
