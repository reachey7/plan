package com.hb.service.impl;

import com.hb.entity.StudentInfo;
import com.hb.mapper.StudentInfoMapper;
import com.hb.service.IStudentInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirc
 * @since 2019-08-19
 */
@Service("studentInfoService")
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements IStudentInfoService {

	/* (non-Javadoc)
	 * @see com.hb.service.IStudentInfoService#selectStudentInfo(com.baomidou.mybatisplus.extension.plugins.pagination.Page, java.util.Map)
	 */
	@Override
	public Page<Map> selectStudentInfo(Page page, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
