package com.hb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlanBed;
import com.hb.mapper.DormitoryPartMapper;
import com.hb.mapper.DormitoryPlanBedMapper;
import com.hb.service.IDormitoryPlanBedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirc
 * @since 2019-08-09
 */
@Service("dormitoryPlanBedService")
public class DormitoryPlanBedServiceImpl extends ServiceImpl<DormitoryPlanBedMapper, DormitoryPlanBed> implements IDormitoryPlanBedService {
    @Autowired
    private DormitoryPlanBedMapper dormitoryPlanBedMapper;

    private static Logger log = LoggerFactory.getLogger(DormitoryPlanBedServiceImpl.class);

    @Override
    public Page<Map> selectPlanBed(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryPlanBedMapper.selectPlanBed(page, param);
        page.setRecords(resultList);
        return page;
    }
}
