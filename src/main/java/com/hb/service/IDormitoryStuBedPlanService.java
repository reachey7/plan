package com.hb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryStuBedPlan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirc
 * @since 2019-08-12
 */
public interface IDormitoryStuBedPlanService extends IService<DormitoryStuBedPlan> {
    public Page<Map> selectStuBedPlan(Page page, Map<String,Object> param);
}
