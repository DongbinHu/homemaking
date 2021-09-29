package com.company.homemaking.business.entity;

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
 * 优惠券表
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_coupon")
public class BusCoupon extends Model<BusCoupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 面值
     */
    private BigDecimal value;

    /**
     * 编码
     */
    private String code;

    /**
     * 验证码
     */
    private String checkCode;

    /**
     * 使用说明
     */
    private String description;

    /**
     * 0-面值代金券/1-计次劵
     */
    private Integer type;

    /**
     * 0-不可用/1-可用/2-已使用/3-已过期/4-已注销
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 状态修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 是否删除（0正常/1已删除）
     */
    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
