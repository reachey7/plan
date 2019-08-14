package com.hb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPartStu;
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
public interface IDormitoryPartStuService extends IService<DormitoryPartStu> {
    public Page<Map> selectPartStu(Page page, Map<String,Object> param);
}
