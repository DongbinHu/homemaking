package com.company.homemaking.consumer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 保洁工人信息表
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_worker_baojie_info")
public class WorkerBaojieInfo extends Model<WorkerBaojieInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联worker表主键
     */
    private Integer fkWorkerId;

    /**
     * 从业起始时间
     */
    private Integer workBeginDate;

    /**
     * 工作区域
     */
    private String workRegion;

    /**
     * 自我介绍
     */
    private String selfDescription;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
