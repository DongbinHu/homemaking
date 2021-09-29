package com.company.homemaking.business.vo.customer;

import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.common.enums.IdCardTypeEnum;
import com.company.homemaking.common.enums.UserAuthStatusEnum;
import com.company.homemaking.common.enums.UserStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 胡东斌
 * @create 2020-05-15
 */
@Data
public class CustomerDetailVO {

    /**
     * 编号
     */
    @NotNull( groups = {GroupEdit.class}, message = "id不能为空")
    private Integer id;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String trueName;

    /**
     * 性别(0未知/1男/2女)
     */
    private Integer gender;

    /**
     * 手机号码
     */
    @NotEmpty(message = "手机号码不能为空")
    private String phoneNumber;

    /**
     * 证件类型(1身份证)
     */
    @NotNull(message = "证件类型不能为空")
    private IdCardTypeEnum idType;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地区
     */
    private String area;

    /**
     * 微信用户昵称
     */
    private String wechatNickName;

    /**
     * 微信头像
     */
    private String wechatAvatarUrl;

    /**
     * 微信性别(0未知/1男/2女)
     */
    private Integer wehchatGender;

    /**
     * 微信所在国家
     */
    private String wechatCountry;

    /**
     * 微信所在省份
     */
    private String wechatProvince;

    /**
     * 微信所在城市
     */
    private String wechatCity;

    /**
     * 微信语言(en英文/zh_CN简体中文/zh_TW繁体中文)
     */
    private String wechatLanguage;

    /**
     * 微信手机号
     */
    private String wechatPhoneNumber;

    /**
     * 微信区号
     */
    private String wechatCountryCode;

    /**
     * 微信没有区号的手机号
     */
    private String wechatPurePhoneNumber;

    /**
     * 微信用户唯一标识
     */
    private String wechatOpenid;

    /**
     * 微信开放平台标识符
     */
    private String wechatUnionid;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 状态(1正常/2注销/3冻结)
     */
    private UserStatusEnum status;

    /**
     * 身份认证状态(0未认证/1已认证)
     */
    private UserAuthStatusEnum ifAuth;

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
