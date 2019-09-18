package com.hb.service;

import com.hb.entity.DormitoryDormStuChange;
import com.hb.entity.R;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirc
 * @since 2019-09-18
 */
public interface IDormitoryDormStuChangeService extends IService<DormitoryDormStuChange> {

	public R saveStuChange(Map<String, Object> paramMap);
	
	public Page<Map> selectStuChange(Page page, Map<String,Object> param);
}
