package com.hb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlanStu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 学生与计划关系表 服务类
 * </p>
 *
 * @author lirc
 * @since 2019-08-09
 */
public interface IDormitoryPlanStuService extends IService<DormitoryPlanStu> {
    public Page<Map> selectPlanStudent(Page page, Map<String,Object> param);

}
