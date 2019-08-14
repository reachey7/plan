package com.hb.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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


import com.hb.service.IDormitoryPartStuService;
import com.hb.entity.DormitoryPartStu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hb.entity.R;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


    /**
     * 新增：
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public R add(@RequestBody List<DormitoryPartStu> dormitoryPartStuList) {
        try {
            for (DormitoryPartStu dps : dormitoryPartStuList) {
                dps.setState("0");
                boolean result = dormitoryPartStuService.save(dps);
                if (!result) {
                    return new R(true, "新增失败", "");
                }
            }

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
            DormitoryPartStu tmp = dormitoryPartStuService.getById(dormitoryPartStu.getId());
            if (tmp == null) {
                return new R(true, "根据ID查找数据并不存在", "");
            }

            Boolean result = dormitoryPartStuService.removeById(dormitoryPartStu.getId());
            if (!result) {
                return new R(true, "删除失败", "");
            }
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
                page = new Page(Integer.parseInt(paramMap.get("size") + ""), Integer.parseInt(paramMap.get("size") + ""));
            }
            page = dormitoryPartStuService.selectPartStu(page,paramMap);
            return new R(true, "查询成功", page.getRecords());
        } catch (Exception e) {
            logger.error("dormitoryPartStuService -=- {}", e.toString());
            return new R(true, "查询失败", "");
        }
    }


}