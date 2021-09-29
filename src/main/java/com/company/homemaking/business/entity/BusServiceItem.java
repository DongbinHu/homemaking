package com.company.homemaking.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.company.homemaking.common.enums.ServiceItemStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 服务项目表
 * </p>
 *
 * @author liubangzi
 * @since 2020-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_service_item")
public class BusServiceItem extends Model<BusServiceItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 服务标准
     */
    private String standardDescription;

    /**
     * 分类（保洁/母婴/陪护/保姆）
     */
    private String cataType;

    /**
     * 服务科目分类id
     */
    private Integer subType;

    /**
     * 状态（1有效2暂时无效3废止）
     */
    private ServiceItemStatusEnum status;

    /**
     * 是否删除（0未删除/1已删除）
     */
    private Boolean ifDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
