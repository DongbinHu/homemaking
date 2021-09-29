package com.company.homemaking.business.vo.customer;

import com.company.homemaking.business.vo.PageVO;
import com.company.homemaking.common.enums.UserStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 胡东斌
 * @create 2020-05-15
 */
@Data
public class CustomerQueryVO extends PageVO {
    private String trueName;
    private String phoneNumber;
    private UserStatusEnum status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
