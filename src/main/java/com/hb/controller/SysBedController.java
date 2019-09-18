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


import com.hb.service.ISysBedService;
import com.hb.entity.SysBed;
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
 * @since 2019-09-03
 */
@Controller
@RequestMapping("/sysBed")
public class SysBedController {
private final Logger logger=LoggerFactory.getLogger(SysBedController.class);

@Autowired
public ISysBedService sysBedService;



/**
 * 新增
 */
@ResponseBody
@RequestMapping(method = RequestMethod.POST, value = "/add")
public R add(@RequestBody SysBed sysBed){
        try{
        boolean result =sysBedService.save(sysBed);
        if(!result){
        return new R(true, "新增失败", "");
        }
        }catch(Exception e){
        logger.error("sysBedSave -=- {}" ,e.toString());
        return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", sysBed);
}




	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody SysBed sysBed) {
		try {
			SysBed tmp = sysBedService.getById(sysBed.getId());
			if(tmp==null){
				return new R(true, "根据ID查找数据并不存在", "");
			}
			
			Boolean result = sysBedService.updateById(sysBed);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", sysBed);
		} catch (Exception e) {
			logger.error("SysBedUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}
		
	}

	/**
		 * 删除
		 */
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, value = "/delete")
		public R delete(@RequestBody SysBed sysBed) {
			try {
				SysBed tmp = sysBedService.getById(sysBed.getId());
				if (tmp == null) {
					return new R(true, "根据ID查找数据并不存在", "");
				}

				Boolean result = sysBedService.removeById(sysBed.getId());
				if (!result) {
					return new R(true, "删除失败", "");
				}
				return new R(true, "删除成功", sysBed);
			} catch (Exception e) {
				logger.error("sysBedDelete -=- {}", e.toString());
				return new R(true, "删除失败", "");
			}
	}

		/**
		 *  查询
		 */
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, value = "/query")
		public R query(@RequestBody SysBed sysBed) {
			try {
				QueryWrapper<SysBed> queryWrapper =  new QueryWrapper<>();
				

        //条件拼接
                if (!StringUtils.isEmpty(sysBed.getDormId())){
            queryWrapper.eq("dormId", sysBed.getDormId());
        }
                if (!StringUtils.isEmpty(sysBed.getBedCode())){
            queryWrapper.eq("bedCode", sysBed.getBedCode());
        }
                if (!StringUtils.isEmpty(sysBed.getBedNumber())){
            queryWrapper.eq("bedNumber", sysBed.getBedNumber());
        }
                if (!StringUtils.isEmpty(sysBed.getBedStatus())){
            queryWrapper.eq("bedStatus", sysBed.getBedStatus());
        }
                if (!StringUtils.isEmpty(sysBed.getRemark())){
            queryWrapper.eq("remark", sysBed.getRemark());
        }
    

				
				List<SysBed> resultList = sysBedService.list(queryWrapper);
				return new R(true, "查询成功", resultList);
			} catch (Exception e) {
				logger.error("sysAreaDelete -=- {}", e.toString());
				return new R(true, "查询失败", "");
			}
	}


        }