package com.company.homemaking.business.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.company.homemaking.common.enums.OrderStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order")
public class BusOrder extends Model<BusOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 客户外键
     */
    private Integer fkCustomerId;

    /**
     * 客户姓名
     */
    private Integer customerName;

    /**
     * 客户地址
     */
    private String customerAddress;

    /**
     * 客户联系电话
     */
    private String customerMobile;

    /**
     * 家政师外键
     */
    private Integer fkWorkerId;

    /**
     * 家政师姓名
     */
    private String workerName;

    /**
     * 家政师编号
     */
    private Integer workerCode;

    /**
     * 销售项目外键
     */
    private Integer fkProjectId;

    /**
     * 销售项目名称
     */
    private String saleName;

    /**
     * 客户说明
     */
    private String customerMemo;

    /**
     * 成交价格
     */
    private BigDecimal price;

    /**
     * 约定起始时间
     */
    private LocalDateTime scheduleStartDate;

    /**
     * 约定结束时间
     */
    private LocalDateTime scheduleEndDate;

    /**
     * 工作开始时间
     */
    private LocalDateTime workStartDate;

    /**
     * 工作实际结束时间
     */
    private LocalDateTime workEndDate;

    /**
     * 状态（0-保存待发/1-待签约/2-待付款/3-待履约/4-履约中/5-待评价/6-已完成/7-已取消）
     */
    private OrderStatusEnum status;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 是否删除
     */
    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
