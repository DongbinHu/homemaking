package com.company.homemaking.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author 胡东斌
 * @create 2020-05-15
 */

public enum FeeStatusEnum implements IEnum {
    PAYING(1,"待支付"),PAID(2,"已支付"),CANCEL(3,"已取消");
    private int code;
    private String desc;
    FeeStatusEnum(final int code, final String desc){
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
