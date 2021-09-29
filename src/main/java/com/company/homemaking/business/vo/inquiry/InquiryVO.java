package com.company.homemaking.business.vo.inquiry;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 询价单  前端列表用
 * </p>
 *
 * @author 胡东斌
 * @since 2020-05-28
 */
@Data
public class InquiryVO {


    private Integer id;

    /**
     * 询价用户
     */
    private Integer fkCustomerId;
    //客户电话
    private String customerMobile;
    //客户姓名
    private String customerName;

    /**
     * 项目
     */
    private Integer fkSaleProjectId;
    //项目名称
    private String projectName;

    /**
     * 家政师
     */
    private Integer fkWorkerId;
    //家政师姓名
    private String workerName;
    //家政师编号
    private Integer workerCode;

    /**
     * 用户第一次出价
     */
    private BigDecimal firstPrice;

    /**
     * 用户第二次出价
     */
    private BigDecimal secondPrice;

    /**
     * 家政师还价
     */
    private BigDecimal backPrice;

    /**
     * 用户最后出价
     */
    private BigDecimal lastPrice;

    /**
     * 询价轮数，一共两轮(1/2)
     */
    private Boolean inquiryRound;

    /**
     * 家政师是否确认
     */
    private Boolean ifWorkerAffirm;

    /**
     * 用户说明
     */
    private String memo;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

}
