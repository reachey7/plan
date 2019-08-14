package com.hb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lirc
 * @since 2019-08-08
 */
public interface IDormitoryPartService extends IService<DormitoryPart> {
    public Page<Map> selectDormitoryPart(Page page, Map<String,Object> param);
}
