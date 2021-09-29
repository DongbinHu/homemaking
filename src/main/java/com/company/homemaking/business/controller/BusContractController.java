package com.company.homemaking.business.controller;


import com.company.homemaking.business.service.BusContractService;
import com.company.homemaking.business.service.BusWorkerService;
import com.company.homemaking.business.utils.ValidatedUtils;
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

import java.util.HashMap;

/**
 * <p>
 * 合同表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Api(description="合同接口")
@Controller
@RequestMapping("/business/contract")
public class BusContractController {



    @Autowired
    BusContractService contractService;

    //按条件查找合同
    @ApiOperation(value = "按条件查找合同", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated ContractQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        return contractService.getContractList(vo);
    }
}
