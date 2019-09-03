package com.hb.mapper;

import com.hb.entity.StudentInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lirc
 * @since 2019-08-19
 */
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {
	 public List<Map<String, Object>> selectStudent(Page page, @Param("param") Map<String, Object> param);
}
