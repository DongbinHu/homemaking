package com.company.homemaking.business.vo.sysuser;

import com.company.homemaking.business.entity.BusSysUserRoleBind;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 胡东斌
 * @create 2020/4/8
 */
@Data
public class SysRoleUserListVO {

    @NotNull(message = "id不能为空")
    private Integer userId;

    @NotEmpty(message = "用户与角色关系数组不能为空")
    private List<@NotNull @Valid BusSysUserRoleBind> relationList;
}
