package com.company.homemaking.business.vo.order;

import com.company.homemaking.business.vo.PageVO;
import com.company.homemaking.common.enums.OrderStatusEnum;
import lombok.Data;

/**
 * @author 胡东斌
 * @create 2020-05-28
 */
@Data
public class ContractQueryVO extends PageVO {
    //订单编号
    private String orderCode;
    //客户姓名
    private String customerName;
    //家政师姓名
    private String workerName;
    //订单id
    private Integer orderId;
}
