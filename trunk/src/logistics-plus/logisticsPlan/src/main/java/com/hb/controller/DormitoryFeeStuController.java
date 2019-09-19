package com.hb.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hb.bean.ExceptionUtil;
import com.hb.entity.DormitoryFeeStu;
import com.hb.entity.R;
import com.hb.service.IDormitoryFeeStuService;
import com.hb.util.ReadExcel;

/**
 *
 * @author lirc
 * @since 2019-09-18
 */
@Controller
@RequestMapping("/dormitoryFeeStu")
public class DormitoryFeeStuController {
	private final Logger logger = LoggerFactory.getLogger(DormitoryFeeStuController.class);

	@Autowired
	public IDormitoryFeeStuService dormitoryFeeStuService;

	/**
	 * 批量导入学生缴费记录，前台传入的Excel包括以下字段学生姓名，学生学号，本次缴费金额，学年度，操作人。顺序不能发生改变
	 * 1.插入主表fee_stu，插入前首先查询该学生在本年度是否有过缴费记录，如果有则进行累加动作，如果没有则直接插入
	 * 2.插入子表fee_stuitem
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/addListUpload", method = RequestMethod.POST)
	@ResponseBody
	public R addListUpload(HttpServletRequest request) {
		try {
			Date startTime = new Date();
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			ReadExcel readExcel = new ReadExcel();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					List<Map<String, Object>> list = readExcel.getExcelInfo(file);
					if(list==null || list.size()<=0){
						return new R(false, "文件内容不能为空", "");
					}
					R result = dormitoryFeeStuService.insertFeeStu(list);
					return result;
				}
			}
			return new R(false, "上传失败", null);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTrace(e));
			return new R(false, "系统错误，请联系管理员！", null);
		}
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody DormitoryFeeStu dormitoryFeeStu) {
		try {
			DormitoryFeeStu tmp = dormitoryFeeStuService.getById(dormitoryFeeStu.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryFeeStuService.updateById(dormitoryFeeStu);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", dormitoryFeeStu);
		} catch (Exception e) {
			logger.error("DormitoryFeeStuUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}

	}

	/**
		 * 删除
		 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/delete")
	public R delete(@RequestBody DormitoryFeeStu dormitoryFeeStu) {
		try {
			DormitoryFeeStu tmp = dormitoryFeeStuService.getById(dormitoryFeeStu.getId());
			if (tmp == null) {
				return new R(true, "根据ID查找数据并不存在", "");
			}

			Boolean result = dormitoryFeeStuService.removeById(dormitoryFeeStu.getId());
			if (!result) {
				return new R(true, "删除失败", "");
			}
			return new R(true, "删除成功", dormitoryFeeStu);
		} catch (Exception e) {
			logger.error("dormitoryFeeStuDelete -=- {}", e.toString());
			return new R(true, "删除失败", "");
		}
	}


	/**
	* <p>学生缴费记录查询
	* 参数：
	* 1.年份,year
	* 2.学生姓名,student_name
	* 3.学生编号,student_num
	* 4.是否缴清,if_over</p>
	* <p>Company: 和邦科技</p> 
	* @author lirc
	* @date 下午4:08:07
	*/
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/query")
	public R query(@RequestBody Map<String, Object> paramMap) {
		try {
			QueryWrapper<DormitoryFeeStu> queryWrapper = new QueryWrapper<>();

			
			if (!StringUtils.isEmpty(paramMap.get("year"))) {
				queryWrapper.eq("YEAR", paramMap.get("year"));
			}
			
			if (!StringUtils.isEmpty(paramMap.get("studentName"))) {
				queryWrapper.like("STUDENT_NAME", paramMap.get("studentName"));
			}
			if (!StringUtils.isEmpty(paramMap.get("studentNum"))) {
				queryWrapper.eq("STUDENT_NUM", paramMap.get("studentNum"));
			}
			if (!StringUtils.isEmpty(paramMap.get("isOver"))) {
				queryWrapper.eq("IS_OVER", paramMap.get("isOver"));
			}
			List<DormitoryFeeStu> resultList = dormitoryFeeStuService.list(queryWrapper);
			return new R(true, "查询成功", resultList);
		} catch (Exception e) {
			logger.error("sysAreaDelete -=- {}", e.toString());
			return new R(true, "查询失败", "");
		}
	}

}