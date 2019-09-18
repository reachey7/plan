package com.hb.service.impl;

import com.hb.entity.DormitoryBedStu;
import com.hb.entity.DormitoryDormStuChange;
import com.hb.entity.R;
import com.hb.entity.StudentInfo;
import com.hb.entity.SysBed;
import com.hb.mapper.DormitoryBedStuMapper;
import com.hb.mapper.DormitoryDormStuChangeMapper;
import com.hb.mapper.StudentInfoMapper;
import com.hb.mapper.SysBedMapper;
import com.hb.mapper.SysDormMapper;
import com.hb.service.IDormitoryDormStuChangeService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirc
 * @since 2019-09-18
 */
@Service("dormitoryDormStuChangeService")
@Transactional
public class DormitoryDormStuChangeServiceImpl extends ServiceImpl<DormitoryDormStuChangeMapper, DormitoryDormStuChange> implements IDormitoryDormStuChangeService {
	@Autowired
	public DormitoryDormStuChangeMapper dormitoryDormStuChangeMapper;

	@Autowired
	public StudentInfoMapper studentInfoMapper;

	@Autowired
	public SysBedMapper sysBedMapper;

	@Autowired
	public SysDormMapper sysDormMapper;

	private static Logger log = LoggerFactory.getLogger(DormitoryDormStuChangeServiceImpl.class);

	@Override
	public R saveStuChange(Map<String, Object> paramMap) {
		
		//判断传入的stuId、afterBedId、beforeBedId是否真实
		SysBed afterBed = sysBedMapper.selectById((String)paramMap.get("afterBedId"));
		if(afterBed == null){
			return new R(false, "根据afterBedId未找到数据", "");
		}
		
		SysBed beforeBed = sysBedMapper.selectById((String)paramMap.get("beforeBedId"));
		if(beforeBed == null){
			return new R(false, "根据beforBedId未找到数据", "");
		}
		
		StudentInfo stuInfo = studentInfoMapper.selectById((String)paramMap.get("stuId"));
		if(stuInfo == null){
			return new R(false, "根据stuId未找到数据", "");
		}
		
		//1.dorm_stu_change表中插入数据
		DormitoryDormStuChange dDormStuChange = new DormitoryDormStuChange();
		dDormStuChange.setAfterBedId((String)paramMap.get("afterBedId"));
		dDormStuChange.setBeforeBedId((String)paramMap.get("beforeBedId"));
		dDormStuChange.setCreateDate(PlugDateUtil.getCurDateTime());
		dDormStuChange.setId(IdUtil.createSerialSS(""));
		dDormStuChange.setOperatorPersonId((String)paramMap.get("operatorPersonId"));
		dDormStuChange.setOperatorPersonName((String)paramMap.get("operatorPersonName"));
		dDormStuChange.setRemark((String)paramMap.get("remark"));
		dDormStuChange.setState("1");
		dDormStuChange.setStudentId((String)paramMap.get("stuId"));
		dDormStuChange.setType("0");
		dDormStuChange.setUpdateReasonCode((String)paramMap.get("updateReasonCode"));
		dDormStuChange.setUpdateReasonName((String)paramMap.get("updateReasonName"));
		dDormStuChange.setAfterDormId((String)paramMap.get("afterDormId"));
		dDormStuChange.setBeforeDormId((String)paramMap.get("afterDormId"));
		
		int stuChanResult = dormitoryDormStuChangeMapper.insert(dDormStuChange);
		if (stuChanResult == 0) {
			throw new RuntimeException("插入dorm_stu_change异常");
		}

		//2.将原来的床位释放，sys_bed表中的BED_STATUS改为0，空闲
		beforeBed.setBedStatus(0);
		int beforeBedResult = sysBedMapper.updateById(beforeBed);
		if (beforeBedResult == 0) {
			throw new RuntimeException("更新原来床位状态异常");
		}
				
		//3.将新床位改为1，已入住
		afterBed.setBedStatus(1);
		int afterBedResult = sysBedMapper.updateById(afterBed);
		if (afterBedResult == 0) {
			throw new RuntimeException("更新新床位状态异常");
		}
		
		return new R(true, "成功", "");
	}

	@Override
	public Page<Map> selectStuChange(Page page, Map<String, Object> param) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		resultList = dormitoryDormStuChangeMapper.selectStuChange(page, param);
		page.setRecords(resultList);
		return page;
	}
}
