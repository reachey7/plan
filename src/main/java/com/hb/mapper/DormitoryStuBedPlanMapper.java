package com.hb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryStuBedPlan;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lirc
 * @since 2019-08-13
 */
public interface DormitoryStuBedPlanMapper extends BaseMapper<DormitoryStuBedPlan> {
    public List<Map<String, Object>> selectStuBedPlan(Page page, @Param("param") Map<String, Object> param);
}
