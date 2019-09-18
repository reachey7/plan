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
import com.hb.service.IDormitoryDormStuChangeService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;
import com.hb.util.SysHelperUtil;
import com.hb.entity.DormitoryDormStuChange;
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
 * @since 2019-09-18
 */
@Controller
@RequestMapping("/dormitoryDormStuChange")
public class DormitoryDormStuChangeController {
	private final Logger logger = LoggerFactory.getLogger(DormitoryDormStuChangeController.class);

	@Autowired
	public IDormitoryDormStuChangeService dormitoryDormStuChangeService;

	/**
	* <p>学生办理入住:
	* 1.dorm_stu_change表中插入数据
	* 2.将原来的床位释放，sys_bed表中的BED_STATUS改为0，空闲
	* 3.将新床位改为1，已入住
	* 
	* 参数为：
	* 1.学生ID：stu_id，必填
	* 2.老床位ID：beforeBedId,必填
	* 3.新床位ID：afterBedId，必填
	* 2.老宿舍ID：beforeDormId,必填
	* 3.新宿舍ID：afterDormId，必填
	* 4.操作人ID：operatorPersonId，必填
	* 5.操作人姓名：operatorPersonName，必填
	* 6.调换原因编码：updateReasonCode，参数表中定义，必填
	* 7.调换原因名称：updateReasonName，参数表中定义，必填
	* 8.备注：remark，非必填
	* </p>
	* <p>Company: 和邦科技</p> 
	* @author lirc
	* @date 上午10:15:33
	*/
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public R add(@RequestBody Map<String, Object> paramMap) {
		try {
			// 首先判断必填项
			List<String> list = new ArrayList<>();
			list.add("stuId");
			list.add("afterBedId");
			list.add("beforeBedId");
			list.add("afterDormId");
			list.add("beforeDormId");
			list.add("operatorPersonId");
			list.add("operatorPersonName");
			list.add("updateReasonCode");
			list.add("updateReasonName");
			
			R checkR = SysHelperUtil.check(list, paramMap);
			if (!checkR.isState()) {
				return checkR;
			}

			R result = dormitoryDormStuChangeService.saveStuChange(paramMap);
			return result;
			
		} catch (Exception e) {
			logger.error("dormitoryDormStuChangeSave -=- {}", e.toString());
			return new R(true, "新增失败", "");
		}
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody DormitoryDormStuChange dormitoryDormStuChange) {
		try {
			DormitoryDormStuChange tmp = dormitoryDormStuChangeService.getById(dormitoryDormStuChange.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryDormStuChangeService.updateById(dormitoryDormStuChange);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", dormitoryDormStuChange);
		} catch (Exception e) {
			logger.error("DormitoryDormStuChangeUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}

	}

	/**
		 * 删除
		 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public R delete(@RequestBody DormitoryDormStuChange dormitoryDormStuChange) {
		try {
			DormitoryDormStuChange tmp = dormitoryDormStuChangeService.getById(dormitoryDormStuChange.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryDormStuChangeService.removeById(dormitoryDormStuChange.getId());
			if (!result) {
				return new R(true, "删除失败", "");
			}
			return new R(true, "删除成功", dormitoryDormStuChange);
		} catch (Exception e) {
			logger.error("dormitoryDormStuChangeDelete -=- {}", e.toString());
			return new R(true, "删除失败", "");
		}
	}

	
	/**
	* <p>寝室记录调换查询
	* 参数：
	*  1.学生ID：stuId
	* 2.学生学号：stuNo
	* 3.学生姓名：stuName
	* </p>
	* <p>Company: 和邦科技</p> 
	* @author lirc
	* @date 上午11:16:52
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
			page = dormitoryDormStuChangeService.selectStuChange(page, paramMap);
			return new R(true, "查询成功", page);
		} catch (Exception e) {
			logger.error("sysAreaDelete -=- {}", e.toString());
			return new R(true, "查询失败", "");
		}
	}

}