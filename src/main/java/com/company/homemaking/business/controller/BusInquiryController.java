package com.company.homemaking.business.controller;


import com.company.homemaking.business.service.BusContractService;
import com.company.homemaking.business.service.BusInquiryService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.vo.inquiry.InquiryQueryVO;
import com.company.homemaking.business.vo.order.ContractQueryVO;
import com.company.homemaking.common.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 询价单 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */

@Api(description="议价单接口")
@Controller
@RequestMapping("/business/inquiry")
public class BusInquiryController {




    @Autowired
    BusInquiryService inquiryService;

    //按条件查找议价单
    @ApiOperation(value = "按条件查找议价单", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated InquiryQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        return inquiryService.getInquiryList(vo);
    }

}
