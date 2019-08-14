package com.hb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryBedStu;
import com.hb.service.IDormitoryPlanService;
import com.hb.util.PlugDateUtil;
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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.hb.service.IDormitoryPartService;
import com.hb.util.SysHelperUtil;
import com.hb.entity.DormitoryPart;
import com.hb.entity.DormitoryPlan;

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
 * @since 2019-08-08
 */
@Controller
@RequestMapping("/dormitoryPart")
public class DormitoryPartController {
    private final Logger logger = LoggerFactory.getLogger(DormitoryPartController.class);

    @Autowired
    public IDormitoryPartService dormitoryPartService;
    @Autowired
    public IDormitoryPlanService dormitoryPlanService;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 新增
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public R add(@RequestBody DormitoryPart dormitoryPart) {
        try {
            dormitoryPart.setPartStatus("0");
            boolean result = dormitoryPartService.save(dormitoryPart);
            if (!result) {
                return new R(true, "新增失败", "");
            }
        } catch (Exception e) {
            logger.error("dormitoryPartSave -=- {}", e.toString());
            return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", dormitoryPart);
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public R update(@RequestBody DormitoryPart dormitoryPart) {
        try {
            DormitoryPart tmp = dormitoryPartService.getById(dormitoryPart.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryPartService.updateById(dormitoryPart);
            if (!result) {
                return new R(true, "修改失败", "");
            }
            return new R(true, "修改成功", dormitoryPart);
        } catch (Exception e) {
            logger.error("DormitoryPartUpdate -=- {}", e.toString());
            return new R(true, "修改失败", "");
        }

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public R delete(@RequestBody DormitoryPart dormitoryPart) {
        try {
            DormitoryPart tmp = dormitoryPartService.getById(dormitoryPart.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryPartService.removeById(dormitoryPart.getId());
            if (!result) {
                return new R(true, "删除失败", "");
            }
            return new R(true, "删除成功", dormitoryPart);
        } catch (Exception e) {
            logger.error("dormitoryPartDelete -=- {}", e.toString());
            return new R(true, "删除失败", "");
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
                page = new Page(0, 1);
            } else {
                page = new Page(Integer.parseInt(paramMap.get("size") + ""), Integer.parseInt(paramMap.get("size") + ""));
            }

            Page listPage = dormitoryPartService.selectDormitoryPart(page, paramMap);
            return new R(true, "查询成功", listPage);

        } catch (Exception e) {
            logger.error("dormitoryPartService -=- {}", e.toString());
            return new R(true, "查询失败", e.toString());
        }
    }

    /**
     * 划分完成，调用微服务的工作流，接受工作流返回的信息并记录数据库
     * 参数包括
     * 工作流ID:activityId
     * 划分ID：partId
     * 计划ID：planId
     * 当前操作人：currentPersonId和currentPersonName
     * 下一环节操作人：nextPersonId和nextPersonName
     * <p>
     * 方法流程：
     * 1.首先判断划分ID，当前操作人，下一环节操作人是否传入
     * 2.判断partId是否在数据库存在
     * 3.调用工作流微服务
     * 3.1 由于activity微服务中在创建划分时默认多创建一个划分，所以分为两个方法。一个是huafenSubmit一个是huafenMSubmit
     * 3.2 判断当前计划下的划分是否有完成的划分，如果有则调用huafenMSubmit。dormitory_part表中的part_status=1则为完成
     * 4.根据工作流返回的结果判断是否成功，
     * 4.1如果成功则更新dormitory_plan表中的PLAN_STATUS，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
     * 4.2再根据微服务返回的信息，更新dormitory_part表中的TASK_ID以及表中的status，更新为1已划分
     * 如果失败则返回给前台，允许前台再次调用
     *
     * @author lirc
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/partSubmit")
    public R partSubmit(@RequestBody Map<String, Object> paramMap) {
        try {

            //首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("partId");
            list.add("planId");
            list.add("activityId");
            list.add("currentPersonId");
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


            //调用微服务工作流
            JSONObject activityJb = new JSONObject();
            activityJb.put("currentPersonId", (String) paramMap.get("currentPersonId"));
            List<String> nextList = new ArrayList<String>();
            nextList.add((String) paramMap.get("nextPersonId"));
            activityJb.put("nextPersonId", nextList);
            activityJb.put("processInstanceId", (String) paramMap.get("activityId"));
            activityJb.put("huafenType", "0");

            //判断当前计划下的划分是否有完成的划分
            String url = "huafenSubmit";
            QueryWrapper<DormitoryPart> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PART_STATUS", "1");
            List partList = dormitoryPartService.list(queryWrapper);
            if (partList != null && partList.size() >= 1) {
                url = "huafenMSubmit";
            }

            R activityCallR = SysHelperUtil.callActivity(activityJb, restTemplate, url);
            if (!activityCallR.isState()) {
                return new R(false, "调用微服务工作流失败", "");
            }

            //调用微服务成功，
            // 4.1更新dormitory_plan表中的PLAN_STATUS，NEXT_PERSON_ID，NEXT_PERSON_NAME，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
            JSONObject jbresult = JSONObject.parseObject((String) activityCallR.getResult());
            String planStatus = jbresult.getString("status");
            dormitory.setPlanStatus(planStatus);
            dormitory.setCurrentPersonId((String) paramMap.get("nextPersonId"));
            dormitory.setCurrentPersonName((String) paramMap.get("nextPersonName"));
            Boolean saveReslt = dormitoryPlanService.saveOrUpdate(dormitory);
            if (!saveReslt) {
                return new R(false, "调用工作流程后回更计划表失败", "");
            }


            //4.2再根据微服务返回的信息，更新dormitory_part表中的TASK_ID以及表中的status，更新为1已完成
            DormitoryPart dpart = dormitoryPartService.getById((String) paramMap.get("partId"));
            dpart.setPartStatus("1");
            dpart.setTaskId(jbresult.getString("taskId"));
            //dpart.setTaskId("111");
            Boolean updateResult = dormitoryPartService.updateById(dpart);
            if (!updateResult) {
                return new R(false, "调用工作流程后回更划分表失败", "");
            }


            return new R(true, "操作成功", "");
        } catch (Exception e) {
            logger.error("操作失败-=- {}", e.toString());
            return new R(false, "操作失败", e.toString());
        }
    }


    /**
     * 划分结束，调用微服务的工作流，接受工作流返回的信息并记录数据库
     * 参数包括
     * 工作流ID:activityId
     * 计划ID:planId
     * 当前操作人：currentPersonId和currentPersonName
     * <p>
     * 方法流程：
     * 1.首先判断划分ID、计划ID以及当前操作人是否传入
     * 2.判断planId是否在数据库存在
     * 3.调用工作流微服务
     * 4.根据工作流返回的结果判断是否成功，如果成功则更新dormitory_plan表中的PLAN_STATUS，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
     * 如果失败则返回给前台，允许前台再次调用
     *
     * @author lirc
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/partEnd")
    public R partEnd(@RequestBody Map<String, Object> paramMap) {
        try {

            //首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("activityId");
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
            activityJb.put("currentPersonId", (String) paramMap.get("currentPersonId"));

            List<String> nextPersonIdList = new ArrayList<>();
            nextPersonIdList.add((String) paramMap.get("nextPersonId"));

            activityJb.put("nextPersonId", nextPersonIdList);
            activityJb.put("processInstanceId", (String) paramMap.get("activityId"));
            activityJb.put("huafenType", "1");
            activityJb.put("partId", (String)paramMap.get("partId"));


            R activityCallR = SysHelperUtil.callActivity(activityJb, restTemplate, "huafenSubmit");
            if (!activityCallR.isState()) {
                return new R(false, "调用微服务工作流失败", "");
            }

            //调用微服务成功，更新dormitory_plan表中的PLAN_STATUS，NEXT_PERSON_ID，NEXT_PERSON_NAME，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
            JSONObject jbresult = JSONObject.parseObject((String) activityCallR.getResult());
            String planStatus = jbresult.getString("status");
            dormitory.setPlanStatus(planStatus);
            dormitory.setCurrentPersonId((String) paramMap.get("nextPersonId"));
            dormitory.setCurrentPersonName((String) paramMap.get("nextPersonName"));
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


    /**
     * 划分删除学生，调用微服务的工作流，接受工作流返回的信息并记录数据库
     * 参数包括
     * 工作流ID:activityId
     * 计划ID:planId
     * 任务ID：taskId
     * 当前操作人：currentPersonId和currentPersonName
     * <p>
     * 方法流程：
     * 1.首先参数是否传入
     * 2.判断planId是否在数据库存在
     * 3.调用工作流微服务
     * 4.根据工作流返回的结果判断是否成功，如果成功则更新dormitory_plan表中的PLAN_STATUS，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
     * 如果失败则返回给前台，允许前台再次调用
     *
     * @author lirc
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/partDeleteStu")
    public R partDeleteStu(@RequestBody Map<String, Object> paramMap) {
        try {

            //首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("planId");
            list.add("activityId");
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
            activityJb.put("currentPersonId", (String) paramMap.get("currentPersonId"));
            activityJb.put("taskId", (String) paramMap.get("taskId"));
            activityJb.put("huafenType", "2");


            R activityCallR = SysHelperUtil.callActivity(activityJb, restTemplate, "huafenSubmit");
            if (!activityCallR.isState()) {
                return new R(false, "调用微服务工作流失败", "");
            }

            //调用微服务成功，更新dormitory_plan表中的PLAN_STATUS，NEXT_PERSON_ID，NEXT_PERSON_NAME，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
            JSONObject jbresult = JSONObject.parseObject((String) activityCallR.getResult());
            String planStatus = jbresult.getString("status");
            dormitory.setPlanStatus(planStatus);
            dormitory.setCurrentPersonId(jbresult.getString("nextPersonId"));
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