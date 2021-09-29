package com.company.homemaking.business.vo.servicecat;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 胡东斌
 * @create 2020-05-28
 */
@Data
public class CatSelectQueryVO {

    //父类别id
    @NotNull(message = "父类别id不能为空")
    private Integer pid;
    //需要获取的等级（一级传0；二级传1，三级传2）
    @NotNull(message = "等级不能为空")
    private Integer catLevel;
}
