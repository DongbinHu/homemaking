package com.company.homemaking.business.vo.serviceitem;

import com.company.homemaking.business.vo.PageVO;
import lombok.Data;

/**
 * @author 胡东斌
 * @create 2020-06-01
 */
@Data
public class ItemQueryVO extends PageVO {

    //项目名称
    private String itemName;
    //工种类别
    private String catType;
    //类别id
    private Integer catId;
}
