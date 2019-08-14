package com.hb.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.*;
import com.hb.mapper.*;
import com.hb.service.*;
import com.hb.util.IdUtil;
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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lirc
 * @since 2019-08-12
 */
@Controller
@RequestMapping("/dormitoryStuBedPlan")
public class DormitoryStuBedPlanController {
    private final Logger logger = LoggerFactory.getLogger(DormitoryStuBedPlanController.class);

    @Autowired
    public IDormitoryStuBedPlanService dormitoryStuBedPlanService;
    @Autowired
    public IDormitoryPlanStuService dormitoryPlanStuService;
    @Autowired
    public IDormitoryPlanBedService dormitoryPlanBedService;
    @Autowired
    public IDormitoryPartStuService dormitoryPartStuService;
    @Autowired
    public IDormitoryPartBedService dormitoryPartBedService;
    @Autowired
    public IDormitoryPlanService dormitoryPlanService;
    @Autowired
    public IDormitoryPartService dormitoryPartService;
    @Autowired
    private RestTemplate restTemplate;
   /* @Autowired
    public DormitoryStuBedPlanMapper dormitoryStuBedPlanMapper;
    @Autowired
    public DormitoryPlanStuMapper dormitoryPlanStuMapper;
    @Autowired
    public DormitoryPlanBedMapper dormitoryPlanBedMapper;
    @Autowired
    public DormitoryPartStuMapper dormitoryPartStuMapper;
    @Autowired
    public DormitoryPartBedMapper dormitoryPartBedMapper;*/

