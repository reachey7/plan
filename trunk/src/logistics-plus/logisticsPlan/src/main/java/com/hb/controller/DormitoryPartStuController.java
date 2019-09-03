package com.hb.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPartStu;
import com.hb.entity.DormitoryPlanStu;
import com.hb.entity.R;
import com.hb.mapper.DormitoryPlanStuMapper;
import com.hb.service.IDormitoryPartStuService;
import com.hb.service.IDormitoryPlanStuService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;

/**
 * @author lirc
 * @since 2019-08-12
 */
@Controller
@RequestMapping("/dormitoryPartStu")
public class DormitoryPartStuController {
    private final Logger logger = LoggerFactory.getLogger(DormitoryPartStuController.class);

    @Autowired
    public IDormitoryPartStuService dormitoryPartStuService;
    @Autowired
    public DormitoryPlanStuMapper dormitoryPlanStuMapper;

    @Autowired
    public IDormitoryPlanStuService dormitoryPlanStuService;

    /**
     * 新增：
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public R add(@RequestBody List<DormitoryPartStu> dormitoryPartStuList) {
        try {
        	//向part_stu表中添加数据，成功加入后将plan_stu中的学生状态改为2
            for (DormitoryPartStu dps : dormitoryPartStuList) {
            	dps.setId(IdUtil.createSerialSS(""));
            	dps.setCreateDate(PlugDateUtil.getCurDateTime());
                dps.setState("0");
                boolean result = dormitoryPartStuService.save(dps);
                if (!result) {
                    return new R(true, "新增失败", "");
                }
            }
            
            //更新plan_stu表中的学生状态未2
            
            QueryWrapper<DormitoryPlanStu> queryWrapper = new QueryWrapper();
            List<String> studentIdList = new ArrayList<String>();
            for(DormitoryPartStu dps : dormitoryPartStuList){
            	studentIdList.add(dps.getStudentId());
            }
            queryWrapper.in("STUDENT_ID", studentIdList);
            queryWrapper.in("STATE", "1");
            List<DormitoryPlanStu> planStuList = dormitoryPlanStuMapper.selectList(queryWrapper);
            for(DormitoryPlanStu dps : planStuList){
            	dps.setState("2");
            }
            dormitoryPlanStuService.updateBatchById(planStuList);
            
        } catch (Exception e) {
            logger.error("dormitoryPartStuSave -=- {}", e.toString());
            return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", null);
    }


    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public R update(@RequestBody DormitoryPartStu dormitoryPartStu) {
        try {
            DormitoryPartStu tmp = dormitoryPartStuService.getById(dormitoryPartStu.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryPartStuService.updateById(dormitoryPartStu);
            if (!result) {
                return new R(true, "修改失败", "");
            }
            return new R(true, "修改成功", dormitoryPartStu);
        } catch (Exception e) {
            logger.error("DormitoryPartStuUpdate -=- {}", e.toString());
            return new R(true, "修改失败", "");
        }

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public R delete(@RequestBody DormitoryPartStu dormitoryPartStu) {
        try {
        	//将划分中的学生删除后，还要将plan_stu中的状态改为1
        	QueryWrapper<DormitoryPartStu> queryPsWrapper = new QueryWrapper();
        	queryPsWrapper.eq("STUDENT_ID", dormitoryPartStu.getStudentId());
        	queryPsWrapper.eq("PART_ID", dormitoryPartStu.getPartId());
        	DormitoryPartStu dpsTmp = dormitoryPartStuService.getOne(queryPsWrapper);

            Boolean result = dormitoryPartStuService.removeById(dpsTmp.getId());
            if (!result) {
                return new R(true, "删除失败", "");
            }
            
            //将plan_stu中的状态改为1
            QueryWrapper<DormitoryPlanStu> queryWrapper = new QueryWrapper();
            queryWrapper.eq("STUDENT_ID", dormitoryPartStu.getStudentId());
            queryWrapper.eq("STATE", "2");
            List<DormitoryPlanStu> planStuList = dormitoryPlanStuMapper.selectList(queryWrapper);
            for(DormitoryPlanStu dps : planStuList){
            	dps.setState("1");
            }
            dormitoryPlanStuService.updateBatchById(planStuList);
            
            return new R(true, "删除成功", dormitoryPartStu);
        } catch (Exception e) {
            logger.error("dormitoryPartStuDelete -=- {}", e.toString());
            return new R(true, "删除失败", "");
        }
    }

    /**
     * 查询
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/query")
    public R query(@RequestBody Map paramMap) {
        try {
            Page page = null;
            if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
                page = new Page(0, 1);
            } else {
                page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
            }
            page = dormitoryPartStuService.selectPartStu(page,paramMap);
            return new R(true, "查询成功", page);
        } catch (Exception e) {
            logger.error("dormitoryPartStuService -=- {}", e.toString());
            return new R(true, "查询失败", "");
        }
    }


}