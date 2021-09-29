package com.company.homemaking.consumer.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_customer")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 性别(0未知/1男/2女)
     */
    private Integer gender;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 密码
     */
    private String password;

    /**
     * 证件类型(1身份证)
     */
    private Integer idType;

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
    private Integer status;

    /**
     * 是否删除(0不删除/1删除)
     */
    private Boolean ifDelete;

    /**
     * 身份认证状态(0未认证/1已认证)
     */
    private Integer ifAuth;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
