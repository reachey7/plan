package com.hb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlanStu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生与计划关系表 Mapper 接口
 * </p>
 *
 * @author lirc
 * @since 2019-08-09
 */
public interface DormitoryPlanStuMapper extends BaseMapper<DormitoryPlanStu> {
    public List<Map<String, Object>> selectPlanStudent(Page page, @Param("param")Map<String,Object> param);
}
