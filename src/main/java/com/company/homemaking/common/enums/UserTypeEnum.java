package com.company.homemaking.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author 胡东斌
 * @create 2020-04-13
 */

public enum UserTypeEnum implements IEnum {
    VONTOWN(1,"万通员工"),SGQ(2,"泊头水钢琴业主"),BLTX(3,"泊头泊澜天下业主"),SSHY(4,"蓟县山水和苑业主"),NORMAL(5,"普通用户"),OTHER(6,"其他");
    private int code;
    private String desc;
    UserTypeEnum(final int code, final String desc){
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
