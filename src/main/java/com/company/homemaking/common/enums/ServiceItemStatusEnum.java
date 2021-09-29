package com.company.homemaking.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author 胡东斌
 * @create 2020-05-15
 */

public enum ServiceItemStatusEnum implements IEnum {
    VALID(1,"有效"),INVALID(2,"暂时无效"),DISCARD(3,"废止");
    private int code;
    private String desc;
    ServiceItemStatusEnum(final int code, final String desc){
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
