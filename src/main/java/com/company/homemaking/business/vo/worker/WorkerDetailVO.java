package com.company.homemaking.business.vo.worker;

import com.company.homemaking.common.enums.GenderEnum;
import com.company.homemaking.common.enums.UserAuthStatusEnum;
import com.company.homemaking.common.enums.WorkerStatusEnum;
import com.company.homemaking.common.enums.WorkerWorkStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 家政师
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-21
 */
@Data
public class WorkerDetailVO{

    /**
     * id
     */
    private Integer id;

    /**
     * 编号
     */
    private Integer code;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 手机号码
     */
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
     * 微信所在城市
     */
    private String wechatCity;

    /**
     * 微信用户唯一标识
     */
    private String wechatOpenid;

    /**
     * 微信开放平台标识符
     */
    private String wechatUnionid;

    /**
     * 从事项目（#保洁#育婴#陪护#保姆#）
     */
    private String workProject;

    /**
     * 状态（1新建2正常3冻结4注销）
     */
    private WorkerStatusEnum status;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 营业状态(1开放预约/2暂停预约)
     */
    private WorkerWorkStatusEnum workStatus;

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
