package com.company.homemaking.business.utils;


import org.apache.shiro.subject.Subject;

/**
 * @author 胡东斌
 * @create 2020-03-05
 */

public class AuthenticationUtils {

    public static boolean AuthCommon(String requireRole, Subject subject){
        if(subject.hasRole(requireRole) || subject.hasRole("管理员")){
            return true;
        }
        return false;
    }
}
