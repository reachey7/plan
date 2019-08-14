package com.hb.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPart;
import com.hb.mapper.DormitoryPartMapper;
import com.hb.mapper.DormitoryPartMapper;
import com.hb.service.IDormitoryPartService;
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
 * @since 2019-08-08
 */
@Service("dormitoryPartService")
public class DormitoryPartServiceImpl extends ServiceImpl<DormitoryPartMapper, DormitoryPart> implements IDormitoryPartService {
    @Autowired
    private DormitoryPartMapper dormitoryPartMapper;

    private static Logger log = LoggerFactory.getLogger(DormitoryPartServiceImpl.class);

    @Override
    public Page<Map> selectDormitoryPart(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryPartMapper.selectDormitoryPart(page, param);
        page.setRecords(resultList);
        return page;
    }
}
