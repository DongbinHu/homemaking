package com.company.homemaking.business.conf;

import com.company.homemaking.business.entity.BusSysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 胡东斌
 * @create 2019/8/27
 */
@Slf4j
class ShiroLoginFilter extends AdviceFilter {

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        BusSysUser sysUser = (BusSysUser) httpServletRequest.getSession().getAttribute("userInfo");
        String uri = httpServletRequest.getRequestURI();
        //登录了且访问admin-继续往下执行（标识②）
        if(sysUser != null && uri.equals("/admin")){
            return true;
        }
        //路径包含404和登录注册等页面地址-继续往下执行（标识①）
        if(StringUtils.contains(uri,"/business/sys") || StringUtils.contains(uri,"/404")){
            return true;
        }
        //未登录情况
        if (null == sysUser) {
            String requestedWith = httpServletRequest.getHeader("X-Requested-With");
            //ajax情况
            if (StringUtils.isNotEmpty(requestedWith) && StringUtils.equals(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定数据
                log.debug("该请求为ajax请求，并且没登录");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json; charset=utf-8");
                PrintWriter out = null ;
                JSONObject res = new JSONObject();
                res.put("ok",false);
                res.put("msg","访问本页面需要登录");
                res.put("status",402);
                out = response.getWriter();
                out.append(res.toString());
                return false;
            } else {
                //不是ajax进行重定向处理
                //没登录并且不是直接访问admin，重定向到404
                if(!uri.equals("/admin")){
                    httpServletResponse.sendRedirect("/404");
                }else{
                //直接访问admin路径，重定向到登录页，此时回重新走一遍这个过滤器，如果没有上面（标识①）中的配置，会不断重定向
                    httpServletResponse.sendRedirect("/business/sys/toLogin");
                }
                return false;
            }
         //登录并且不是ajax请求，重定向到首页，如果没有上面（标识②）中的配置，会不断重定向
        }else if(sysUser != null && !(StringUtils.isNotEmpty(httpServletRequest.getHeader("X-Requested-With")) && StringUtils.equals(httpServletRequest.getHeader("X-Requested-With"), "XMLHttpRequest"))){
            httpServletResponse.sendRedirect("/admin");
            return false;
        }
        //登录并且是ajax请求，通过此过滤器，继续往下执行
        return true;
    }

}
