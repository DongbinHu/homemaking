package com.company.homemaking.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author 胡东斌
 * @create 2020-05-15
 */

public enum WorkerWorkStatusEnum implements IEnum {
    OPEN(1,"开放预约"),PAUSE(2,"暂停预约");
    private int code;
    private String desc;
    WorkerWorkStatusEnum(final int code, final String desc){
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
