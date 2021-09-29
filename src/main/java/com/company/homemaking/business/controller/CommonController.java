package com.company.homemaking.business.controller;


import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.vo.PictureUploadVO;
import com.company.homemaking.common.pojo.JSONResult;
import com.company.homemaking.common.utils.Base64ConvertUtil;
import com.company.homemaking.common.utils.OSSClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-04-03
 */
@Slf4j
@Api(description="公用接口")
@Controller
@RequestMapping("/business/common")
public class CommonController {

    //上传图片
    @ApiOperation(value = "上传图片", notes = "")
    @RequestMapping(value = "/uploadImg", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult uploadImg(@RequestBody @Validated PictureUploadVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        MultipartFile picFile = Base64ConvertUtil.base64ToMultipart(vo.getBase64Str());
        if(picFile == null){
            return JSONResult.errorMsg("base64转换失败");
        }
        return OSSClientUtil.uploadImg2Oss(picFile,vo.getImgType());
    }
}
