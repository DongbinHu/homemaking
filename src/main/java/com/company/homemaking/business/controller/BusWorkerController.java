package com.company.homemaking.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.homemaking.business.entity.BusOrder;
import com.company.homemaking.business.entity.BusWorker;
import com.company.homemaking.business.entity.BusWorkerBaojieInfo;
import com.company.homemaking.business.service.BusOrderService;
import com.company.homemaking.business.service.BusWorkerBaojieInfoService;
import com.company.homemaking.business.service.BusWorkerService;
import com.company.homemaking.business.utils.ValidatedUtils;
import com.company.homemaking.business.validation.service.GroupEdit;
import com.company.homemaking.business.vo.worker.BaojieVO;
import com.company.homemaking.business.vo.worker.WorkerDetailVO;
import com.company.homemaking.business.vo.worker.WorkerQueryVO;
import com.company.homemaking.business.vo.worker.WorkerVO;
import com.company.homemaking.common.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 家政师 前端控制器
 * </p>
 *
 * @author liubangzi
 * @since 2020-05-15
 */
@Slf4j
@Api(description="家政师接口")
@Controller
@RequestMapping("/business/worker")
public class BusWorkerController {

    @Autowired
    BusWorkerService workerService;
    @Autowired
    BusWorkerBaojieInfoService baojieInfoService;
    @Autowired
    BusOrderService orderService;

    //添加家政师
    @ApiOperation(value = "添加家政师", notes = "")
    @RequestMapping(value = "/addItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult addItem(@RequestBody @Validated WorkerVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusWorker item = new BusWorker();
        BeanUtils.copyProperties(vo, item);
        boolean result = workerService.save(item);
        if(!result){
            return JSONResult.errorMsg("添加失败");
        }
        //往各个属性表添加一条关联数据
        //保洁
        BusWorkerBaojieInfo baojie = new BusWorkerBaojieInfo();
        baojie.setFkWorkerId(item.getId());
        boolean projectResult1 = baojieInfoService.save(baojie);
        if(!projectResult1){
            return JSONResult.errorMsg("添加保洁属性失败");
        }

        return JSONResult.ok();
    }

    //删除家政师
    @ApiOperation(value = "删除家政师", notes = "")
    @RequestMapping(value = "/deleteItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult deleteItem(@RequestParam(name = "id") Integer id) {
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        BusWorker item = new BusWorker();
        item.setId(id);
        item.setIfDelete(true);
        boolean result = workerService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("删除失败");
    }

    //修改家政师资料、状态
    @ApiOperation(value = "修改家政师资料、状态", notes = "")
    @RequestMapping(value = "/editItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editItem(@RequestBody @Validated({GroupEdit.class, Default.class}) WorkerVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusWorker item = new BusWorker();
        BeanUtils.copyProperties(vo, item);
        boolean result = workerService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("修改失败");
    }

    //修改保洁人员信息
    @ApiOperation(value = "修改保洁人员信息", notes = "")
    @RequestMapping(value = "/editBaojieItem", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult editBaojieItem(@RequestBody @Validated BaojieVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        BusWorkerBaojieInfo item = new BusWorkerBaojieInfo();
        BeanUtils.copyProperties(vo, item, new String[]{"fkWorkerId"});
        System.out.println(item);
        boolean result = baojieInfoService.updateById(item);
        return result ? JSONResult.ok() : JSONResult.errorMsg("修改失败");
    }

    //按条件查找家政师
    @ApiOperation(value = "按条件查找家政师", notes = "")
    @RequestMapping(value = "/getItemList", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemList(@RequestBody @Validated WorkerQueryVO vo, BindingResult bindingResult) {
        JSONResult validResult = ValidatedUtils.validData(bindingResult);
        if(!validResult.isOK()){
            return validResult;
        }
        Page<BusWorker> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        LambdaQueryWrapper<BusWorker> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(vo.getWorkProject())){
            queryWrapper.like(BusWorker::getWorkProject, vo.getWorkProject());
        }
        if(vo.getCode() != null){
            queryWrapper.eq(BusWorker::getCode, vo.getCode());
        }
        if(StringUtils.isNotBlank(vo.getPhoneNumber())){
            queryWrapper.like(BusWorker::getPhoneNumber, vo.getPhoneNumber());
        }
        if(vo.getStartDate() != null && vo.getEndDate() != null){
            queryWrapper.between(BusWorker::getRegisterDate, vo.getStartDate(),vo.getEndDate());
        }
        queryWrapper.eq(BusWorker::getIfDelete,false);
        IPage<BusWorker> itemIPage = workerService.page(page, queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",itemIPage.getRecords());
        map.put("total",itemIPage.getTotal());
        map.put("pageNum",itemIPage.getCurrent());
        map.put("pageSize",itemIPage.getSize());
        return JSONResult.ok(map);
    }

    //获取家政师详情
    @ApiOperation(value = "获取家政师详情", notes = "")
    @RequestMapping(value = "/getItemDetail", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemDetail(@RequestParam(name = "id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        if(id == null){
            return JSONResult.errorMsg("id不能为空");
        }
        BusWorker item = workerService.getById(id);
        WorkerDetailVO vo = new WorkerDetailVO();
        if(item == null || item.getIfDelete()){
            return JSONResult.errorMsg("没有此家政师");
        }
        //基本信息
        BeanUtils.copyProperties(item, vo);
        map.put("detail",vo);
        //附加信息
        if(item.getWorkProject().contains("保洁")){
            LambdaQueryWrapper<BusWorkerBaojieInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BusWorkerBaojieInfo::getFkWorkerId,id);
            List<BusWorkerBaojieInfo> baojie = baojieInfoService.list(queryWrapper);
            map.put("baojie",baojie.get(0));
        }

        //订单部分
        LambdaQueryWrapper<BusOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusOrder::getFkWorkerId,id);
        List<BusOrder> orders = orderService.list(queryWrapper);
        map.put("orderList",orders);

        return JSONResult.ok(map);
    }


    //家政师下拉选
    @ApiOperation(value = "家政师下拉选", notes = "")
    @RequestMapping(value = "/getItemSelect", method = {RequestMethod.POST})
    @ResponseBody
    public JSONResult getItemSelect() {
        LambdaQueryWrapper<BusWorker> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusWorker::getIfDelete,false);
        queryWrapper.eq(BusWorker::getStatus,2);
        List<BusWorker> list = workerService.list(queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("select",list);
        return JSONResult.ok(map);
    }
}
