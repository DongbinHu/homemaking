package com.company.homemaking.business.vo.worker;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 家政师
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-22
 */
@Data
public class BaojieVO {


    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Integer id;

    /**
     * 编号
     */
    @NotNull(message = "家政师编号不能为空")
    private Integer fkWorkerId;

    /**
     * 从业起始时间
     */
    private Integer workBeginDate;

    /**
     * 工作区域
     */
    private String workRegion;

    /**
     * 自我介绍
     */
    private String selfDescription;

}
