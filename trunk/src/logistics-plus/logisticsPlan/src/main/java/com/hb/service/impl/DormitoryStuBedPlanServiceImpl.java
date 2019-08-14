package com.hb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.entity.DormitoryStuBedPlan;
import com.hb.mapper.DormitoryStuBedPlanMapper;
import com.hb.service.IDormitoryStuBedPlanService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirc
 * @since 2019-08-13
 */
@Service("dormitoryStuBedPlanService")
public class DormitoryStuBedPlanServiceImpl extends ServiceImpl<DormitoryStuBedPlanMapper, DormitoryStuBedPlan> implements IDormitoryStuBedPlanService {
	@Autowired
    private DormitoryStuBedPlanMapper dormitoryStuBedPlanMapper;


    @Override
    public Page<Map> selectStuBedPlan(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryStuBedPlanMapper.selectStuBedPlan(page, param);
        page.setRecords(resultList);
        return page;
    }
}
