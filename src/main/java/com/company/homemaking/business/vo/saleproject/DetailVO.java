package com.company.homemaking.business.vo.saleproject;

import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.common.enums.OwnerTypeEnum;
import com.company.homemaking.common.enums.SaleProjectDetailTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 销售项目明细VO
 * </p>
 *
 * @author liubangzi
 * @since 2020-06-02
 */
@Data
public class DetailVO {


    /**
     * id
     */
    @NotNull( groups = {GroupEdit.class}, message = "id不能为空")
    private Integer id;

    /**
     * 销售项目id
     */
    @NotNull(message = "销售项目id不能为空")
    private Integer fkSaleProjectId;

    /**
     * 服务项目id
     */
    @NotNull(message = "服务项目id不能为空")
    private Integer fkServiceItemId;

    /**
     * 类型（0基础项目1增值项目）
     */
    @NotNull(message = "类型不能为空")
    private SaleProjectDetailTypeEnum type;

}
