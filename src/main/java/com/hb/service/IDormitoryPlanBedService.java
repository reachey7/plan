package com.hb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlanBed;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirc
 * @since 2019-08-09
 */
public interface IDormitoryPlanBedService extends IService<DormitoryPlanBed> {

    public Page<Map> selectPlanBed(Page page, Map<String,Object> param);
}
