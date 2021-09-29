package com.company.homemaking.business.vo.servicecat;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 胡东斌
 * @create 2020-05-28
 */
@Data
public class CatChildQueryVO extends CatParentQueryVO {

    //父类别id
    @NotNull(message = "父类别id不能为空")
    private Integer pid;
}
