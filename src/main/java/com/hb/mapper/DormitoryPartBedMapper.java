package com.hb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPartBed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lirc
 * @since 2019-08-12
 */
public interface DormitoryPartBedMapper extends BaseMapper<DormitoryPartBed> {

	public List<Map<String, Object>> selectPartBed(Page page, @Param("param")Map<String,Object> param);

	public List<Map<String, Object>> selectPartBedByStu(Page page,  @Param("param")Map<String,Object> param);
	
}
