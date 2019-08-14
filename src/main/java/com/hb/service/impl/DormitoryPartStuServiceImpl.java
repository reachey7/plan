package com.hb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPartStu;
import com.hb.mapper.DormitoryPartMapper;
import com.hb.mapper.DormitoryPartStuMapper;
import com.hb.service.IDormitoryPartStuService;
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
@Service("dormitoryPartStuService")
public class DormitoryPartStuServiceImpl extends ServiceImpl<DormitoryPartStuMapper, DormitoryPartStu> implements IDormitoryPartStuService {
    @Autowired
    private DormitoryPartStuMapper dormitoryPartStuMapper;

    private static Logger log = LoggerFactory.getLogger(DormitoryPartStuServiceImpl.class);

    @Override
    public Page<Map> selectPartStu(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryPartStuMapper.selectPartStu(page, param);
        page.setRecords(resultList);
        return page;
    }
}
