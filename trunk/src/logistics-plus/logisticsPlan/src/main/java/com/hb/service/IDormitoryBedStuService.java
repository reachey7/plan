package com.hb.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hb.entity.DormitoryBedStu;
import com.hb.entity.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirc
 * @since 2019-08-12
 */
public interface IDormitoryBedStuService extends IService<DormitoryBedStu> {

	public R saveBedStu(Map<String, Object> paramMap);
	
	public Page<Map> selectBedStu(Page page, Map<String,Object> param);
}
