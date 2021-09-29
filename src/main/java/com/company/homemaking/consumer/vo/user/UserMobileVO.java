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
public class UserMobileVO {

    @NotEmpty(message = "手机号码不能为空")
    private String phoneNumber;
//    @NotEmpty(message = "验证码不能为空")
//    private String smsCode;
    @NotNull(message = "身份类型不能为空")
    private UserTypeEnum roleType;

}
