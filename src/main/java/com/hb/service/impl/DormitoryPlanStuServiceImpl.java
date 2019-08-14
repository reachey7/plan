package com.hb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlanStu;
import com.hb.mapper.DormitoryPlanMapper;
import com.hb.mapper.DormitoryPlanStuMapper;
import com.hb.service.IDormitoryPlanStuService;
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
 * 学生与计划关系表 服务实现类
 * </p>
 *
 * @author lirc
 * @since 2019-08-09
 */
@Service("dormitoryPlanStuService")
public class DormitoryPlanStuServiceImpl extends ServiceImpl<DormitoryPlanStuMapper, DormitoryPlanStu> implements IDormitoryPlanStuService {
    @Autowired
    private DormitoryPlanStuMapper dormitoryPlanStuMapper;

    private static Logger log = LoggerFactory.getLogger(DormitoryPlanStuServiceImpl.class);

    @Override
    public Page<Map> selectPlanStudent(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryPlanStuMapper.selectPlanStudent(page, param);
        page.setRecords(resultList);
        return page;
    }
}
