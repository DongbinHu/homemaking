package com.company.homemaking.business.service;

import com.company.homemaking.business.entity.BusSysUserRoleBind;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 用户角色关系表 服务类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
public interface BusSysUserRoleBindService extends IService<BusSysUserRoleBind> {
    Set<String> selByUserIdString(Integer userId);
}
