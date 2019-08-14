package com.hb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPartBed;
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
public interface IDormitoryPartBedService extends IService<DormitoryPartBed> {
    public Page<Map> selectPartBed(Page page, Map<String,Object> param);
}
