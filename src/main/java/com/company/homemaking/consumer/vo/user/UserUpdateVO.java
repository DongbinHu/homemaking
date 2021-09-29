package com.company.homemaking.consumer.vo.user;

import com.company.homemaking.common.enums.UserTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 存入redis用的user类，此类不应返回给前端，因为包含微信的session_key
 * @author 胡东斌
 * @create 2020-04-14
 */

@Data
public class UserUpdateVO {
    /**
     * 姓名
     */
    @NotEmpty(message = "真实姓名不能为空")
    private String trueName;
    private String phoneNumber;
//    private String smsCode;
    @NotNull(message = "身份类型不能为空")
    private UserTypeEnum roleType;
    /**
     * 证件号码
     */
    @NotEmpty(message = "身份证号不能为空")
    private String idNumber;
    /**
     * 账户名
     */
    @NotEmpty(message = "账户名不能为空")
    private String accountName;
    /**
     * 银行卡号
     */
    @NotEmpty(message = "银行账号不能为空")
    private String accountNumber;
    /**
     * 开户行
     */
    @NotEmpty(message = "开户行不能为空")
    private String accountBank;
}
