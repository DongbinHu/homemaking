package com.company.homemaking.consumer.utils;


import com.company.homemaking.common.constant.Const;
import com.company.homemaking.common.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 胡东斌
 * @create 2020-04-15
 */

public class LoginUtils {

    public static String getToken(HttpServletRequest request) {
        return CookieUtils.getCookieValue(request, Const.TOKEN_LOGIN, false);
    }

}
