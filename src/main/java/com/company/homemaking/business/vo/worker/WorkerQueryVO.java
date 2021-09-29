package com.company.homemaking.business.vo.worker;

import com.company.homemaking.business.vo.PageVO;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 胡东斌
 * @create 2020-05-26
 */
@Data
public class WorkerQueryVO extends PageVO {
    //编号
    private Integer code;
    //手机号
    private String phoneNumber;
    //从事项目
    private String workProject;
    //注册时间开始 ("2020-05-22")
    private String startDate;
    //注册时间结束 ("2020-05-22")
    private String endDate;
}
