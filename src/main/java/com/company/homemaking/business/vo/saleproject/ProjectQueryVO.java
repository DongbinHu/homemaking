package com.company.homemaking.business.vo.saleproject;

import com.company.homemaking.business.vo.PageVO;
import com.company.homemaking.common.enums.OwnerTypeEnum;
import lombok.Data;

/**
 * @author 胡东斌
 * @create 2020-06-01
 */
@Data
public class ProjectQueryVO extends PageVO {
    //名称
    private String name;
    //所属类别
    private String fkCategoryId;
    //销售名称
    private String saleName;
    //所有者类型
    private OwnerTypeEnum ownerType;
}
