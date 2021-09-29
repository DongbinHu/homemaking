package com.company.homemaking.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户地址表
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_customer_address")
public class BusCustomerAddress extends Model<BusCustomerAddress> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户表外键
     */
    private Integer fkCustomerId;

    /**
     * 城市
     */
    private String city;

    /**
     * 行政区
     */
    private String distirct;

    /**
     * 街道
     */
    private String street;

    /**
     * 小区
     */
    private String community;

    /**
     * 具体地址
     */
    private String address;

    /**
     * 是否删除（0正常1删除）
     */
    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
