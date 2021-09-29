package com.company.homemaking.business.service;

import com.company.homemaking.business.entity.BusContract;
import com.baomidou.mybatisplus.extension.service.IService;
import com.company.homemaking.business.vo.order.ContractQueryVO;
import com.company.homemaking.common.pojo.JSONResult;


/**
 * <p>
 * 合同表 服务类
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-14
 */
public interface BusContractService extends IService<BusContract> {

    JSONResult getContractList(ContractQueryVO vo);

}
