package com.hb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.util.PlugDateUtil;
import com.hb.util.SysHelperUtil;
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
import com.hb.entity.DormitoryPlan;
import com.hb.entity.R;
import com.hb.service.IDormitoryPlanService;
import com.hb.util.IdUtil;
import org.springframework.web.client.RestTemplate;

/**
 * @author lirc
 * @since 2019-08-06
 */
@Controller
@RequestMapping("/dormitoryPlan")
public class DormitoryPlanController {
    private final Logger logger = LoggerFactory.getLogger(DormitoryPlanController.class);

    @Autowired
    public IDormitoryPlanService dormitoryPlanService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 新增
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public R add(@RequestBody DormitoryPlan dormitoryPlan) {
        try {
            dormitoryPlan.setId(IdUtil.createSerialSS(""));
            dormitoryPlan.setPlanStatus("创建中");
            dormitoryPlan.setPlanCode(dormitoryPlan.getId());
            dormitoryPlan.setCreateTime(PlugDateUtil.getCurDateTime());
            dormitoryPlan.setHfOverNumber(0);
            dormitoryPlan.setPartitionNumber(0);
            boolean result = dormitoryPlanService.save(dormitoryPlan);
            if (!result) {
                return new R(true, "新增失败", "");
            }
        } catch (Exception e) {
            logger.error("dormitoryPlanSave -=- {}", e.toString());
            return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", dormitoryPlan);
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public R update(@RequestBody Map<String, Object> paramMap) {
        try {
            DormitoryPlan dormitoryPlan = dormitoryPlanService.getById((String)paramMap.get("id"));
            if (dormitoryPlan == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            dormitoryPlan.setPlanName(paramMap.get("planName")+"");
            dormitoryPlan.setDescription(paramMap.get("description")+"");
            dormitoryPlan.setStuType(paramMap.get("stuType")+"");
            dormitoryPlan.setCurrentPersonId(paramMap.get("currentPersonId")+"");
            dormitoryPlan.setCurrentPersonName(paramMap.get("currentPersonName")+"");
            
            Boolean result = dormitoryPlanService.updateById(dormitoryPlan);
            if (!result) {
                return new R(true, "修改失败", "");
            }
            return new R(true, "修改成功", dormitoryPlan);
        } catch (Exception e) {
            logger.error("DormitoryPlanUpdate -=- {}", e.toString());
            return new R(true, "修改失败", "");
        }

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public R delete(@RequestBody DormitoryPlan dormitoryPlan) {
        try {
            DormitoryPlan tmp = dormitoryPlanService.getById(dormitoryPlan.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryPlanService.removeById(dormitoryPlan.getId());
            if (!result) {
                return new R(true, "删除失败", "");
            }
            return new R(true, "删除成功", dormitoryPlan);
        } catch (Exception e) {
            logger.error("dormitoryPlanDelete -=- {}", e.toString());
            return new R(true, "删除失败", "");
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/querySysUser")
    public R querySysUser(@RequestBody Map<String, Object> paramMap) {
        try {

            Page page = null;
            if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
                page = new Page(0, 10);
            } else {
                page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
            }
            Page listPage = dormitoryPlanService.selectSysUser(page, paramMap);
            return new R(true, "查询成功", listPage);

        } catch (Exception e) {
            logger.error("dormitoryPlanService -=- {}", e.toString());
            return new R(true, "查询失败", e.toString());
        }
    }
    
    /**
     * 查询
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/query")
    public R query(@RequestBody Map<String, Object> paramMap) {
        try {

            Page page = null;
            if (StringUtils.isEmpty(paramMap.get("current")) || StringUtils.isEmpty(paramMap.get("size"))) {
                page = new Page(0, 10);
            } else {
                page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
            }
            Page listPage = dormitoryPlanService.selectDormitoryPlan(page, paramMap);
            return new R(true, "查询成功", listPage);

        } catch (Exception e) {
            logger.error("dormitoryPlanService -=- {}", e.toString());
            return new R(true, "查询失败", e.toString());
        }
    }

    /**
     * 计划完成，调用微服务的工作流，接受工作流返回的信息并记录数据库
     * 参数包括
     * 计划ID：planId
     * 下一环节操作人：nextPersonId和nextPersonName
     * <p>
     * 方法流程：
     * 1.首先判断计划ID，下一环节操作人是否传入
     * 2.判断planId是否在数据库存在
     * 3.调用工作流微服务
     * 4.根据工作流返回的结果判断是否成功，如果成功则更新dormitory_plan表中的PLAN_STATUS，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
     * ACTIVITY_ID
     * 如果失败则返回给前台，允许前台再次调用
     *
     * @author lirc
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/planSubmit")
    public R planSubmit(@RequestBody Map<String, Object> paramMap) {
        try {

            //首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("planId");
            list.add("nextPersonId");

            R checkR = SysHelperUtil.check(list, paramMap);
            if (!checkR.isState()) {
                return checkR;
            }

            //判断传入的planId是否存在
            DormitoryPlan dormitory = dormitoryPlanService.getById((String) paramMap.get("planId"));
            if (dormitory == null) {
                return new R(false, "根据planId未找到计划数据", "");
            }

            if(!dormitory.getPlanStatus().equals("创建中")){
            	 return new R(false, "非创建中状态的计划不可以实施!", "");
            }
            //调用微服务工作流
            JSONObject activityJb = new JSONObject();
            activityJb.put("currentPersonId", dormitory.getCreatePersonId());
            activityJb.put("planId", (String) paramMap.get("planId"));
            activityJb.put("nextPersonId", (String) paramMap.get("nextPersonId"));

            R activityCallR = SysHelperUtil.callActivity(activityJb, restTemplate, "planSubmit");
            if (!activityCallR.isState()) {
                return new R(false, "调用微服务工作流失败", "");
            }

            //调用微服务成功，更新dormitory_plan表中的PLAN_STATUS，NEXT_PERSON_ID，NEXT_PERSON_NAME，CURRENT_PERSON_ID，CURRENT_PERSON_NAME,ACTIVITY_ID
            //获取ACTIVITY_ID
            JSONObject jbresult = JSONObject.parseObject((String) activityCallR.getResult());
            String processInstanceId = jbresult.getString("processInstanceId");
            String planStatus = jbresult.getString("status");
            dormitory.setPlanStatus(planStatus);
            dormitory.setCurrentPersonId((String) paramMap.get("nextPersonId"));
            dormitory.setCurrentPersonName((String) paramMap.get("nextPersonName"));
            dormitory.setActivityId(processInstanceId);
            dormitory.setUpdateTime(PlugDateUtil.getCurDateTime());
            Boolean saveReslt = dormitoryPlanService.saveOrUpdate(dormitory);
            if (!saveReslt) {
                return new R(false, "调用工作流程后回更失败", "");
            }

            return new R(true, "操作成功", "");
        } catch (Exception e) {
            logger.error("操作失败-=- {}", e.toString());
            return new R(false, "操作失败", e.toString());
        }
    }
}