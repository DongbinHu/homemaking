package com.company.homemaking.consumer.vo.user;

import com.company.homemaking.common.enums.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 返回给前端的用户类
 * @author 胡东斌
 * @create 2020-04-14
 */

@Data
public class UserInfoVO {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 身份类型(1万通员工/2泊头水钢琴业主/3泊头泊澜天下业主/4蓟县山水和苑业主/5普通用户/6其他)
     */
    private UserTypeEnum roleType;

    /**
     * 证件类型(1身份证)
     */
    private IdCardTypeEnum idType;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 所在地区
     */
    private String areaName;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 银行卡号
     */
    private String accountNumber;

    /**
     * 开户行
     */
    private String accountBank;

    /**
     * 微信用户昵称
     */
    private String wechatNickName;

    /**
     * 微信头像
     */
    private String wechatAvatarUrl;

    /**
     * 微信性别(0未知/1男性/2女性)
     */
    private GenderEnum wehchatGender;

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
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 身份认证状态(0未认证/1认证成功/2认证失败)
     */
    private UserAuthStatusEnum ifAuth;

    /**
     * 状态(1有效/2无效/3初始)
     */
    private UserStatusEnum status;
}
