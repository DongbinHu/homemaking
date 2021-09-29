package com.company.homemaking.business.vo.worker;

import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.common.enums.GenderEnum;
import com.company.homemaking.common.enums.UserAuthStatusEnum;
import com.company.homemaking.common.enums.WorkerStatusEnum;
import com.company.homemaking.common.enums.WorkerWorkStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 家政师
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-15
 */
@Data
public class WorkerVO{


    /**
     * id
     */
    @NotNull( groups = {GroupEdit.class}, message = "id不能为空")
    private Integer id;

    /**
     * 编号
     */
    private Integer code;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String trueName;

    /**
     * 手机号码
     */
    @NotEmpty(message = "手机号码不能为空")
    private String phoneNumber;

    /**
     * 出生年月
     */
    private LocalDate birthday;

    /**
     * 性别(0未知/1男/2女)
     */
    private GenderEnum gender;

    /**
     * 二维码地址
     */
    private String qrcodeUrl;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 从事项目（#保洁#育婴#陪护#保姆#）
     */
    @NotEmpty(message = "从事项目不能为空")
    private String workProject;

    /**
     * 状态（1新建2正常3冻结4注销）
     */
    private WorkerStatusEnum status;

    /**
     * 营业状态(1开放预约/2暂停预约)
     */
    @NotNull(message = "营业状态不能为空")
    private WorkerWorkStatusEnum workStatus;

    /**
     * 身份认证状态(0未认证/1已认证)
     */
    private UserAuthStatusEnum ifAuth;

    /**
     * 是否删除(0不删除/1删除)
     */
    private Boolean ifDelete;

    /**
     * 注册时间
     */
    private LocalDateTime registerDate;

    /**
     * 最后修改日期
     */
    private LocalDateTime updateDate;

    /**
     * 最后登录日期
     */
    private LocalDateTime lastLoginDate;

}
