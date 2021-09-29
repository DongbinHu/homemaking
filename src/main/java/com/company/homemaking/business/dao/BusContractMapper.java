package com.company.homemaking.business.dao;

import com.company.homemaking.business.entity.BusContract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.homemaking.business.vo.order.ContractQueryVO;
import com.company.homemaking.business.vo.order.ContractVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 合同表 Mapper 接口
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
@Component(value = "BusContractMapper")
public interface BusContractMapper extends BaseMapper<BusContract> {

    List<ContractVO> getContractList(ContractQueryVO vo);
}
