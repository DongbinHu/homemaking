package com.company.homemaking.business.vo.saleproject;

import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.common.enums.OwnerTypeEnum;
import com.company.homemaking.common.enums.ServiceItemStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 家政师
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-15
 */
@Data
public class ProjectVO {


    /**
     * id
     */
    @NotNull( groups = {GroupEdit.class}, message = "id不能为空")
    private Integer id;

    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 所属类别
     */
    @NotEmpty(message = "家政类型不能为空")
    private String fkCategoryId;

    /**
     * 平台平均价格
     */
    private BigDecimal avgPrice;

    /**
     * 销售名称
     */
    @NotEmpty(message = "销售名称不能为空")
    private String saleName;

    /**
     * 所有者类型（1-平台项目/2-私有项目）
     */
    @NotNull(message = "所有者类型不能为空")
    private OwnerTypeEnum ownerType;

    /**
     * 所属家政师(私有项目才有)
     */
    private Integer fkWorkerId;

}
