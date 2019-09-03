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
import com.hb.service.IStudentInfoService;
import com.hb.entity.StudentInfo;
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
 * @since 2019-08-19
 */
@Controller
@RequestMapping("/studentInfo")
public class StudentInfoController {
private final Logger logger=LoggerFactory.getLogger(StudentInfoController.class);

@Autowired
public IStudentInfoService studentInfoService;



/**
 * 新增
 */
@ResponseBody
@RequestMapping(method = RequestMethod.POST, value = "/add")
public R add(@RequestBody StudentInfo studentInfo){
        try{
        boolean result =studentInfoService.save(studentInfo);
        if(!result){
        return new R(true, "新增失败", "");
        }
        }catch(Exception e){
        logger.error("studentInfoSave -=- {}" ,e.toString());
        return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", studentInfo);
}




	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody StudentInfo studentInfo) {
		try {
			StudentInfo tmp = studentInfoService.getById(studentInfo.getId());
			if(tmp==null){
				return new R(true, "根据ID查找数据并不存在", "");
			}
			
			Boolean result = studentInfoService.updateById(studentInfo);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", studentInfo);
		} catch (Exception e) {
			logger.error("StudentInfoUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}
		
	}

	/**
		 * 删除
		 */
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, value = "/delete")
		public R delete(@RequestBody StudentInfo studentInfo) {
			try {
				StudentInfo tmp = studentInfoService.getById(studentInfo.getId());
				if (tmp == null) {
					return new R(true, "根据ID查找数据并不存在", "");
				}

				Boolean result = studentInfoService.removeById(studentInfo.getId());
				if (!result) {
					return new R(true, "删除失败", "");
				}
				return new R(true, "删除成功", studentInfo);
			} catch (Exception e) {
				logger.error("studentInfoDelete -=- {}", e.toString());
				return new R(true, "删除失败", "");
			}
	}

		/**
		 *  查询
		 */
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, value = "/query")
		public R query(@RequestBody Map<String, Object> paramMap) {
			try {
				  Page page = null;
		            if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
		                page = new Page(0, 10);
		            } else {
		                page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
		            }
		            Page listPage = studentInfoService.selectStudentInfo(page, paramMap);
		            return new R(true, "查询成功", listPage);
			} catch (Exception e) {
				logger.error("sysAreaDelete -=- {}", e.toString());
				return new R(true, "查询失败", "");
			}
	}


        }