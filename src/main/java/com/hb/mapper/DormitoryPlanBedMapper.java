package com.hb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlanBed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lirc
 * @since 2019-08-09
 */
public interface DormitoryPlanBedMapper extends BaseMapper<DormitoryPlanBed> {
    public List<Map<String, Object>> selectPlanBed(Page page, @Param("param") Map<String, Object> param);
    public List<Map<String, Object>> selectSysBed(Page page, @Param("param") Map<String, Object> param);
    }
