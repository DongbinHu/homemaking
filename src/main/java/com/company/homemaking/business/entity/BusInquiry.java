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
 * 询价单
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_inquiry")
public class BusInquiry extends Model<BusInquiry> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 询价用户
     */
    private Integer fkCustomerId;

    /**
     * 项目
     */
    private Integer fkSaleProjectId;

    /**
     * 家政师
     */
    private Integer fkWorkerId;

    /**
     * 用户第一次出价
     */
    private BigDecimal firstPrice;

    /**
     * 用户第二次出价
     */
    private BigDecimal secondPrice;

    /**
     * 家政师还价
     */
    private BigDecimal backPrice;

    /**
     * 用户最后出价
     */
    private BigDecimal lastPrice;

    /**
     * 询价轮数，一共两轮(1/2)
     */
    private Boolean inquiryRound;

    /**
     * 家政师是否确认
     */
    private Boolean ifWorkerAffirm;

    /**
     * 用户说明
     */
    private String memo;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 是否删除（0正常1删除）
     */
    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
