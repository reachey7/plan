package com.hb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlan;
import com.hb.mapper.DormitoryPlanMapper;
import com.hb.service.IDormitoryPlanService;
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
 * @since 2019-08-06
 */
@Service("dormitoryPlanService")
public class DormitoryPlanServiceImpl extends ServiceImpl<DormitoryPlanMapper, DormitoryPlan> implements IDormitoryPlanService {

    @Autowired
    private DormitoryPlanMapper dormitoryPlanMapper;

    private static Logger log = LoggerFactory.getLogger(DormitoryPlanServiceImpl.class);

    @Override
    public Page<Map> selectDormitoryPlan(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryPlanMapper.selectDormitoryPlan(page, param);
        page.setRecords(resultList);
        return page;
    }
    
    @Override
    public Page<Map> selectSysUser(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryPlanMapper.selectSysUser(page, param);
        page.setRecords(resultList);
        return page;
    }
}
