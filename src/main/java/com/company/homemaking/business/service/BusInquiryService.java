package com.company.homemaking.business.service;

import com.company.homemaking.business.entity.BusInquiry;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.homemaking.business.vo.inquiry.InquiryQueryVO;
import com.company.homemaking.common.pojo.JSONResult;

/**
 * <p>
 * 询价单 服务类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
public interface BusInquiryService extends IService<BusInquiry> {

    JSONResult getInquiryList(InquiryQueryVO vo);
}
