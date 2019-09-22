package com.hb.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPart;
import com.hb.entity.DormitoryPartBed;
import com.hb.entity.DormitoryPartStu;
import com.hb.entity.DormitoryPlan;
import com.hb.entity.DormitoryStuBedPlan;
import com.hb.entity.R;
import com.hb.entity.SysBed;
import com.hb.mapper.SysBedMapper;
import com.hb.service.IDormitoryPartBedService;
import com.hb.service.IDormitoryPartService;
import com.hb.service.IDormitoryPartStuService;
import com.hb.service.IDormitoryPlanBedService;
import com.hb.service.IDormitoryPlanService;
import com.hb.service.IDormitoryPlanStuService;
import com.hb.service.IDormitoryStuBedPlanService;
import com.hb.service.ISysBedService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;
import com.hb.util.SysHelperUtil;

/**
 * @author lirc
 * @since 2019-08-12
 */
@Controller
@RequestMapping("/dormitoryStuBedPlan")
public class DormitoryStuBedPlanController {
	private final Logger logger = LoggerFactory.getLogger(DormitoryStuBedPlanController.class);

	@Autowired
	public IDormitoryStuBedPlanService dormitoryStuBedPlanService;
	@Autowired
	public IDormitoryPlanStuService dormitoryPlanStuService;
	@Autowired
	public IDormitoryPlanBedService dormitoryPlanBedService;
	@Autowired
	public IDormitoryPartStuService dormitoryPartStuService;
	@Autowired
	public IDormitoryPartBedService dormitoryPartBedService;
	@Autowired
	public IDormitoryPlanService dormitoryPlanService;
	@Autowired
	public IDormitoryPartService dormitoryPartService;
	@Autowired
	public ISysBedService sysBedService;
	@Autowired
	public SysBedMapper sysBedMapper;
	@Autowired
	private RestTemplate restTemplate;
	/*
	 * @Autowired public DormitoryStuBedPlanMapper dormitoryStuBedPlanMapper;
	 * 
	 * @Autowired public DormitoryPlanStuMapper dormitoryPlanStuMapper;
	 * 
	 * @Autowired public DormitoryPlanBedMapper dormitoryPlanBedMapper;
	 * 
	 * @Autowired public DormitoryPartStuMapper dormitoryPartStuMapper;
	 * 
	 * @Autowired public DormitoryPartBedMapper dormitoryPartBedMapper;
	 */

