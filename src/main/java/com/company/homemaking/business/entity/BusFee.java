package com.company.homemaking.business.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.company.homemaking.common.enums.FeeStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 收费表
 * </p>
 *
 * @author liubangzi
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_fee")
public class BusFee extends Model<BusFee> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属订单
     */
    private Integer fkOrderId;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 用户id
     */
    private Integer fkCustomerId;

    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 用户手机号
     */
    private String customerMobile;

    /**
     * 家政师
     */
    private Integer fkWorkerId;

    /**
     * 家政师姓名
     */
    private String workerName;

    /**
     * 家政师联系方式
     */
    private String workerMobile;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 描述
     */
    private String description;

    /**
     * 1待支付2已支付3已取消
     */
    private FeeStatusEnum status;

    /**
     * 创建日期
     */
    private LocalDateTime createDate;

    /**
     * 是否删除（0正常1已删除）
     */
    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
