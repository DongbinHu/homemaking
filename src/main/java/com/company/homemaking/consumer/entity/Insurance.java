package com.company.homemaking.consumer.entity;

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
 * 保险表
 * </p>
 *
 * @author liubangzi
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_Insurance")
public class Insurance extends Model<Insurance> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属订单
     */
    private Integer fkOrderId;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 客户id
     */
    private Integer fkCustomerId;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 家政师id
     */
    private Integer fkWorkerId;

    /**
     * 家政师编号
     */
    private Integer workerCode;

    /**
     * 家政师姓名
     */
    private String workerName;

    /**
     * 保险名称
     */
    private String insuranceName;

    /**
     * 保险编码
     */
    private String insuranceCode;

    /**
     * 保险说明
     */
    private String insuranceMemo;

    /**
     * 保险公司
     */
    private String insuranceCompany;

    /**
     * 1未生效/2已生效/3已过期/4已注销
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 是否删除（0-正常/1已删除）
     */
    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
