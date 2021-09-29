package com.company.homemaking.business.service.impl;

import com.company.homemaking.business.entity.BusContract;
import com.company.homemaking.business.dao.BusContractMapper;
import com.company.homemaking.business.service.BusContractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.homemaking.business.vo.order.ContractQueryVO;
import com.company.homemaking.business.vo.order.ContractVO;
import com.company.homemaking.common.pojo.JSONResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 合同表 服务实现类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Service
public class BusContractServiceImpl extends ServiceImpl<BusContractMapper, BusContract> implements BusContractService {

    @Autowired
    private BusContractMapper contractMapper;

    @Override
    public JSONResult getContractList(ContractQueryVO vo){
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<ContractVO> list = contractMapper.getContractList(vo);
        PageInfo<ContractVO> pageInfo = new PageInfo<>(list);
        map.put("pageNum", pageInfo.getPageNum());//当前页码
        map.put("pageSize", vo.getPageSize());//每页条数
        map.put("total", pageInfo.getTotal());//总记录数
        map.put("list", list);//数据集合
        return JSONResult.ok(map);
    }
}
