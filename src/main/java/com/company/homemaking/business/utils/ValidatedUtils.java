package com.company.homemaking.business.utils;

import com.company.homemaking.common.pojo.JSONResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @author 胡东斌
 * @create 2020-04-13
 */

public class ValidatedUtils {

    public static JSONResult validData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }
            return JSONResult.errorMsg(sb.toString());
        }
        return JSONResult.ok();
    }
}
