package com.company.homemaking.consumer.filter;

import com.company.homemaking.consumer.service.CustomerService;
import com.company.homemaking.consumer.vo.user.UserForRedis;
import com.company.homemaking.common.constant.Const;
import com.company.homemaking.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginFilter implements HandlerInterceptor {

    @Autowired
    CustomerService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //执行handler之前执行此方法 true 放行 false 拦截
        String cookieValue = CookieUtils.getCookieValue(request, Const.TOKEN_LOGIN);


        if (StringUtils.isNotBlank(cookieValue)) {
            UserForRedis userInfo = userService.getUserInfo(cookieValue);
            if (userInfo != null) {
                userService.timeDelay(cookieValue);
                return true;
            }
        }
        response.setContentType("text/html;charset=gb2312");
        response.setCharacterEncoding("UTF-8");
        response.sendError(402, "登录超时");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
