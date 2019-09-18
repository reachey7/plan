package com.hb.controller;

import org.springframework.stereotype.Controller;

import java.util.List;

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
import com.hb.service.IDormitoryBedStuService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;
import com.hb.util.SysHelperUtil;
import com.hb.entity.DormitoryBedStu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hb.entity.R;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lirc
 * @since 2019-08-12
 */
@Controller
@RequestMapping("/dormitoryBedStu")
public class DormitoryBedStuController {
	private final Logger logger = LoggerFactory.getLogger(DormitoryBedStuController.class);

	@Autowired
	public IDormitoryBedStuService dormitoryBedStuService;

	/**
	* <p>新生入住安排(单个学生办理入住)
	* 1.bed_stu插入数据
	* 2.sys_bed表中的BED_STATUS改1，已入住
	* 3.sys_stu表中的IS_CHECKIN改1，是
	* 参数：stuId,bedId,operatorPersonId,operatorPersonName
	* </p>
	* <p>Company: 和邦科技</p> 
	* @author lirc
	* @date 下午1:45:29
	*/
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public R add(@RequestBody Map<String, Object> paramMap) {
		try {
			// 首先判断必填项
			List<String> list = new ArrayList<>();
			list.add("stuId");
			list.add("bedId");
			list.add("operatorPersonId");
			list.add("operatorPersonName");
			R checkR = SysHelperUtil.check(list, paramMap);
			if (!checkR.isState()) {
				return checkR;
			}

			R result = dormitoryBedStuService.saveBedStu(paramMap);
			return result;
		} catch (Exception e) {
			logger.error("dormitoryBedStuSave -=- {}", e.toString());
			return new R(true, "新增失败", "");
		}
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody DormitoryBedStu dormitoryBedStu) {
		try {
			DormitoryBedStu tmp = dormitoryBedStuService.getById(dormitoryBedStu.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryBedStuService.updateById(dormitoryBedStu);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", dormitoryBedStu);
		} catch (Exception e) {
			logger.error("DormitoryBedStuUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}

	}

	/**
		 * 删除
		 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public R delete(@RequestBody DormitoryBedStu dormitoryBedStu) {
		try {
			DormitoryBedStu tmp = dormitoryBedStuService.getById(dormitoryBedStu.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryBedStuService.removeById(dormitoryBedStu.getId());
			if (!result) {
				return new R(true, "删除失败", "");
			}
			return new R(true, "删除成功", dormitoryBedStu);
		} catch (Exception e) {
			logger.error("dormitoryBedStuDelete -=- {}", e.toString());
			return new R(true, "删除失败", "");
		}
	}

	
	/**
	* <p>查询学生入住窗外情况
	* 1.学生ID：stuId
	* 2.学生学号：stuNo
	* 3.学生姓名：stuName
	* </p>
	* <p>Company: 和邦科技</p> 
	* @author lirc
	* @date 下午5:00:27
	*/
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/query")
	public R query(@RequestBody Map<String, Object> paramMap) {
		try {
			String stuId = (String)paramMap.get("stuId");
			String stuNo = (String)paramMap.get("stuNo");
			String stuName = (String)paramMap.get("stuName") ;

			if (StringUtils.isEmpty(stuId) && StringUtils.isEmpty(stuNo) && StringUtils.isEmpty(stuName)) {
				return new R(false, "学生ID、学号、姓名至少传入一个!", "");
			}

			Page page = null;
			if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
				page = new Page(0, 1);
			} else {
				page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
			}
			page = dormitoryBedStuService.selectBedStu(page, paramMap);
			return new R(true, "查询成功", page);

		} catch (Exception e) {
			logger.error("sysAreaDelete -=- {}", e.toString());
			return new R(true, "查询失败", "");
		}
	}

}