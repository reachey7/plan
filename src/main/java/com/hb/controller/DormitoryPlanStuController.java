package com.hb.controller;


import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.entity.DormitoryPlan;
import com.hb.entity.DormitoryPlanBed;
import com.hb.entity.DormitoryPlanStu;
import com.hb.entity.R;
import com.hb.mapper.DormitoryPlanBedMapper;
import com.hb.mapper.DormitoryPlanStuMapper;
import com.hb.service.IDormitoryPartService;
import com.hb.service.IDormitoryPlanService;
import com.hb.service.IDormitoryPlanStuService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;
import com.hb.util.SysHelperUtil;

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
    public DormitoryPlanStuMapper dormitoryPlanStuMapper;
    @Autowired
    public DormitoryPlanBedMapper dormitoryPlanBedMapper;
    
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 新增,将
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public R add(@RequestBody DormitoryPlanStu dormitoryPlanStu) {
        try {
        	//添加之前先校验之前是否添加过
        	QueryWrapper<DormitoryPlanStu> queryStuWrapper =  new QueryWrapper<>();
            queryStuWrapper.eq("STUDENT_ID", dormitoryPlanStu.getStudentId());
            queryStuWrapper.eq("PLAN_ID", dormitoryPlanStu.getPlanId());
            List<DormitoryPlanStu> queryStuWrapperList = dormitoryPlanStuMapper.selectList(queryStuWrapper);
        	if(queryStuWrapperList!=null && queryStuWrapperList.size()>0){
        		return new R(true, "添加成功", "");
        	}
            
        	dormitoryPlanStu.setCreateDate(PlugDateUtil.getCurDateTime());
        	dormitoryPlanStu.setState("1");
        	dormitoryPlanStu.setId(IdUtil.createSerialSS(""));
            boolean result = dormitoryPlanStuService.save(dormitoryPlanStu);
            if (!result) {
                return new R(false, "新增失败", "");
            }
        } catch (Exception e) {
            logger.error("dormitoryPlanStuSave -=- {}", e.toString());
            return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", dormitoryPlanStu);
    }
    /**
     * 新增,将
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/addList")
    public R addList(@RequestBody Map<String, Object> paramMap) {
        try {
        	//首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("planId");
        	list.add("operatorId");
        	list.add("stuSex");
        	list.add("stuType");
        	 R checkR = SysHelperUtil.check(list, paramMap);
             if (!checkR.isState()) {
                 return checkR;
             }
        	
        	//获取学生性别和类型，然后去表中将符合条件的学生信息查询出来
            String planId = paramMap.get("planId")+"";
            String operatorId = paramMap.get("operatorId")+"";
            String stuSex = paramMap.get("stuSex")+"";
            String stuType = paramMap.get("stuType")+"";
        
            Page page = new Page(0,999999);
            Map<String, Object> selectMap = new HashMap<String, Object>();
            selectMap.put("sex",stuSex);
            selectMap.put("stuType",stuType);
            selectMap.put("isCheckin","0");
            List<Map<String, Object>> studentList = dormitoryPlanStuMapper.selectStudent(page, selectMap);
            if(studentList.size()<1){
            	return new R(false, "根据条件未找到对应学生", ""); 
            }
            List<DormitoryPlanStu> planStuList = new ArrayList<DormitoryPlanStu>();
            List<DormitoryPlanStu> planStuListCopy = new ArrayList<DormitoryPlanStu>();
            List<String> studentIdList = new ArrayList<String>();
            
            for(Map<String, Object> map : studentList){
            	DormitoryPlanStu tmp =  new  DormitoryPlanStu();
            	String sutdentId = "";
            	tmp.setId(IdUtil.createSerialSS(""));
            	tmp.setCreateDate(PlugDateUtil.getCurDateTime());
            	tmp.setOperatorId(operatorId);
            	tmp.setPlanId(planId);
            	tmp.setState("1");
            	tmp.setStudentId(map.get("ID")+"");
            	planStuList.add(tmp);
            	studentIdList.add(map.get("ID")+"");
            }
          
            //根据添加的学生，在plan_stu表中查询是否已经添加过，如果已经包含存在的数据，则将前台提交的list进行移除，然后再批量添加
            QueryWrapper<DormitoryPlanStu> queryStuWrapper =  new QueryWrapper<>();
            queryStuWrapper.in("STUDENT_ID", studentIdList);
            queryStuWrapper.eq("PLAN_ID", planId);
            List<DormitoryPlanStu> queryStuWrapperList = dormitoryPlanStuMapper.selectList(queryStuWrapper);
            
            boolean result = false;
            //进行剔重
            if(queryStuWrapperList!=null && queryStuWrapperList.size()>0){
            	for(DormitoryPlanStu updps : planStuList){
            		boolean check = true;
            		for(DormitoryPlanStu dps : queryStuWrapperList){
            			if(updps.getStudentId().equals(dps.getStudentId())){
            				check =false;
            			}
            		}
            		if(check){
            			planStuListCopy.add(updps);
            		}
            	}
            	//查询学生信息
                 result =  dormitoryPlanStuService.saveOrUpdateBatch(planStuListCopy);
            }else{
            	 result =  dormitoryPlanStuService.saveOrUpdateBatch(planStuList);
            }
            
            if (!result) {
                return new R(false, "新增失败", "");
            }
        } catch (Exception e) {
            logger.error("dormitoryPlanStuSave -=- {}", e.toString());
            return new R(true, "新增失败", "");
        }
        return new R(true, "新增成功", "");
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
    
    
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/deleteByStuId")
    public R deleteByStuId(@RequestBody Map paramMap) {
        try {
        	
        	//首先判断必填项
            List<String> list = new ArrayList<>();
            list.add("stuId");
            list.add("planId");
            
            R checkR = SysHelperUtil.check(list, paramMap);
            if (!checkR.isState()) {
                return checkR;
            }
            
        	//获取学生在计划中的数据信息
            QueryWrapper<DormitoryPlanStu> queryStuWrapper =  new QueryWrapper<>();
            queryStuWrapper.eq("STUDENT_ID", (String)paramMap.get("stuId"));
            queryStuWrapper.eq("PLAN_ID", (String)paramMap.get("planId"));
            List<DormitoryPlanStu> queryStuWrapperList = dormitoryPlanStuMapper.selectList(queryStuWrapper);

            Boolean result = dormitoryPlanStuService.removeById(queryStuWrapperList.get(0).getId());
            if (!result) {
                return new R(false, "删除失败", "");
            }
            return new R(true, "删除成功", "");
        } catch (Exception e) {
            logger.error("dormitoryPlanStuDelete -=- {}", e.toString());
            return new R(true, "删除失败", "");
        }
    }

    /**
     * 查询学生
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
                    page = new Page(Integer.parseInt(paramMap.get("current") + ""), Integer.parseInt(paramMap.get("size") + ""));
                }
                page = dormitoryPlanStuService.selectStudent(page,paramMap);
                return new R(true, "查询成功", page);
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
     * 查询计划中的学生与选中的床位
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/queryPlanStuBed")
    public R queryPlanStuBed(@RequestBody Map paramMap) {
        try {
            try {
            	Map<String, Object> resultMap =  new HashMap<String, Object>();
            	
            	//首先判断必填项
                List<String> list = new ArrayList<>();
                list.add("planId");
                R checkR = SysHelperUtil.check(list, paramMap);
                if (!checkR.isState()) {
                    return checkR;
                }
                
                //查询计划中的学生
            	QueryWrapper<DormitoryPlanStu> queryStuWrapper =  new QueryWrapper<>();
            	queryStuWrapper.eq("planId", paramMap.get("planId")+"");
                List<Map<String, Object>> resultStuList = dormitoryPlanStuMapper.selectMaps(queryStuWrapper);
                
                //查询计划中的床位
                QueryWrapper<DormitoryPlanBed> queryBedWrapper =  new QueryWrapper<>();
                queryBedWrapper.eq("planId", paramMap.get("planId")+"");
                List<Map<String, Object>> resultBedList = dormitoryPlanBedMapper.selectMaps(queryBedWrapper);
                
                resultMap.put("STU_NUMBER", resultStuList.size());
                resultMap.put("BED_NUMBER", resultBedList.size());
                
                return new R(true, "查询成功",resultMap);
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