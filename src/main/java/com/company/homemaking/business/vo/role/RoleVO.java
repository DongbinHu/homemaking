package com.company.homemaking.business.vo.role;

import com.company.homemaking.business.validation.service.GroupEdit;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 胡东斌
 * @create 2020-04-08
 */
@Data
public class RoleVO {
    @NotNull( groups = {GroupEdit.class}, message = "id不能为空")
    private Integer id;
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;
}
