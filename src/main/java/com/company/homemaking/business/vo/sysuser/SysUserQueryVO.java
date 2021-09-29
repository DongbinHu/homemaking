package com.company.homemaking.business.vo.sysuser;

import com.company.homemaking.business.vo.PageVO;
import com.company.homemaking.common.enums.SysUserStatusEnum;
import lombok.Data;

/**
 * @author 胡东斌
 * @create 2020-04-08
 */
@Data
public class SysUserQueryVO extends PageVO {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 状态(1:正常2:冻结3:失效)
     */
    private SysUserStatusEnum status;
}
