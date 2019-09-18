package com.hb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lirc
 * @since 2019-09-18
 */
public class DormitoryDormStuChange implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    @TableId("ID")
    private String id;

    /**
     * 学生ID
     */
    @TableField("STUDENT_ID")
    private String studentId;

    /**
     * 调换前床位ID
     */
    @TableField("BEFORE_BED_ID")
    private String beforeBedId;

    /**
     * 调换后床位ID
     */
    @TableField("AFTER_BED_ID")
    private String afterBedId;

    @TableField("AFTER_DORM_ID")
    private String afterDormId;

    @TableField("BEFORE_DORM_ID")
    private String beforeDormId;

    /**
     * 操作时间
     */
    @TableField("CREATE_DATE")
    private String createDate;

    /**
     * 操作人
     */
    @TableField("OPERATOR_PERSON_ID")
    private String operatorPersonId;

    @TableField("OPERATOR_PERSON_NAME")
    private String operatorPersonName;

    /**
     * 操作类型0调寝1
     */
    @TableField("TYPE")
    private String type;

    /**
     * 状态0待审批1通过2驳回3作废4不通过
     */
    @TableField("STATE")
    private String state;

    /**
     * 变动原因编码，码值在参数表中
     */
    @TableField("UPDATE_REASON_CODE")
    private String updateReasonCode;

    @TableField("UPDATE_REASON_NAME")
    private String updateReasonName;


    /**
     * 审批不通过、驳回、作废原因
     */
    @TableField("APPROVAL_REASON")
    private String approvalReason;

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

	/**
     * 备注
     */
    @TableField("REMARK")
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getBeforeBedId() {
        return beforeBedId;
    }

    public void setBeforeBedId(String beforeBedId) {
        this.beforeBedId = beforeBedId;
    }

    public String getAfterBedId() {
        return afterBedId;
    }

    public void setAfterBedId(String afterBedId) {
        this.afterBedId = afterBedId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUpdateReasonCode() {
        return updateReasonCode;
    }

    public void setUpdateReasonCode(String updateReasonCode) {
        this.updateReasonCode = updateReasonCode;
    }


    public String getApprovalReason() {
        return approvalReason;
    }

    public void setApprovalReason(String approvalReason) {
        this.approvalReason = approvalReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	/**
	 * @return the updateReasonName
	 */
	public String getUpdateReasonName() {
		return updateReasonName;
	}

	/**
	 * @param updateReasonName the updateReasonName to set
	 */
	public void setUpdateReasonName(String updateReasonName) {
		this.updateReasonName = updateReasonName;
	}

	/**
	 * @return the afterDormId
	 */
	public String getAfterDormId() {
		return afterDormId;
	}

	/**
	 * @param afterDormId the afterDormId to set
	 */
	public void setAfterDormId(String afterDormId) {
		this.afterDormId = afterDormId;
	}

	/**
	 * @return the beforeDormId
	 */
	public String getBeforeDormId() {
		return beforeDormId;
	}

	/**
	 * @param beforeDormId the beforeDormId to set
	 */
	public void setBeforeDormId(String beforeDormId) {
		this.beforeDormId = beforeDormId;
	}

}
