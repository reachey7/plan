package com.hb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirc
 * @since 2019-08-06
 */
public interface IDormitoryPlanService extends IService<DormitoryPlan> {

    public  Page<Map> selectDormitoryPlan(Page page, Map<String,Object> param);
}