	/**
	 * 新增
	 * 1.前台将学生列表、床位列表、划分ID、操作人、计划ID、分配类型(0:PC,1:H5)以及分配规则传入
	 * 2.服务端根据学生列表以及床位列表分别去dormitory_part_stu、dormitory_part_bed两张表，按照分配规则排序查出两列结果
	 * 3.按照排序好的两个列表进行插入dormitory_stu_bed_plan
	 * 4.更新dormitory_part:PART_STATUS,STUDENT_NUMBER,BED_NUMBER
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public R add(@RequestBody Map paramMap) {
		try {
			// 首先判断必填项
			List<String> list = new ArrayList<>();
			list.add("partId");
			list.add("planId");
			list.add("studentList");
			list.add("bedList");
			list.add("operatorPersonId");
			list.add("operatorPersonName");
			list.add("type");
			list.add("fenpeiRule");

			Map<String, Object> fenpeiInfo = (Map<String, Object>) paramMap.get("fenpeiRule");
			List<String> keyList = new ArrayList<String>();
			for (String key : fenpeiInfo.keySet()) {
				keyList.add(String.valueOf(fenpeiInfo.get(key)));
			}
			Collections.sort(keyList);
			StringBuffer fenpeiRule = new StringBuffer();
			for (String key : keyList) {
				for (String k : fenpeiInfo.keySet()) {
					if (key.equals(String.valueOf(fenpeiInfo.get(k)))) {
						fenpeiRule.append(k + ",");
					}
				}
			}
			fenpeiRule.deleteCharAt(fenpeiRule.length() - 1);

			R checkR = SysHelperUtil.check(list, paramMap);
			if (!checkR.isState()) {
				return checkR;
			}

			// 判断学生列表与床位列表长度是否大于等于1
			if (((List) paramMap.get("studentList")).size() < 1 || ((List) paramMap.get("bedList")).size() < 1) {
				return new R(false, "选择的学生列表和床位列表不能为空!", "");
			}

			// 判断床位数量是否大于学生数量
			if (((List) paramMap.get("studentList")).size() > ((List) paramMap.get("bedList")).size()) {
				return new R(false, "选择的学生数量一定要小于床位数量!", "");
			}

			// 根据分配规则查询学生列表
			Page partStuPage = new Page(0, 9999999);
			Map<String, Object> partStuParamMap = new HashMap<>();
			partStuParamMap.put("partId", (String) paramMap.get("partId"));
			partStuParamMap.put("fenpeiRule", fenpeiRule.toString());

			StringBuffer studentListStr = new StringBuffer();
			List<String> studentList = (List) paramMap.get("studentList");
			for (String s : studentList) {
				studentListStr.append("\"" + s + "\"" + ",");
			}
			studentListStr.deleteCharAt(studentListStr.length() - 1);
			partStuParamMap.put("studentIdList", "(" + studentListStr + ")");

			partStuPage = dormitoryPartStuService.selectPartStu(partStuPage, partStuParamMap);
			if (partStuPage == null || partStuPage.getSize() <= 0) {
				return new R(false, "选择的学生列表未在数据中找到!", "");

			}

			// 根据床位排序查询床位列表
			Page partBedPage = new Page(0, 9999999);
			Map<String, Object> planBedParamMap = new HashMap<>();
			planBedParamMap.put("partId", (String) paramMap.get("partId"));

			StringBuffer bedListStr = new StringBuffer();
			List<String> bedList = (List) paramMap.get("bedList");
			for (String s : bedList) {
				bedListStr.append("\"" + s + "\"" + ",");
			}
			bedListStr.deleteCharAt(bedListStr.length() - 1);
			planBedParamMap.put("bedIdList", "(" + bedListStr + ")");
			partBedPage = dormitoryPartBedService.selectPartBed(partBedPage, planBedParamMap);
			if (partBedPage == null || partBedPage.getSize() <= 0) {
				return new R(false, "选择的床位列表未在数据中找到!", "");
			}

			// 将学生列表和床位列表匹配，并插入数据库中
			List<DormitoryStuBedPlan> stuBedPlanList = new ArrayList();

			for (int i = 0; i < partStuPage.getRecords().size(); i++) {
				DormitoryStuBedPlan dsbp = new DormitoryStuBedPlan();
				dsbp.setId(IdUtil.createSerialSS(""));
				dsbp.setBedId((String) ((Map<String, Object>) partBedPage.getRecords().get(i)).get("BED_ID"));
				dsbp.setCreateDate(PlugDateUtil.getCurDateTimeHS());
				dsbp.setOperatorPersonId(paramMap.get("operatorPersonId") + "");
				dsbp.setOperatorPersonName(paramMap.get("operatorPersonName") + "");
				dsbp.setPartId((String) ((Map<String, Object>) partBedPage.getRecords().get(i)).get("PART_ID"));
				dsbp.setPlanId((String) paramMap.get("planId"));
				dsbp.setStuId((String) ((Map<String, Object>) partStuPage.getRecords().get(i)).get("ID"));
				dsbp.setType(paramMap.get("type") + "");
				dsbp.setState("0");
				stuBedPlanList.add(dsbp);
			}
			// 批量插入数据库中
			Boolean result = dormitoryStuBedPlanService.saveOrUpdateBatch(stuBedPlanList);

			if (result) {

				// 更新dormitory_part:PART_STATUS,STUDENT_NUMBER,BED_NUMBER

				QueryWrapper<DormitoryPartStu> partStuWrapper = new QueryWrapper<>();
				partStuWrapper.eq("PART_ID", paramMap.get("partId") + "");
				partStuWrapper.in("STUDENT_ID", studentList);
				List<DormitoryPartStu> partStuListUpdate = dormitoryPartStuService.list(partStuWrapper);
				for (DormitoryPartStu tmp : partStuListUpdate) {
					tmp.setState("1");
				}
				dormitoryPartStuService.saveOrUpdateBatch(partStuListUpdate);

				// 查出该划分下面多少个床位
				QueryWrapper<DormitoryPartBed> partBedWrapper = new QueryWrapper<>();
				partBedWrapper.eq("PART_ID", paramMap.get("partId") + "");
				partBedWrapper.in("BED_ID", bedList);
				List<DormitoryPartBed> partBedListUpdate = dormitoryPartBedService.list(partBedWrapper);
				for (DormitoryPartBed tmp : partBedListUpdate) {
					tmp.setState("1");
				}
				dormitoryPartBedService.saveOrUpdateBatch(partBedListUpdate);

				// 判断该划分下是否还有学生未分床位，如果学生已经完成分配则直接调用划分结束工作流，并释放未分配的床位状态
				partStuWrapper = new QueryWrapper<>();
				partStuWrapper.eq("PART_ID", paramMap.get("partId") + "");
				partStuWrapper.in("STATE", "0");
				Integer count = dormitoryPartStuService.list(partStuWrapper).size();
				if (count <= 0) {
					DormitoryPart part = dormitoryPartService.getById(paramMap.get("partId") + "");
					DormitoryPlan plan = dormitoryPlanService.getById(part.getPlanId());

					// 调用工作流
					Map<String, Object> activityMap = new HashMap<String, Object>();
					activityMap.put("partId", paramMap.get("partId") + "");
					activityMap.put("planId", paramMap.get("planId") + "");
					activityMap.put("currentPersonId", paramMap.get("operatorPersonId") + "");
					activityMap.put("taskId", part.getTaskId());
					activityMap.put("activityId", plan.getActivityId());

					R activityR = innerAssignSubmit(activityMap);
					if (!activityR.isState()) {
						return activityR;
					}
					// 将未分配的床位进行释放
					// 查询该划分下状态为0的床位,并将状态改为2:未处理。同时将sys_bed表中状态改为已占用
					partBedWrapper = new QueryWrapper<>();
					partBedWrapper.eq("STATE", "0");
					partBedWrapper.eq("PART_ID", paramMap.get("partId") + "");
					partBedListUpdate = dormitoryPartBedService.list(partBedWrapper);
					List<String> bedUpdateList = new ArrayList<String>();
					for (DormitoryPartBed tmp : partBedListUpdate) {
						tmp.setState("2");
						bedUpdateList.add(tmp.getBedId());
					}
					dormitoryPartBedService.saveOrUpdateBatch(partBedListUpdate);

					// 更新sys_bed表中的状态
					QueryWrapper<SysBed> sysBedWrapper = new QueryWrapper<>();
					sysBedWrapper.eq("BED_CODE", bedUpdateList);
					List<SysBed> sysBedList = sysBedMapper.selectList(sysBedWrapper);
					for (SysBed tmp : sysBedList) {
						tmp.setBedStatus(1);
					}
					Boolean resultSysBed = sysBedService.saveOrUpdateBatch(sysBedList);
					if (resultSysBed) {
						return new R(true, "操作成功", "");
					} else {
						return new R(false, "操作失败", "");
					}
				}

				return new R(true, "操作成功", "");
			} else {
				return new R(false, "操作失败", "");
			}

		} catch (Exception e) {
			logger.error("dormitoryStuBedPlanSave -=- {}", e.toString());
			return new R(true, "新增失败", "");
		}
	}

	/**为H5页面提供学生自选床位，查询空闲床位的接口
	 * 参数：stuId
	* <p>Description: </p>
	* <p>Company: 和邦科技</p> 
	* @author lirc
	* @date 上午11:04:56
	*/
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/queryBedForPc")
	public R queryBedForPc(@RequestBody Map paramMap) {
		try {
			Page page = null;
			if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
				page = new Page(0, 1);
			} else {
				page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
			}
			page = dormitoryPartBedService.selectPartBedByStu(page, paramMap);
			return new R(true, "操作成功", page);

		} catch (Exception e) {
			logger.error("dormitoryStuBedPlanSave -=- {}", e.toString());
			return new R(true, "新增失败", "");
		}
	}

	/**为H5页面提供学生自选床位的接口
	 * 参数partId，studentId,bedId
	* <p>Description: </p>
	* <p>Company: 和邦科技</p> 
	* @author lirc
	* @date 上午11:05:48
	*/
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/addForPc")
	public R addForPc(@RequestBody Map paramMap) {
		try {
			// 首先判断必填项
			List<String> list = new ArrayList<>();
			list.add("partId");
			list.add("studentId");
			list.add("bedId");

			R checkR = SysHelperUtil.check(list, paramMap);
			if (!checkR.isState()) {
				return checkR;
			}

			// 校验partId是否存在
			DormitoryPart dpart = dormitoryPartService.getById((String) paramMap.get("partId") + "");
			if (dpart == null) {
				return new R(false, "根据partId没找到对应part数据", "");
			}

			// 将学生列表和床位列表匹配，并插入数据库中

			DormitoryStuBedPlan dsbp = new DormitoryStuBedPlan();
			dsbp.setId(IdUtil.createSerialSS(""));
			dsbp.setBedId(paramMap.get("bedId") + "");
			dsbp.setCreateDate(PlugDateUtil.getCurDateTimeHS());
			dsbp.setOperatorPersonId("stuAuto");
			dsbp.setPartId(paramMap.get("partId") + "");
			dsbp.setPlanId(paramMap.get("planId") + "");
			dsbp.setStuId(paramMap.get("studentId") + "");
			dsbp.setState("0");
			dsbp.setType("1");
			Boolean result = dormitoryStuBedPlanService.save(dsbp);
			if (result) {
				// 更新dormitory_part:PART_STATUS,STUDENT_NUMBER,BED_NUMBER
				QueryWrapper<DormitoryPartStu> partStuWrapper = new QueryWrapper<>();
				partStuWrapper.eq("PART_ID", paramMap.get("partId") + "");
				partStuWrapper.eq("STUDENT_ID", paramMap.get("studentId") + "");
				DormitoryPartStu partStuUpdate = dormitoryPartStuService.getOne(partStuWrapper);
				partStuUpdate.setState("1");
				Boolean partStuUpdateResult = dormitoryPartStuService.saveOrUpdate(partStuUpdate);
				if (!partStuUpdateResult) {
					return new R(false, "更新part_stu状态失败", "");
				}
			}
			return new R(true, "操作成功", "");

		} catch (

		Exception e) {
			logger.error("dormitoryStuBedPlanSave -=- {}", e.toString());
			return new R(true, "新增失败", "");
		}
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody DormitoryStuBedPlan dormitoryStuBedPlan) {
		try {
			DormitoryStuBedPlan tmp = dormitoryStuBedPlanService.getById(dormitoryStuBedPlan.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryStuBedPlanService.updateById(dormitoryStuBedPlan);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", dormitoryStuBedPlan);
		} catch (Exception e) {
			logger.error("DormitoryStuBedPlanUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}

	}

	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public R delete(@RequestBody DormitoryStuBedPlan dormitoryStuBedPlan) {
		try {
			DormitoryStuBedPlan tmp = dormitoryStuBedPlanService.getById(dormitoryStuBedPlan.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryStuBedPlanService.removeById(dormitoryStuBedPlan.getId());
			if (!result) {
				return new R(true, "删除失败", "");
			}
			return new R(true, "删除成功", dormitoryStuBedPlan);
		} catch (Exception e) {
			logger.error("dormitoryStuBedPlanDelete -=- {}", e.toString());
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
			try {
				Page page = null;
				if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
					page = new Page(0, 1);
				} else {
					page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
				}
				page = dormitoryStuBedPlanService.selectStuBedPlan(page, paramMap);
				return new R(true, "查询成功", page);
			} catch (Exception e) {
				logger.error("dormitoryPlanStudentService -=- {}", e.toString());
				return new R(true, "查询失败", "");
			}
		} catch (Exception e) {
			logger.error("sysAreaDelete -=- {}", e.toString());
			return new R(true, "查询失败", "");
		}
	}

	/**
	 * 分配完成，调用微服务的工作流，接受工作流返回的信息并记录数据库
	 * 参数包括
	 * 工作流ID：activityId
	 * taskID
	 * 计划ID：planId
	 * 当前操作人：currentPersonId和currentPersonName
	 * 下一环节操作人：nextPersonId和nextPersonName
	 * <p>
	 * 方法流程：
	 * 1.首先判断planId，当前操作人，下一环节操作人是否传入
	 * 2.判断planId是否在数据库存在
	 * 3.调用工作流微服务
	 * 4.根据工作流返回的结果判断是否成功，
	 * 4.1如果成功则更新dormitory_plan表中的PLAN_STATUS，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
	 * 4.2如果成功则更新dormitory_part表中的PART_STATUS为2，已完成
	 * 如果失败则返回给前台，允许前台再次调用
	 *
	 * @author lirc
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/assignSubmit")
	public R assignSubmit(@RequestBody Map<String, Object> paramMap) {
		try {

			// 首先判断必填项
			List<String> list = new ArrayList<>();
			list.add("partId");
			list.add("planId");
			list.add("currentPersonId");
			list.add("taskId");
			list.add("activityId");
			R checkR = SysHelperUtil.check(list, paramMap);
			if (!checkR.isState()) {
				return checkR;
			}
			return innerAssignSubmit(paramMap);
		} catch (Exception e) {
			logger.error("操作失败-=- {}", e.toString());
			return new R(false, "操作失败", e.toString());
		}
	}

	private R innerAssignSubmit(Map<String, Object> paramMap) {
		// 判断传入的planId是否存在
		DormitoryPlan dormitory = dormitoryPlanService.getById((String) paramMap.get("planId"));
		if (dormitory == null) {
			return new R(false, "根据planId未找到计划数据", "");
		}

		// 调用微服务工作流
		JSONObject activityJb = new JSONObject();
		activityJb.put("currentPersonId", (String) paramMap.get("currentPersonId"));
		activityJb.put("activityId", (String) paramMap.get("activityId"));
		activityJb.put("taskId", (String) paramMap.get("taskId"));
		R activityCallR = SysHelperUtil.callActivity(activityJb, restTemplate, "fenpeiSubmit");
		if (!activityCallR.isState()) {
			return new R(false, "调用微服务工作流失败", "");
		}

		// 调用微服务成功，更新dormitory_part表中的PART_STATUS，NEXT_PERSON_ID，NEXT_PERSON_NAME，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
		JSONObject jbresult = JSONObject.parseObject((String) activityCallR.getResult());
		String planStatus = jbresult.getString("status");
		dormitory.setPlanStatus(planStatus);
		dormitory.setCurrentPersonId(jbresult.getString("currentPersonId"));
		Boolean saveReslt = dormitoryPlanService.saveOrUpdate(dormitory);
		if (!saveReslt) {
			return new R(false, "调用工作流程后回更失败", "");
		}

		// 如果成功则更新dormitory_part表中的PART_STATUS为2，已完成
		DormitoryPart dormitoryPart = dormitoryPartService.getById((String) paramMap.get("partId"));
		dormitoryPart.setPartStatus("2");
		Boolean savepartReslt = dormitoryPartService.saveOrUpdate(dormitoryPart);
		if (!savepartReslt) {
			return new R(false, "调用工作流程后回更失败", "");
		}
		return new R(true, "操作成功", "");
	}

}