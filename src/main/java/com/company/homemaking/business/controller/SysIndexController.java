package com.company.homemaking.business.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author 胡东斌
 * @create 2020/4/8
 */

@Api(description = "后台首页接口")
@Slf4j
@Controller
public class SysIndexController {

    @RequestMapping(value = "/admin", method = {RequestMethod.GET})
    public String toLogin(Model model) {
        return "system/index";
    }

    @RequestMapping(value = "/404", method = {RequestMethod.GET})
    public String toError(Model model) {
        return "404";
    }

}
