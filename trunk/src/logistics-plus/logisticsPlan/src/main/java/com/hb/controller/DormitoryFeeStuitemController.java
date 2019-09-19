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

import com.hb.service.IDormitoryFeeStuitemService;
import com.hb.entity.DormitoryFeeStuitem;
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
@RequestMapping("/dormitoryFeeStuitem")
public class DormitoryFeeStuitemController {
	private final Logger logger = LoggerFactory.getLogger(DormitoryFeeStuitemController.class);

	@Autowired
	public IDormitoryFeeStuitemService dormitoryFeeStuitemService;

	/**
	 * 新增
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	public R add(@RequestBody DormitoryFeeStuitem dormitoryFeeStuitem) {
		try {
			boolean result = dormitoryFeeStuitemService.save(dormitoryFeeStuitem);
			if (!result) {
				return new R(true, "新增失败", "");
			}
		} catch (Exception e) {
			logger.error("dormitoryFeeStuitemSave -=- {}", e.toString());
			return new R(true, "新增失败", "");
		}
		return new R(true, "新增成功", dormitoryFeeStuitem);
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody DormitoryFeeStuitem dormitoryFeeStuitem) {
		try {
			DormitoryFeeStuitem tmp = dormitoryFeeStuitemService.getById(dormitoryFeeStuitem.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryFeeStuitemService.updateById(dormitoryFeeStuitem);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", dormitoryFeeStuitem);
		} catch (Exception e) {
			logger.error("DormitoryFeeStuitemUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}

	}

	/**
		 * 删除
		 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public R delete(@RequestBody DormitoryFeeStuitem dormitoryFeeStuitem) {
		try {
			DormitoryFeeStuitem tmp = dormitoryFeeStuitemService.getById(dormitoryFeeStuitem.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryFeeStuitemService.removeById(dormitoryFeeStuitem.getId());
			if (!result) {
				return new R(true, "删除失败", "");
			}
			return new R(true, "删除成功", dormitoryFeeStuitem);
		} catch (Exception e) {
			logger.error("dormitoryFeeStuitemDelete -=- {}", e.toString());
			return new R(true, "删除失败", "");
		}
	}

	/**
	* <p>学生缴费记录明细
	* 参数：
	* 1.feeStuId,缴费主表中的ID
	* <p>Company: 和邦科技</p> 
	* @author lirc
	* @date 下午4:08:07
	*/
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/query")
	public R query(@RequestBody Map<String, Object> paramMap) {
		try {
			QueryWrapper<DormitoryFeeStuitem> queryWrapper = new QueryWrapper<>();

			// 条件拼接
			if (!StringUtils.isEmpty(paramMap.get("feeStuId"))) {
				queryWrapper.eq("FEE_STU_ID", paramMap.get("feeStuId"));
			}

			List<DormitoryFeeStuitem> resultList = dormitoryFeeStuitemService.list(queryWrapper);
			return new R(true, "查询成功", resultList);
		} catch (Exception e) {
			logger.error("sysAreaDelete -=- {}", e.toString());
			return new R(true, "查询失败", "");
		}
	}

}