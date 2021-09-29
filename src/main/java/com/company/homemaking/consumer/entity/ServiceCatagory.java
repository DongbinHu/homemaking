package com.company.homemaking.consumer.entity;

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
 * 服务项目分类表
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_service_catagory")
public class ServiceCatagory extends Model<ServiceCatagory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父分类id
     */
    private Integer pid;

    /**
     * 层级从0开始
     */
    private Integer level;

    /**
     * 0-正常/1-冻结/2-注销
     */
    private Integer status;

    /**
     * 是否删除（0未删除/1已删除）
     */
    private Boolean ifDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
