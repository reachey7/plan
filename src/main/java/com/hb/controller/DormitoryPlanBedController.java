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
import com.hb.entity.DormitoryPlanBed;
import com.hb.entity.DormitoryPlanStu;
import com.hb.entity.R;
import com.hb.entity.SysBed;
import com.hb.mapper.DormitoryPlanBedMapper;
import com.hb.mapper.SysBedMapper;
import com.hb.mapper.SysDormMapper;
import com.hb.service.IDormitoryPlanBedService;
import com.hb.service.ISysBedService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;
import com.hb.util.SysHelperUtil;

/**
 *
 * @author lirc
 * @since 2019-08-09
 */
@Controller
@RequestMapping("/dormitoryPlanBed")
public class DormitoryPlanBedController {
	private final Logger logger = LoggerFactory.getLogger(DormitoryPlanBedController.class);

	@Autowired
	public IDormitoryPlanBedService dormitoryPlanBedService;

	@Autowired
	public ISysBedService sysBedService;

	@Autowired
	public SysBedMapper sysBedMapper;
	@Autowired
	public SysDormMapper sysDormMapper;
	@Autowired
	public DormitoryPlanBedMapper dormitoryPlanBedMapper;

	/**
	 * 新增
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public R add(@RequestBody DormitoryPlanBed dormitoryPlanBed) {
		try {
			boolean result = dormitoryPlanBedService.save(dormitoryPlanBed);
			if (!result) {
				return new R(true, "新增失败", "");
			}
		} catch (Exception e) {
			logger.error("dormitoryPlanBedSave -=- {}", e.toString());
			return new R(true, "新增失败", "");
		}
		return new R(true, "新增成功", dormitoryPlanBed);
	}

	/**
	* 新增,将
	*/
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/addList")
	public R addList(@RequestBody Map<String, Object> paramMap) {
		try {
			// 首先判断必填项
			List<String> list = new ArrayList<>();
			list.add("planId");
			list.add("operatorId");

			R checkR = SysHelperUtil.check(list, paramMap);
			if (!checkR.isState()) {
				return checkR;
			}

			String planId = paramMap.get("planId") + "";
			List<String> bedIdList = (List<String>) paramMap.get("bedIdList");

			List<DormitoryPlanBed> planBedList = new ArrayList<DormitoryPlanBed>();
			List<DormitoryPlanBed> planBedListCopy = new ArrayList<DormitoryPlanBed>();

			for (String key : bedIdList) {
				DormitoryPlanBed planBed = new DormitoryPlanBed();
				planBed.setId(IdUtil.createSerialSS(""));
				planBed.setCreateDate(PlugDateUtil.getCurDateTime());
				planBed.setBedId(key);
				planBed.setOperatorId(paramMap.get("operatorId") + "");
				planBed.setPlanId(planId);
				planBed.setState("0");
				planBedList.add(planBed);
			}

			// 根据添加的床位，在plan_bed表中查询是否已经添加过，如果已经包含存在的数据，则将前台提交的list进行移除，然后再批量添加
			QueryWrapper<DormitoryPlanBed> queryBedWrapper = new QueryWrapper<>();
			queryBedWrapper.in("BED_ID", bedIdList);
			queryBedWrapper.eq("PLAN_ID", planId);
			List<DormitoryPlanBed> queryBedWrapperList = dormitoryPlanBedMapper.selectList(queryBedWrapper);

			boolean result = false;
			// 进行剔重
			if (queryBedWrapperList != null && queryBedWrapperList.size() > 0) {
				for (DormitoryPlanBed updps : planBedList) {
					boolean check = true;
					for (DormitoryPlanBed dps : queryBedWrapperList) {
						if (updps.getBedId().equals(dps.getBedId())) {
							check = false;
						}
					}
					if (check) {
						planBedListCopy.add(updps);
					}
				}
				// 查询床位信息
				result = dormitoryPlanBedService.saveOrUpdateBatch(planBedListCopy);
			} else {
				result = dormitoryPlanBedService.saveOrUpdateBatch(planBedList);
				//将sys_bed表中的状态更新为2预占中
				QueryWrapper<SysBed> sysBedWrapper =  new QueryWrapper<>();
				sysBedWrapper.in("ID", bedIdList);
				List<SysBed> upSysBedList = sysBedService.list(sysBedWrapper);
				for(SysBed tmp:upSysBedList){
					tmp.setBedStatus(2);
				}
				sysBedService.saveOrUpdateBatch(upSysBedList);
			}

			if (!result) {
				return new R(false, "新增失败", "");
			}
		} catch (Exception e) {
			logger.error("dormitoryPlanStuSave -=- {}", e.toString());
			return new R(true, "新增失败", "");
		}
		return new R(true, "新增成功", "");
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody DormitoryPlanBed dormitoryPlanBed) {
		try {
			DormitoryPlanBed tmp = dormitoryPlanBedService.getById(dormitoryPlanBed.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryPlanBedService.updateById(dormitoryPlanBed);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", dormitoryPlanBed);
		} catch (Exception e) {
			logger.error("DormitoryPlanBedUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}

	}

	/**
		 * 删除
		 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public R delete(@RequestBody DormitoryPlanBed dormitoryPlanBed) {
		try {
			DormitoryPlanBed tmp = dormitoryPlanBedService.getById(dormitoryPlanBed.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryPlanBedService.removeById(dormitoryPlanBed.getId());
			if (!result) {
				return new R(true, "删除失败", "");
			}
			return new R(true, "删除成功", dormitoryPlanBed);
		} catch (Exception e) {
			logger.error("dormitoryPlanBedDelete -=- {}", e.toString());
			return new R(true, "删除失败", "");
		}
	}

	/**
	 *  查询
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
			page = dormitoryPlanBedService.selectPlanBed(page, paramMap);
			return new R(true, "查询成功", page);
		} catch (Exception e) {
			logger.error("dormitoryPlanBedService -=- {}", e.toString());
			return new R(true, "查询失败", "");
		}
	}

	/**
	 *  查询床位实体
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/queryBed")
	public R queryBed(@RequestBody Map paramMap) {
		try {
			Page page = null;
			if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
				page = new Page(0, 1);
			} else {
				page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
			}
			page = dormitoryPlanBedService.selectSysBed(page, paramMap);
			return new R(true, "查询成功", page);
		} catch (Exception e) {
			logger.error("dormitoryPlanBedService -=- {}", e.toString());
			return new R(true, "查询失败", "");
		}
	}

}