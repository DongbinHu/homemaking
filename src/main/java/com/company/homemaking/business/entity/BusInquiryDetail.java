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
 * 询价明细
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_inquiry_detail")
public class BusInquiryDetail extends Model<BusInquiryDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 询价单id
     */
    private Integer fkInquiryId;

    /**
     * 包含的服务项目
     */
    private Integer fkServiceItemId;

    /**
     * 服务项目名称
     */
    private String serviceItemName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
