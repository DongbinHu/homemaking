package com.company.homemaking.consumer.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 胡东斌
 * @create 2020-04-03
 */
@Data
public class PageVO {

    @NotNull(message = "每页数量不能为空")
    private Integer pageSize;
    @NotNull(message = "当前页码不能为空")
    private Integer pageNum;
}
