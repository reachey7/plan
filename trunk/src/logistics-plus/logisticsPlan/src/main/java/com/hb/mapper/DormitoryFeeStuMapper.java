package com.hb.mapper;

import com.hb.entity.DormitoryFeeStu;

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
public interface DormitoryFeeStuMapper extends BaseMapper<DormitoryFeeStu> {

	public List<Map<String, Object>> selectFeeStu(Page page,  @Param("param")Map<String,Object> param);
	
	public List<Map<String, Object>> selectBedFee(@Param("param")Map<String,Object> param);
}
