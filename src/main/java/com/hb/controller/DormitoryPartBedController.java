package com.hb.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPartBed;
import com.hb.entity.DormitoryPlanBed;
import com.hb.entity.R;
import com.hb.service.IDormitoryPartBedService;
import com.hb.service.IDormitoryPlanBedService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;

/**
 * @author lirc
 * @since 2019-08-12
 */
@Controller
@RequestMapping("/dormitoryPartBed")
public class DormitoryPartBedController {
    private final Logger logger = LoggerFactory.getLogger(DormitoryPartBedController.class);

    @Autowired
    public IDormitoryPartBedService dormitoryPartBedService;

    @Autowired
    public IDormitoryPlanBedService dormitoryPlanBedService;

    /**
     * 新增
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public R add(@RequestBody List<DormitoryPartBed> dormitoryPartBedList) {
        try {
            for (DormitoryPartBed dpb : dormitoryPartBedList) {
                boolean result = dormitoryPartBedService.save(dpb);
                if (!result) {
                    return new R(true, "新增失败", "");
                }
            }
        } catch (Exception e) {
            logger.error("dormitoryPartBedSave -=- {}", e.toString());
            return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", null);
    }

    @ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/addList")
	public R addList(@RequestBody Map<String, Object> paramMap) {
		try {

			String partId = paramMap.get("partId") + "";
			String planId = paramMap.get("planId") + "";
			List<String> bedIdList = (List<String>) paramMap.get("bedIdList");

			List<DormitoryPartBed> partBedList = new ArrayList<DormitoryPartBed>();
			List<DormitoryPlanBed> planBedList = new ArrayList<DormitoryPlanBed>();

			for (String key : bedIdList) {
				DormitoryPartBed partBed = new DormitoryPartBed();
				partBed.setId(IdUtil.createSerialSS(""));
				partBed.setCreateDate(PlugDateUtil.getCurDateTime());
				partBed.setBedId(key);
				partBed.setOperatorId(paramMap.get("operatorId") + "");
				partBed.setPartId(partId);
				partBed.setState("0");
				partBedList.add(partBed);

				DormitoryPlanBed planBed = new DormitoryPlanBed();

			}

			// 插入part_bed表
			Boolean result = dormitoryPartBedService.saveOrUpdateBatch(partBedList);

			if (!result) {
				return new R(false, "插入part_bed表", "");
			}
			// 将plan_bed表中的状态更新为1已划分
			QueryWrapper<DormitoryPlanBed> planBedWrapper = new QueryWrapper<>();
			planBedWrapper.in("BED_ID", bedIdList);
			List<DormitoryPlanBed> dormitoryPlanBedList = dormitoryPlanBedService.list(planBedWrapper);
			for (DormitoryPlanBed tmp : dormitoryPlanBedList) {
				tmp.setState("1");
			}
			Boolean planBedResult = dormitoryPlanBedService.saveOrUpdateBatch(dormitoryPlanBedList);
			if (!planBedResult) {
				return new R(false, "更新plan_bed表", "");
			}
		} catch (Exception e) {
			logger.error("dormitoryPlanStuSave -=- {}", e.toString());
			return new R(false, "新增失败", "");
		}
		return new R(true, "新增成功", "");
	}

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public R update(@RequestBody DormitoryPartBed dormitoryPartBed) {
        try {
            DormitoryPartBed tmp = dormitoryPartBedService.getById(dormitoryPartBed.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryPartBedService.updateById(dormitoryPartBed);
            if (!result) {
                return new R(true, "修改失败", "");
            }
            return new R(true, "修改成功", dormitoryPartBed);
        } catch (Exception e) {
            logger.error("DormitoryPartBedUpdate -=- {}", e.toString());
            return new R(true, "修改失败", "");
        }

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public R delete(@RequestBody Map<String, Object> paramMap) {
        try {
        	String bedId = (String)paramMap.get("bedId");
        	String partId = (String)paramMap.get("partId");
        	String planId = (String)paramMap.get("planId");
        	
        
        	QueryWrapper<DormitoryPartBed> queryPsWrapper = new QueryWrapper();
        	queryPsWrapper.eq("BED_ID", bedId);
        	queryPsWrapper.eq("PART_ID", partId);
        	DormitoryPartBed dpsTmp = dormitoryPartBedService.getOne(queryPsWrapper);
        	

            Boolean result = dormitoryPartBedService.removeById(dpsTmp);
            if (!result) {
                return new R(true, "删除失败", "");
            }
            
            //修改plan_bed表中的状态未0
            QueryWrapper<DormitoryPlanBed> queryPbWrapper = new QueryWrapper();
            queryPbWrapper.eq("BED_ID", bedId);
            queryPbWrapper.eq("PLAN_ID", planId);
        	DormitoryPlanBed pbTmp = dormitoryPlanBedService.getOne(queryPbWrapper);
        	pbTmp.setState("0");
        	dormitoryPlanBedService.saveOrUpdate(pbTmp);
            
            return new R(true, "删除成功", "");
        } catch (Exception e) {
            logger.error("dormitoryPartBedDelete -=- {}", e.toString());
            return new R(true, "删除失败", "");
        }
    }

    /**
     * 查询
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/query")
    public R query(@RequestBody Map paramMap) {
        try {
            Page page = null;
            if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
                page = new Page(0, 1);
            } else {
                page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
            }
            page = dormitoryPartBedService.selectPartBed(page,paramMap);
            return new R(true, "查询成功", page);
        } catch (Exception e) {
            logger.error("dormitoryPlanBedService -=- {}", e.toString());
            return new R(true, "查询失败", "");
        }
    }


}