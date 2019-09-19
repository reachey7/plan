package com.hb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.entity.DormitoryFeeStu;
import com.hb.entity.DormitoryFeeStuitem;
import com.hb.entity.R;
import com.hb.mapper.DormitoryFeeStuMapper;
import com.hb.mapper.DormitoryFeeStuitemMapper;
import com.hb.service.IDormitoryFeeStuService;
import com.hb.util.IdUtil;
import com.hb.util.PlugDateUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lirc
 * @since 2019-09-18
 */
@Service("dormitoryFeeStuService")
public class DormitoryFeeStuServiceImpl extends ServiceImpl<DormitoryFeeStuMapper, DormitoryFeeStu> implements IDormitoryFeeStuService {

	@Autowired
    private DormitoryFeeStuMapper dormitoryFeeStuMapper;

	@Autowired
    private DormitoryFeeStuitemMapper dormitoryFeeStuitemMapper;

    private static Logger log = LoggerFactory.getLogger(DormitoryFeeStuServiceImpl.class);

    @Override
    public Page<Map> selectFeeStu(Page page, Map<String, Object> param) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = dormitoryFeeStuMapper.selectFeeStu(page, param);
        page.setRecords(resultList);
        return page;
    }
    
    @Override
    public R insertFeeStu(List<Map<String,Object>> param) {
    	//1.插入主表fee_stu
   	    //2.插入子表fee_stuitem
        List<DormitoryFeeStu> dfeeStuList = new ArrayList<DormitoryFeeStu>();
        //插入前首先查询该学生在本年度是否有过缴费记录，如果有则进行累加动作，如果没有则直接插入
        for(Map<String, Object> map : param ){
        	Map<String, Object> queryMap = new HashMap<String, Object>();
        	queryMap.put("STUDENT_NUM", map.get("value1"));
        	queryMap.put("YEAR", map.get("value3"));
        	List<DormitoryFeeStu> feeStuList = dormitoryFeeStuMapper.selectByMap(queryMap);
        	DormitoryFeeStuitem dormitoryFeeStuitem =new DormitoryFeeStuitem();
        	 DormitoryFeeStu  dormitoryFeeStu =  new DormitoryFeeStu();
        	if(feeStuList==null || feeStuList.size()<1){
        		//不存在缴费记录，直接插入fee_stu表和fee_stuitem表
        		
        		 dormitoryFeeStu.setId(IdUtil.createSerialSS(""));
        		 dormitoryFeeStu.setPayFeeReal(map.get("value2")+"");
        		 
        		 String feeShould = getDormFee(map.get("value1")+"");
        		 if("".equals(feeShould)){
        			 return new R(false, map.get("value0")+"床位信息有误!", "");
        		 }
        		 dormitoryFeeStu.setPayFeeShould(feeShould);
        		 dormitoryFeeStu.setStudentName(map.get("value0")+"");
        		 dormitoryFeeStu.setStudentNum(map.get("value1")+"");
        		 dormitoryFeeStu.setYear(map.get("value3")+"");
        		 
        		 //判断是否已经缴清
        		 Double realPay = Double.parseDouble(dormitoryFeeStu.getPayFeeReal());
        		 Double shouldPay = Double.parseDouble(dormitoryFeeStu.getPayFeeShould());
        		 if(realPay>=shouldPay){
        			 dormitoryFeeStu.setIfOver("1");
        		 }
        		 dormitoryFeeStuMapper.insert(dormitoryFeeStu);
        	}else{
        		//存在缴费记录，更新fee_stu表中的PAY_FEE_REAL，并直接插入fee_stuitem表
        		 dormitoryFeeStu = feeStuList.get(0);
        		 Double payOld = Double.parseDouble(dormitoryFeeStu.getPayFeeReal());
        		 Double payNew = Double.parseDouble(map.get("value2")+"");
        		 dormitoryFeeStu.setPayFeeReal(String.valueOf(payOld+payNew));
        		 
        		 //判断是否已经缴清
        		 Double realPay = Double.parseDouble(dormitoryFeeStu.getPayFeeReal());
        		 Double shouldPay = Double.parseDouble(dormitoryFeeStu.getPayFeeShould());
        		 if(realPay>=shouldPay){
        			 dormitoryFeeStu.setIfOver("1");
        		 }
        		 dormitoryFeeStuMapper.updateById(dormitoryFeeStu);
        	}
        	//插入fee_stuitem表
        	dormitoryFeeStuitem.setCreateDate(PlugDateUtil.getCurDateTime());
        	dormitoryFeeStuitem.setCreateOperatePersonId(map.get("value4")+"");
        	dormitoryFeeStuitem.setFeeStuId(dormitoryFeeStu.getId());
        	dormitoryFeeStuitem.setId(IdUtil.createSerialSS(""));
        	dormitoryFeeStuitem.setItemPayFee(map.get("value2")+"");
        	dormitoryFeeStuitem.setState("1");
        	dormitoryFeeStuitemMapper.insert(dormitoryFeeStuitem);
        }
        
        return new R(true, "成功", "");
    }
    
    /**
    * <p>Description:根据学生查询所在宿舍费用 </p>
    * <p>Company: 和邦科技</p> 
    * @author lirc
    * @date 下午2:17:55
    */
    private String getDormFee(String stuNum){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("stuNum", stuNum);
    	List<Map<String, Object>> resultList = dormitoryFeeStuMapper.selectBedFee(paramMap);
    	if(resultList==null || resultList.size()<1){
    		return "";
    	}
    	return resultList.get(0).get("VALUE2")+"";
    }
}
