package com.company.homemaking.business.vo.servicecat;

import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.common.enums.CategoryStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 胡东斌
 * @create 2020-05-28
 */
@Data
public class CategoryVO {

    @NotNull( groups = {GroupEdit.class}, message = "id不能为空")
    private Integer id;

    /**
     * 分类名称
     */
    @NotEmpty(message = "分类名称不能为空")
    private String name;

    /**
     * 父分类id
     */
    private Integer pid;

    /**
     * 层级从0开始
     */
    private Integer level;

    /**
     * 0-正常/1-冻结/2-注销
     */
    @NotNull( groups = {GroupEdit.class}, message = "状态不能为空")
    private CategoryStatusEnum status;
}
