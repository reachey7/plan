package com.hb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlan;
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
 * @since 2019-08-06
 */
public interface DormitoryPlanMapper extends BaseMapper<DormitoryPlan> {
    public List<Map<String, Object>> selectDormitoryPlan(Page page, @Param("param")Map<String,Object> param);
    public List<Map<String, Object>> selectSysUser(Page page, @Param("param")Map<String,Object> param);
    
}
