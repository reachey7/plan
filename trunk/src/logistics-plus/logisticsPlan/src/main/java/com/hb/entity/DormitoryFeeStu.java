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
 * @since 2019-09-18
 */
public class DormitoryFeeStu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 学生ID
     */
    @TableField("STUDENT_ID")
    private String studentId;

    /**
     * 年份
     */
    @TableField("YEAR")
    private String year;

    /**
     * 应缴费用
     */
    @TableField("PAY_FEE_SHOULD")
    private String payFeeShould;

    /**
     * 实际支付费用
     */
    @TableField("PAY_FEE_REAL")
    private String payFeeReal;

    /**
     * 学生姓名
     */
    @TableField("STUDENT_NAME")
    private String studentName;

    /**
     * 学生编号
     */
    @TableField("STUDENT_NUM")
    private String studentNum;

    /**
     * 是否缴清
     */
    @TableField("IF_OVER")
    private String ifOver;

    
    /**
	 * @return the ifOver
	 */
	public String getIfOver() {
		return ifOver;
	}

	/**
	 * @param ifOver the ifOver to set
	 */
	public void setIfOver(String ifOver) {
		this.ifOver = ifOver;
	}

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPayFeeShould() {
        return payFeeShould;
    }

    public void setPayFeeShould(String payFeeShould) {
        this.payFeeShould = payFeeShould;
    }

    public String getPayFeeReal() {
        return payFeeReal;
    }

    public void setPayFeeReal(String payFeeReal) {
        this.payFeeReal = payFeeReal;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    @Override
    public String toString() {
        return "DormitoryFeeStu{" +
        "id=" + id +
        ", studentId=" + studentId +
        ", year=" + year +
        ", payFeeShould=" + payFeeShould +
        ", payFeeReal=" + payFeeReal +
        ", studentName=" + studentName +
        ", studentNum=" + studentNum +
        "}";
    }
}
