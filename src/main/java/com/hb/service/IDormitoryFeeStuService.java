package com.hb.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hb.entity.DormitoryFeeStu;
import com.hb.entity.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirc
 * @since 2019-09-18
 */
public interface IDormitoryFeeStuService extends IService<DormitoryFeeStu> {

	
	 public Page<Map> selectFeeStu(Page page, Map<String,Object> param);
	 
	 public R insertFeeStu(List<Map<String,Object>> param);
	 
	 
}
