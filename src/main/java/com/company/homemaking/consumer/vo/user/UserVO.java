package com.company.homemaking.consumer.vo.user;

import lombok.Data;

/**
 * 存入redis用的user类，此类不应返回给前端，因为包含微信的session_key
 * @author 胡东斌
 * @create 2020-04-14
 */

@Data
public class UserVO {

    private String phoneNumber;
    private Boolean ifAuth;

}
