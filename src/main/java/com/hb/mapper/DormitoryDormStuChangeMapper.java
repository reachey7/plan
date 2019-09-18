package com.hb.mapper;

import com.hb.entity.DormitoryDormStuChange;

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
 * @since 2019-09-18
 */
public interface DormitoryDormStuChangeMapper extends BaseMapper<DormitoryDormStuChange> {
	public List<Map<String, Object>> selectStuChange(Page page, @Param("param")Map<String,Object> param);
}