    /**
     * 新增
     * 1.前台将学生列表、床位列表、划分ID、操作人、计划ID、分配类型(0:PC,1:H5)以及分配规则传入
     * 2.服务端根据学生列表以及床位列表分别去dormitory_part_stu、dormitory_part_bed两张表，按照分配规则排序查出两列结果
     * 3.按照排序好的两个列表进行插入dormitory_stu_bed_plan
     * 4.更新dormitory_part:PART_STATUS,STUDENT_NUMBER,BED_NUMBER
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public R add(@RequestBody Map paramMap) {
        try {
            //首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("partId");
            list.add("planId");
            list.add("studentList");
            list.add("bedList");
            list.add("operatorPerson");
            list.add("type");
            list.add("fenpeiRule");


            R checkR = SysHelperUtil.check(list, paramMap);
            if (!checkR.isState()) {
                return checkR;
            }

            //判断学生列表与床位列表长度是否大于等于1
            if (((List) paramMap.get("studentList")).size() < 1 || ((List) paramMap.get("bedList")).size() < 1) {
                return new R(false, "选择的学生列表和床位列表不能为空!", "");
            }

            //判断床位数量是否大于学生数量
            if (((List) paramMap.get("studentList")).size() > ((List) paramMap.get("bedList")).size()) {
                return new R(false, "选择的学生数量一定要小于床位数量!", "");
            }

            //根据分配规则查询学生列表
            Page page = new Page(0, 9999999);
            Map<String, Object> partStuParamMap = new HashMap<>();
            partStuParamMap.put("partId", (String) paramMap.get("partId"));
            partStuParamMap.put("fenpeiRule", (String) paramMap.get("fenpeiRule"));
            partStuParamMap.put("studentIdList", (List) paramMap.get("studentList"));
            Page partStuPage = dormitoryPartStuService.selectPartStu(page, partStuParamMap);
            if (partStuPage == null || partStuPage.getSize() <= 0) {
                return new R(false, "选择的学生列表未在数据中找到!", "");

            }

            //根据床位排序查询床位列表
            Map<String, Object> planBedParamMap = new HashMap<>();
            planBedParamMap.put("partId", (String) paramMap.get("partId"));
            planBedParamMap.put("bedIdList", (List) paramMap.get("bedIdList"));
            Page partBedPage = dormitoryPartBedService.selectPartBed(page, planBedParamMap);
            if (partBedPage == null || partBedPage.getSize() <= 0) {
                return new R(false, "选择的床位列表未在数据中找到!", "");
            }


            //将学生列表和床位列表匹配，并插入数据库中
            List<DormitoryStuBedPlan> stuBedPlanList = new ArrayList();
            for (int i = 0; i <= partStuPage.getRecords().size(); i++) {
                for (int j = 0; j <= partBedPage.getRecords().size(); j++) {
                    DormitoryStuBedPlan dsbp = new DormitoryStuBedPlan();
                    dsbp.setId(IdUtil.createSerialSS(""));
                    dsbp.setBedId(((DormitoryPartBed) partBedPage.getRecords().get(j)).getBedId());
                    dsbp.setCreateDate(PlugDateUtil.getCurDateTimeHS());
                    dsbp.setOperatorPerson(paramMap.get("operatorPerson") + "");
                    dsbp.setPartId(((DormitoryPartBed) partBedPage.getRecords().get(j)).getPartId());
                    dsbp.setPlanId(((DormitoryPartBed) partBedPage.getRecords().get(j)).getPartId());
                    dsbp.setStuId(((DormitoryPartStu) partStuPage.getRecords().get(j)).getStudentId());
                    dsbp.setType(paramMap.get("type") + "");
                    stuBedPlanList.add(dsbp);
                }
            }
            //批量插入数据库中
            Boolean result = dormitoryStuBedPlanService.saveBatch(stuBedPlanList);
            if (result) {
                 /*
                //更新dormitory_part:PART_STATUS,STUDENT_NUMBER,BED_NUMBER
                DormitoryPart part = new DormitoryPart();
                //查出该划分下面多少个学生
                QueryWrapper<DormitoryPartStu> partStuWrapper =  new QueryWrapper<>();
                partStuWrapper.eq("PART_ID", paramMap.get("partId") + "");
                Integer partStuNumber = dormitoryPartStuService.list(partStuWrapper).size();

                //查出该划分下面多少个床位
                QueryWrapper<DormitoryPartBed> partBedWrapper =  new QueryWrapper<>();
                partBedWrapper.eq("PART_ID", paramMap.get("partId") + "");
                Integer partBedNumber = dormitoryPartBedService.list(partBedWrapper).size();*/
                return new R(true, "操作成功", "");
            } else {
                return new R(false, "操作失败", "");
            }

        } catch (Exception e) {
            logger.error("dormitoryStuBedPlanSave -=- {}", e.toString());
            return new R(true, "新增失败", "");
        }
    }


    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public R update(@RequestBody DormitoryStuBedPlan dormitoryStuBedPlan) {
        try {
            DormitoryStuBedPlan tmp = dormitoryStuBedPlanService.getById(dormitoryStuBedPlan.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryStuBedPlanService.updateById(dormitoryStuBedPlan);
            if (!result) {
                return new R(true, "修改失败", "");
            }
            return new R(true, "修改成功", dormitoryStuBedPlan);
        } catch (Exception e) {
            logger.error("DormitoryStuBedPlanUpdate -=- {}", e.toString());
            return new R(true, "修改失败", "");
        }

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public R delete(@RequestBody DormitoryStuBedPlan dormitoryStuBedPlan) {
        try {
            DormitoryStuBedPlan tmp = dormitoryStuBedPlanService.getById(dormitoryStuBedPlan.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryStuBedPlanService.removeById(dormitoryStuBedPlan.getId());
            if (!result) {
                return new R(true, "删除失败", "");
            }
            return new R(true, "删除成功", dormitoryStuBedPlan);
        } catch (Exception e) {
            logger.error("dormitoryStuBedPlanDelete -=- {}", e.toString());
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
                page = dormitoryStuBedPlanService.selectStuBedPlan(page, paramMap);
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
     * 分配完成，调用微服务的工作流，接受工作流返回的信息并记录数据库
     * 参数包括
     * 工作流ID：activityId
     * taskID
     * 计划ID：planId
     * 当前操作人：currentPersonId和currentPersonName
     * 下一环节操作人：nextPersonId和nextPersonName
     * <p>
     * 方法流程：
     * 1.首先判断planId，当前操作人，下一环节操作人是否传入
     * 2.判断planId是否在数据库存在
     * 3.调用工作流微服务
     * 4.根据工作流返回的结果判断是否成功，
     * 4.1如果成功则更新dormitory_plan表中的PLAN_STATUS，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
     * 4.2如果成功则更新dormitory_part表中的PART_STATUS为2，已完成
     * 如果失败则返回给前台，允许前台再次调用
     *
     * @author lirc
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/assignSubmit")
    public R assignSubmit(@RequestBody Map<String, Object> paramMap) {
        try {

            //首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("partId");
            list.add("planId");
            list.add("currentPersonId");
            list.add("taskId");
            list.add("activityId");
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
            activityJb.put("activityId", (String) paramMap.get("activityId"));
            activityJb.put("taskId", (String)paramMap.get("taskId"));
            R activityCallR = SysHelperUtil.callActivity(activityJb, restTemplate, "fenpeiSubmit");
            if (!activityCallR.isState()) {
                return new R(false, "调用微服务工作流失败", "");
            }

            //调用微服务成功，更新dormitory_part表中的PART_STATUS，NEXT_PERSON_ID，NEXT_PERSON_NAME，CURRENT_PERSON_ID，CURRENT_PERSON_NAME
            JSONObject jbresult = JSONObject.parseObject((String) activityCallR.getResult());
            String planStatus = jbresult.getString("status");
            dormitory.setPlanStatus(planStatus);
            dormitory.setCurrentPersonId(jbresult.getString("currentPersonId"));
            Boolean saveReslt = dormitoryPlanService.saveOrUpdate(dormitory);
            if (!saveReslt) {
                return new R(false, "调用工作流程后回更失败", "");
            }

            
            //如果成功则更新dormitory_part表中的PART_STATUS为2，已完成
            DormitoryPart dormitoryPart = dormitoryPartService.getById((String) paramMap.get("partId"));
            dormitoryPart.setPartStatus("2");
            Boolean savepartReslt = dormitoryPartService.saveOrUpdate(dormitoryPart);
            if (!savepartReslt) {
                return new R(false, "调用工作流程后回更失败", "");
            }
            return new R(true, "操作成功", "");
        } catch (Exception e) {
            logger.error("操作失败-=- {}", e.toString());
            return new R(false, "操作失败", e.toString());
        }
    }

}