package com.company.homemaking.business.vo.fee;

import com.company.homemaking.business.vo.PageVO;
import lombok.Data;

/**
 * @author 胡东斌
 * @create 2020-06-01
 */
@Data
public class FeeQueryVO extends PageVO {

    //订单编号
    private String orderCode;
}
