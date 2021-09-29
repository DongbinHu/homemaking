package com.company.homemaking.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author 胡东斌
 * @create 2020-04-13
 */

public enum SaleProjectDetailTypeEnum implements IEnum {
    BASE(0,"基础项目"),PLUS(1,"增值项目");
    private int code;
    private String desc;
    SaleProjectDetailTypeEnum(final int code, final String desc){
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Serializable getValue(){
        return this.code;
    }
    @JsonValue
    public String gerDesc(){
        return this.desc;
    }
}
