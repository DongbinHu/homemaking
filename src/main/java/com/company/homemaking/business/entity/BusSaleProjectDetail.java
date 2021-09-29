package com.company.homemaking.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.company.homemaking.common.enums.SaleProjectDetailTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 销售项目明细表
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sale_project_detail")
public class BusSaleProjectDetail extends Model<BusSaleProjectDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 销售项目id
     */
    private Integer fkSaleProjectId;

    /**
     * 服务项目id
     */
    private Integer fkServiceItemId;

    /**
     * 类型（0基础项目1增值项目）
     */
    private SaleProjectDetailTypeEnum type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
