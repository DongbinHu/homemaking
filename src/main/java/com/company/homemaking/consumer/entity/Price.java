package com.company.homemaking.consumer.entity;

import java.math.BigDecimal;
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
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_price")
public class Price extends Model<Price> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属销售项目
     */
    private Integer fkSaleProjectId;

    /**
     * 家政人员
     */
    private Integer fkWorkerId;

    /**
     * 必选部分价格
     */
    private BigDecimal basePrice;

    /**
     * 备注
     */
    private String memo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
