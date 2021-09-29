package com.company.homemaking.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order_log")
public class BusOrderLog extends Model<BusOrderLog> {

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
    private Integer orderCode;

    /**
     * 客户id
     */
    private Integer fkCustomerId;

    /**
     * 客户姓名
     */
    private Integer customerName;

    /**
     * 家政师id
     */
    private Integer fkWorkerId;

    /**
     * 家政师姓名
     */
    private Integer workerName;

    /**
     * 家政师电话
     */
    private Integer workerMobile;

    /**
     * 0-平台/1-用户/2-家政师
     */
    private Integer operatorType;

    /**
     * 操作内容
     */
    private Integer content;

    /**
     * 录入时间
     */
    private Integer createDate;

    /**
     * 是否删除（0正常1已删除）
     */
    private Integer ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
