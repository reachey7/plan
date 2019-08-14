package com.hb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPartBed;
import com.hb.mapper.DormitoryPartBedMapper;
import com.hb.mapper.DormitoryPartStuMapper;
import com.hb.service.IDormitoryPartBedService;
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
 * @since 2019-08-12
 */
@Service("dormitoryPartBedService")
public class DormitoryPartBedServiceImpl extends ServiceImpl<DormitoryPartBedMapper, DormitoryPartBed> implements IDormitoryPartBedService {
    @Autowired
    private DormitoryPartBedMapper dormitoryPartBedMapper;

    private static Logger log = LoggerFactory.getLogger(DormitoryPartStuServiceImpl.class);

    @Override
    public Page<Map> selectPartBed(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryPartBedMapper.selectPartBed(page, param);
        page.setRecords(resultList);
        return page;
    }
}
