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
@TableName("tb_sale_project")
public class SaleProject extends Model<SaleProject> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属类别
     */
    private String fkCategoryId;

    /**
     * 平台平均价格
     */
    private BigDecimal avgPrice;

    /**
     * 销售名称
     */
    private String saleName;

    /**
     * 所有者类型（1-平台项目/2-私有项目）
     */
    private Boolean ownerType;

    /**
     * 所属家政师(私有项目才有)
     */
    private Integer fkWorkerId;

    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
