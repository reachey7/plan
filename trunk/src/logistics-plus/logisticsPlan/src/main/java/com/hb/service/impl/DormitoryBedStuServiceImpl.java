package com.hb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.entity.DormitoryBedStu;
import com.hb.entity.R;
import com.hb.entity.StudentInfo;
import com.hb.entity.SysBed;
import com.hb.mapper.DormitoryBedStuMapper;
import com.hb.mapper.DormitoryPartBedMapper;
import com.hb.mapper.StudentInfoMapper;
import com.hb.mapper.SysBedMapper;
import com.hb.mapper.SysDormMapper;
import com.hb.service.IDormitoryBedStuService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirc
 * @since 2019-08-12
 */
@Service("dormitoryBedStuService")
@Transactional
public class DormitoryBedStuServiceImpl extends ServiceImpl<DormitoryBedStuMapper, DormitoryBedStu> implements IDormitoryBedStuService {

	@Autowired
	public DormitoryBedStuMapper dormitoryBedStuMapper;

	@Autowired
	public StudentInfoMapper studentInfoMapper;

	@Autowired
	public SysBedMapper sysBedMapper;

	@Autowired
	public SysDormMapper sysDormMapper;

	private static Logger log = LoggerFactory.getLogger(DormitoryBedStuServiceImpl.class);

	@Override
	public R saveBedStu(Map<String, Object> paramMap) {
		// 根据stuId和bedId查询学生信息和床位信息
		SysBed sysBed = sysBedMapper.selectById(paramMap.get("bedId") + "");
		if (sysBed == null || !"0".equals(sysBed.getBedStatus() + "")) {
			return new R(true, "根据传入的bedId未找到对应数据,或者床位已被占用", "");
		}

		StudentInfo stuInfo = studentInfoMapper.selectById(paramMap.get("stuId") + "");
		if (stuInfo == null) {
			return new R(true, "根据传入的stuId未找到对应数据", "");
		}

		// 1.bed_stu插入数据
		DormitoryBedStu dbedStu = new DormitoryBedStu();
		dbedStu.setBedId(paramMap.get("bedId") + "");
		dbedStu.setBeginDate(PlugDateUtil.getCurDateTime());
		dbedStu.setEndDate("2050-01-01 23:59:59");
		dbedStu.setId(IdUtil.createSerialSS(""));
		dbedStu.setInMode("1");
		dbedStu.setOperatDate(PlugDateUtil.getCurDateTime());
		dbedStu.setOperatorPersonId(paramMap.get("operatorPersonId") + "");
		dbedStu.setOperatorPersonName(paramMap.get("operatorPersonName") + "");
		dbedStu.setState("1");
		dbedStu.setStudentId(paramMap.get("stuId") + "");
		int bedStuResult = dormitoryBedStuMapper.insert(dbedStu);

		if (bedStuResult == 0) {
			throw new RuntimeException("插入BedStu异常");
		}

		// 2.sys_bed表中的BED_STATUS改1，已入住
		sysBed.setBedStatus(1);
		int sysBedResult = sysBedMapper.updateById(sysBed);
		if (sysBedResult == 0) {
			throw new RuntimeException("更新sysBed异常");
		}

		// 3.sys_stu表中的IS_CHECKIN改1，是
		stuInfo.setIsCheckin(1);
		int studentInfoResult = studentInfoMapper.updateById(stuInfo);
		if (studentInfoResult == 0) {
			throw new RuntimeException("更新student_info异常");
		}

		return new R(true, "成功", "");
	}


	@Override
	public Page<Map> selectBedStu(Page page, Map<String, Object> param) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		resultList = dormitoryBedStuMapper.selectBedStu(page, param);
		page.setRecords(resultList);
		return page;
	}

}
