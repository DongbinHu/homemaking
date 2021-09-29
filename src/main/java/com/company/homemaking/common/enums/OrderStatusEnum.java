package com.company.homemaking.common.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author 胡东斌
 * @create 2020-05-15
 */

public enum OrderStatusEnum implements IEnum {
    SAVE(0,"保存待发"),UNSIGNED(1,"待签约"),UNPAID(2,"待付款"),UNPERFORMANCE(3,"待履约"),PERFORMANCING(4,"履约中"),UNMARK(5,"待评价"),COMPLETE(6,"已完成"),CANCEL(7,"已取消");
    private int code;
    private String desc;
    OrderStatusEnum(final int code, final String desc){
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
