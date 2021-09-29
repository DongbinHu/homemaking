package com.company.homemaking.business.vo.serviceitem;

import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.common.enums.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 家政师
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-15
 */
@Data
public class ItemVO {


    /**
     * id
     */
    @NotNull( groups = {GroupEdit.class}, message = "id不能为空")
    private Integer id;

    /**
     * 项目名称
     */
    @NotEmpty(message = "项目名称不能为空")
    private String name;

    /**
     * 服务标准
     */
    private String standardDescription;

    /**
     * 分类（保洁/母婴/陪护/保姆）
     */
    @NotEmpty(message = "家政类型不能为空")
    private String cataType;

    /**
     * 服务科目分类id
     */
    @NotNull(message = "服务项目分类id不能为空")
    private Integer subType;

    /**
     * 状态（1有效2暂时无效3废止）
     */
    @NotNull(message = "状态不能为空")
    private ServiceItemStatusEnum status;

}
