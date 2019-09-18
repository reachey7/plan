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


import com.hb.service.ISysDormService;
import com.hb.entity.SysDorm;
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
@RequestMapping("/sysDorm")
public class SysDormController {
private final Logger logger=LoggerFactory.getLogger(SysDormController.class);

@Autowired
public ISysDormService sysDormService;



/**
 * 新增
 */
@ResponseBody
@RequestMapping(method = RequestMethod.POST, value = "/add")
public R add(@RequestBody SysDorm sysDorm){
        try{
        boolean result =sysDormService.save(sysDorm);
        if(!result){
        return new R(true, "新增失败", "");
        }
        }catch(Exception e){
        logger.error("sysDormSave -=- {}" ,e.toString());
        return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", sysDorm);
}




	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public R update(@RequestBody SysDorm sysDorm) {
		try {
			SysDorm tmp = sysDormService.getById(sysDorm.getId());
			if(tmp==null){
				return new R(true, "根据ID查找数据并不存在", "");
			}
			
			Boolean result = sysDormService.updateById(sysDorm);
			if (!result) {
				return new R(true, "修改失败", "");
			}
			return new R(true, "修改成功", sysDorm);
		} catch (Exception e) {
			logger.error("SysDormUpdate -=- {}", e.toString());
			return new R(true, "修改失败", "");
		}
		
	}

	/**
		 * 删除
		 */
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, value = "/delete")
		public R delete(@RequestBody SysDorm sysDorm) {
			try {
				SysDorm tmp = sysDormService.getById(sysDorm.getId());
				if (tmp == null) {
					return new R(true, "根据ID查找数据并不存在", "");
				}

				Boolean result = sysDormService.removeById(sysDorm.getId());
				if (!result) {
					return new R(true, "删除失败", "");
				}
				return new R(true, "删除成功", sysDorm);
			} catch (Exception e) {
				logger.error("sysDormDelete -=- {}", e.toString());
				return new R(true, "删除失败", "");
			}
	}

		/**
		 *  查询
		 */
		@ResponseBody
		@RequestMapping(method = RequestMethod.POST, value = "/query")
		public R query(@RequestBody SysDorm sysDorm) {
			try {
				QueryWrapper<SysDorm> queryWrapper =  new QueryWrapper<>();
				

        //条件拼接
                if (!StringUtils.isEmpty(sysDorm.getDormCode())){
            queryWrapper.eq("dormCode", sysDorm.getDormCode());
        }
                if (!StringUtils.isEmpty(sysDorm.getDormName())){
            queryWrapper.eq("dormName", sysDorm.getDormName());
        }
                if (!StringUtils.isEmpty(sysDorm.getFloor())){
            queryWrapper.eq("floor", sysDorm.getFloor());
        }
                if (!StringUtils.isEmpty(sysDorm.getSizeId())){
            queryWrapper.eq("sizeId", sysDorm.getSizeId());
        }
                if (!StringUtils.isEmpty(sysDorm.getUsedQuantity())){
            queryWrapper.eq("usedQuantity", sysDorm.getUsedQuantity());
        }
                if (!StringUtils.isEmpty(sysDorm.getSqm())){
            queryWrapper.eq("sqm", sysDorm.getSqm());
        }
                if (!StringUtils.isEmpty(sysDorm.getRoomTypeId())){
            queryWrapper.eq("roomTypeId", sysDorm.getRoomTypeId());
        }
                if (!StringUtils.isEmpty(sysDorm.getIsStudent())){
            queryWrapper.eq("isStudent", sysDorm.getIsStudent());
        }
                if (!StringUtils.isEmpty(sysDorm.getDormStatus())){
            queryWrapper.eq("dormStatus", sysDorm.getDormStatus());
        }
                if (!StringUtils.isEmpty(sysDorm.getRegisterTime())){
            queryWrapper.eq("registerTime", sysDorm.getRegisterTime());
        }
                if (!StringUtils.isEmpty(sysDorm.getRemark())){
            queryWrapper.eq("remark", sysDorm.getRemark());
        }
                if (!StringUtils.isEmpty(sysDorm.getManWomen())){
            queryWrapper.eq("manWomen", sysDorm.getManWomen());
        }
                if (!StringUtils.isEmpty(sysDorm.getYzNumber())){
            queryWrapper.eq("yzNumber", sysDorm.getYzNumber());
        }
                if (!StringUtils.isEmpty(sysDorm.getIfFull())){
            queryWrapper.eq("ifFull", sysDorm.getIfFull());
        }
                if (!StringUtils.isEmpty(sysDorm.getCampusId())){
            queryWrapper.eq("campusId", sysDorm.getCampusId());
        }
                if (!StringUtils.isEmpty(sysDorm.getCampusName())){
            queryWrapper.eq("campusName", sysDorm.getCampusName());
        }
                if (!StringUtils.isEmpty(sysDorm.getAreaId())){
            queryWrapper.eq("areaId", sysDorm.getAreaId());
        }
                if (!StringUtils.isEmpty(sysDorm.getAreaName())){
            queryWrapper.eq("areaName", sysDorm.getAreaName());
        }
                if (!StringUtils.isEmpty(sysDorm.getBuildingId())){
            queryWrapper.eq("buildingId", sysDorm.getBuildingId());
        }
                if (!StringUtils.isEmpty(sysDorm.getBuildingName())){
            queryWrapper.eq("buildingName", sysDorm.getBuildingName());
        }
                if (!StringUtils.isEmpty(sysDorm.getState())){
            queryWrapper.eq("state", sysDorm.getState());
        }
    

				
				List<SysDorm> resultList = sysDormService.list(queryWrapper);
				return new R(true, "查询成功", resultList);
			} catch (Exception e) {
				logger.error("sysAreaDelete -=- {}", e.toString());
				return new R(true, "查询失败", "");
			}
	}


        }