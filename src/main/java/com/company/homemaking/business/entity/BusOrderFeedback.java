package com.company.homemaking.business.entity;

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
 * 
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order_feedback")
public class BusOrderFeedback extends Model<BusOrderFeedback> {

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
     * 用户
     */
    private Integer fkCustomerId;

    /**
     * 用户姓名
     */
    private String customerName;

    /**
     * 家政师
     */
    private Integer fkWorkerId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 类型（0平台评论1用户评价2家政师评价）
     */
    private Integer type;

    /**
     * 所属收费项目
     */
    private Integer fkSaleProjectId;

    /**
     * 收费项目名称
     */
    private String saleProjectName;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 是否删除（0正常1删除）
     */
    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
