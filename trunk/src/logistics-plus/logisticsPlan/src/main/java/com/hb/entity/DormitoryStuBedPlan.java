package com.hb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lirc
 * @since 2019-08-13
 */
public class DormitoryStuBedPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("STU_ID")
    private String stuId;

    @TableField("BED_ID")
    private String bedId;

    @TableField("PLAN_ID")
    private String planId;

    @TableField("PART_ID")
    private String partId;

    @TableField("OPERATOR_PERSON_ID")
    private String operatorPersonId;

    @TableField("OPERATOR_PERSON_NAME")
    private String operatorPersonName;

    
    @TableField("CREATE_DATE")
    private String createDate;

    @TableField("STATE")
    private String state;

    /**
     * 来源0老师划分1学生自选
     */
    @TableField("TYPE")
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    

    /**
	 * @return the operatorPersonId
	 */
	public String getOperatorPersonId() {
		return operatorPersonId;
	}

	/**
	 * @param operatorPersonId the operatorPersonId to set
	 */
	public void setOperatorPersonId(String operatorPersonId) {
		this.operatorPersonId = operatorPersonId;
	}

	/**
	 * @return the operatorPersonName
	 */
	public String getOperatorPersonName() {
		return operatorPersonName;
	}

	/**
	 * @param operatorPersonName the operatorPersonName to set
	 */
	public void setOperatorPersonName(String operatorPersonName) {
		this.operatorPersonName = operatorPersonName;
	}

	public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   
}
