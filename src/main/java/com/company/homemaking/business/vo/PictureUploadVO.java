package com.company.homemaking.business.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 胡东斌
 * @create 2020/6/3
 */
@Api
@Data
public class PictureUploadVO {
    @ApiModelProperty(value = "图片base64码")
    @NotEmpty(message = "图片base64码不能为空")
    private String base64Str;
    @ApiModelProperty(value = "图片类型：customer(客户)/business(后台)/worker(家政师)")
    @NotEmpty(message = "图片类型不能为空")
    private String imgType;
}
