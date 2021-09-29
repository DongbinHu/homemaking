package com.company.homemaking.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author 胡东斌
 * @create 2020-05-15
 */

public enum CategoryStatusEnum implements IEnum {
    NORMAL(0,"正常"),FROZEN(1,"冻结"),LOGOUT(2,"注销");
    private int code;
    private String desc;
    CategoryStatusEnum(final int code, final String desc){
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
    public Integer getCode(){return this.code;}
}
