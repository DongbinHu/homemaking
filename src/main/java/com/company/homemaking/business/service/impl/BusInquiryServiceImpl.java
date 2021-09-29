package com.company.homemaking.business.service.impl;

import com.company.homemaking.business.entity.BusInquiry;
import com.company.homemaking.business.dao.BusInquiryMapper;
import com.company.homemaking.business.service.BusInquiryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.homemaking.business.vo.inquiry.InquiryQueryVO;
import com.company.homemaking.business.vo.inquiry.InquiryVO;
import com.company.homemaking.common.pojo.JSONResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 询价单 服务实现类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Service
public class BusInquiryServiceImpl extends ServiceImpl<BusInquiryMapper, BusInquiry> implements BusInquiryService {

    @Autowired
    private BusInquiryMapper inquiryMapper;

    @Override
    public JSONResult getInquiryList(InquiryQueryVO vo){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<InquiryVO> list = inquiryMapper.getInquiryList(vo);
        PageInfo<InquiryVO> pageInfo = new PageInfo<>(list);
        map.put("pageNum", pageInfo.getPageNum());//当前页码
        map.put("pageSize", vo.getPageSize());//每页条数
        map.put("total", pageInfo.getTotal());//总记录数
        map.put("list", list);//数据集合
        return JSONResult.ok(map);
    }
}
