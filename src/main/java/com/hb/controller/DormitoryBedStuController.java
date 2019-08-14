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


import com.hb.service.IDormitoryBedStuService;
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
private final Logger logger=LoggerFactory.getLogger(DormitoryBedStuController.class);

@Autowired
public IDormitoryBedStuService dormitoryBedStuService;



/**
 * 新增
 */
@ResponseBody
@RequestMapping(method = RequestMethod.POST, value = "/add")
public R add(@RequestBody DormitoryBedStu dormitoryBedStu){
        try{
        boolean result =dormitoryBedStuService.save(dormitoryBedStu);
        if(!result){
        return new R(true, "新增失败", "");
        }
        }catch(Exception e){
        logger.error("dormitoryBedStuSave -=- {}" ,e.toString());
        return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", dormitoryBedStu);
}




	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody DormitoryBedStu dormitoryBedStu) {
		try {
			DormitoryBedStu tmp = dormitoryBedStuService.getById(dormitoryBedStu.getId());
			if(tmp==null){
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
		 *  查询
		 */
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, value = "/query")
		public R query(@RequestBody DormitoryBedStu dormitoryBedStu) {
			try {
				QueryWrapper<DormitoryBedStu> queryWrapper =  new QueryWrapper<>();
				

        //条件拼接
                if (!StringUtils.isEmpty(dormitoryBedStu.getStudentId())){
            queryWrapper.eq("studentId", dormitoryBedStu.getStudentId());
        }
                if (!StringUtils.isEmpty(dormitoryBedStu.getBedId())){
            queryWrapper.eq("bedId", dormitoryBedStu.getBedId());
        }
                if (!StringUtils.isEmpty(dormitoryBedStu.getOperatorId())){
            queryWrapper.eq("operatorId", dormitoryBedStu.getOperatorId());
        }
                if (!StringUtils.isEmpty(dormitoryBedStu.getOperatDate())){
            queryWrapper.eq("operatDate", dormitoryBedStu.getOperatDate());
        }
                if (!StringUtils.isEmpty(dormitoryBedStu.getState())){
            queryWrapper.eq("state", dormitoryBedStu.getState());
        }
                if (!StringUtils.isEmpty(dormitoryBedStu.getBeginDate())){
            queryWrapper.eq("beginDate", dormitoryBedStu.getBeginDate());
        }
                if (!StringUtils.isEmpty(dormitoryBedStu.getEndDate())){
            queryWrapper.eq("endDate", dormitoryBedStu.getEndDate());
        }
                if (!StringUtils.isEmpty(dormitoryBedStu.getInMode())){
            queryWrapper.eq("inMode", dormitoryBedStu.getInMode());
        }
    

				
				List<DormitoryBedStu> resultList = dormitoryBedStuService.list(queryWrapper);
				return new R(true, "查询成功", resultList);
			} catch (Exception e) {
				logger.error("sysAreaDelete -=- {}", e.toString());
				return new R(true, "查询失败", "");
			}
	}


        }