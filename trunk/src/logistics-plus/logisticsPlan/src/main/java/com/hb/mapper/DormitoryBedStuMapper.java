package com.hb.mapper;

import com.hb.entity.DormitoryBedStu;

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
 * @since 2019-08-12
 */
public interface DormitoryBedStuMapper extends BaseMapper<DormitoryBedStu> {
	public List<Map<String, Object>> selectBedStu(Page page, @Param("param")Map<String,Object> param);
}
