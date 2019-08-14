package com.hb.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlan;
import com.hb.service.IDormitoryPartService;
import com.hb.service.IDormitoryPlanService;
import com.hb.util.PlugDateUtil;
import com.hb.util.SysHelperUtil;
import org.springframework.stereotype.Controller;

import java.util.List;

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


import com.hb.service.IDormitoryPlanStuService;
import com.hb.entity.DormitoryPlanStu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hb.entity.R;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lirc
 * @since 2019-08-09
 */
@Controller
@RequestMapping("/dormitoryPlanStu")
public class DormitoryPlanStuController {
    private final Logger logger = LoggerFactory.getLogger(DormitoryPlanStuController.class);

    @Autowired
    public IDormitoryPlanStuService dormitoryPlanStuService;

    @Autowired
    public IDormitoryPartService dormitoryPartService;
    @Autowired
    public IDormitoryPlanService dormitoryPlanService;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 新增,将
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public R add(@RequestBody DormitoryPlanStu dormitoryPlanStu) {
        try {
            boolean result = dormitoryPlanStuService.save(dormitoryPlanStu);
            if (!result) {
                return new R(true, "新增失败", "");
            }
        } catch (Exception e) {
            logger.error("dormitoryPlanStuSave -=- {}", e.toString());
            return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", dormitoryPlanStu);
    }


    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public R update(@RequestBody DormitoryPlanStu dormitoryPlanStu) {
        try {
            DormitoryPlanStu tmp = dormitoryPlanStuService.getById(dormitoryPlanStu.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryPlanStuService.updateById(dormitoryPlanStu);
            if (!result) {
                return new R(true, "修改失败", "");
            }
            return new R(true, "修改成功", dormitoryPlanStu);
        } catch (Exception e) {
            logger.error("DormitoryPlanStuUpdate -=- {}", e.toString());
            return new R(true, "修改失败", "");
        }

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public R delete(@RequestBody DormitoryPlanStu dormitoryPlanStu) {
        try {
            DormitoryPlanStu tmp = dormitoryPlanStuService.getById(dormitoryPlanStu.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryPlanStuService.removeById(dormitoryPlanStu.getId());
            if (!result) {
                return new R(true, "删除失败", "");
            }
            return new R(true, "删除成功", dormitoryPlanStu);
        } catch (Exception e) {
            logger.error("dormitoryPlanStuDelete -=- {}", e.toString());
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
            try {
                Page page = null;
                if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
                    page = new Page(0, 1);
                } else {
                    page = new Page(Integer.parseInt(paramMap.get("size") + ""), Integer.parseInt(paramMap.get("size") + ""));
                }
                page = dormitoryPlanStuService.selectPlanStudent(page,paramMap);
                return new R(true, "查询成功", page.getRecords());
            } catch (Exception e) {
                logger.error("dormitoryPlanStudentService -=- {}", e.toString());
                return new R(true, "查询失败", "");
            }
        } catch (Exception e) {
            logger.error("sysAreaDelete -=- {}", e.toString());
            return new R(true, "查询失败", "");
        }
    }

    /**
     * 学生与床位添加完，调用微服务的工作流，接受工作流返回的信息并记录数据库
     * 参数包括
     * 计划ID：planId
     * 当前环节处理人currentPersonId
     * 下一环节操作人：nextPersonId和nextPersonName
     * 方法流程：
     * 1.首先判断planId、下一环节操作人是否传入
     * 2.判断planId是否在数据库存在
     * 3.调用工作流微服务
     * 4.根据工作流返回的结果判断是否成功，如果成功则更新dormitory_plan表中的PLAN_STATUS，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
     * 如果失败则返回给前台，允许前台再次调用
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/studentSubmit")
    public R studentSubmit(@RequestBody Map<String, Object> paramMap) {
        try {

            //首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("planId");
            list.add("nextPersonId");
            list.add("currentPersonId");
            R checkR = SysHelperUtil.check(list, paramMap);
            if (!checkR.isState()) {
                return checkR;
            }

            //判断传入的planId是否存在
            DormitoryPlan dormitory = dormitoryPlanService.getById((String) paramMap.get("planId"));
            if (dormitory == null) {
                return new R(false, "根据planId未找到计划数据", "");
            }

            //调用微服务工作流
            JSONObject activityJb = new JSONObject();
            activityJb.put("processInstanceId", dormitory.getActivityId());
            activityJb.put("nextPersonId", (String) paramMap.get("nextPersonId"));
            activityJb.put("currentPersonId", (String) paramMap.get("currentPersonId"));
            R activityCallR = SysHelperUtil.callActivity(activityJb, restTemplate, "studentSubmit");
            if (!activityCallR.isState()) {
                return new R(false, "调用微服务工作流失败", "");
            }

            //调用微服务成功，更新dormitory_plan表中的PLAN_STATUS，NEXT_PERSON_ID，NEXT_PERSON_NAME，CURRENT_PERSON_ID，CURRENT_PERSON_NAME,ACTIVITY_ID
            JSONObject jbresult = JSONObject.parseObject((String) activityCallR.getResult());
            String planStatus = jbresult.getString("status");
            dormitory.setPlanStatus(planStatus);
            dormitory.setCurrentPersonId((String) paramMap.get("nextPersonId"));
            dormitory.setCurrentPersonName((String) paramMap.get("nextPersonName"));
            dormitory.setUpdateTime(PlugDateUtil.getCurDateTime());
            Boolean saveResult = dormitoryPlanService.saveOrUpdate(dormitory);
            if (!saveResult) {
                return new R(false, "调用工作流程后回更失败", "");
            }

            return new R(true, "操作成功", "");
        } catch (Exception e) {
            logger.error("操作失败-=- {}", e.toString());
            return new R(false, "操作失败", e.toString());
        }
    }
}