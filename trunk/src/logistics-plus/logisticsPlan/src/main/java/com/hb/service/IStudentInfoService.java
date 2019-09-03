package com.hb.service;

import com.hb.entity.StudentInfo;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirc
 * @since 2019-08-19
 */
public interface IStudentInfoService extends IService<StudentInfo> {
	 public  Page<Map> selectStudentInfo(Page page, Map<String,Object> param);
}
