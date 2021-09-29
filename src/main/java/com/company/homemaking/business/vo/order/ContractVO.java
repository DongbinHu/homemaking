package com.company.homemaking.business.vo.order;

import com.company.homemaking.common.enums.OrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 合同  前端列表用
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-28
 */
@Data
public class ContractVO {


    private Integer id;

    /**
     * 订单id
     */
    private Integer fkOrderId;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 客户外键
     */
    private Integer fkCustomerId;

    /**
     * 客户姓名
     */
    private Integer customerName;

    /**
     * 客户联系电话
     */
    private String customerMobile;

    /**
     * 家政师外键
     */
    private Integer fkWorkerId;

    /**
     * 家政师姓名
     */
    private String workerName;

    /**
     * 家政师编号
     */
    private Integer workerCode;

    /**
     * 合同文件地址
     */
    private String contractFileUrl;

}
