package com.company.homemaking.business.vo.inquiry;

import com.company.homemaking.business.vo.PageVO;
import lombok.Data;

/**
 * @author 胡东斌
 * @create 2020-05-28
 */
@Data
public class InquiryQueryVO extends PageVO {
    //客户电话
    private String customerMobile;
    //客户姓名
    private String customerName;
    //家政师姓名
    private String workerName;
    //家政师编号
    private Integer workerCode;
    //项目编号
    private Integer fkProjectId;
    //项目名称
    private String projectName;
}
