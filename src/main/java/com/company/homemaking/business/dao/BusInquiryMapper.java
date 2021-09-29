package com.company.homemaking.business.dao;

import com.company.homemaking.business.entity.BusInquiry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.homemaking.business.vo.inquiry.InquiryQueryVO;
import com.company.homemaking.business.vo.inquiry.InquiryVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 询价单 Mapper 接口
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Component(value = "BusInquiryMapper")
public interface BusInquiryMapper extends BaseMapper<BusInquiry> {

    List<InquiryVO> getInquiryList(InquiryQueryVO vo);

}
