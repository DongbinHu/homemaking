package com.company.homemaking.business.vo.saleproject;

import com.company.homemaking.common.enums.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 家政师
 * </p>
 *
 * @author liubangzi
 * @since 2020-06-01
 */
@Data
public class ProjectDetailVO {

    /**
     * id
     */
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
    private OwnerTypeEnum ownerType;

    /**
     * 所属家政师(私有项目才有)
     */
    private Integer fkWorkerId;

}
