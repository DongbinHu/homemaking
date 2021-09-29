package com.company.homemaking.business.vo.sysuser;

import com.company.homemaking.business.validation.service.GroupAdd;
import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.common.enums.SysUserStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author 胡东斌
 * @create 2020-04-08
 */
@Data
public class SysUserVO {

    /**
     * 编号
     */
    @NotNull( groups = {GroupEdit.class}, message = "id不能为空")
    private Integer id;
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotEmpty( groups = {GroupAdd.class}, message = "密码不能为空")
    private String password;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    /**
     * 状态(1:正常2:冻结3:失效)
     */
    private SysUserStatusEnum status;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastDate;
}
