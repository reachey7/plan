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
 * @since 2019-08-12
 */
public class DormitoryBedStu implements Serializable {

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
     * 床ID
     */
    @TableField("BED_ID")
    private String bedId;

    /**
     * 操作人
     */
    @TableField("OPERATOR_ID")
    private String operatorId;

    /**
     * 操作时间
     */
    @TableField("OPERAT_DATE")
    private LocalDateTime operatDate;

    /**
     * 状态0失效1有效
     */
    @TableField("STATE")
    private String state;

    /**
     * 开始时间
     */
    @TableField("BEGIN_DATE")
    private LocalDateTime beginDate;

    /**
     * 结束时间
     */
    @TableField("END_DATE")
    private LocalDateTime endDate;

    /**
     * 办理渠道0开学季1个别入住
     */
    @TableField("IN_MODE")
    private String inMode;


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

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public LocalDateTime getOperatDate() {
        return operatDate;
    }

    public void setOperatDate(LocalDateTime operatDate) {
        this.operatDate = operatDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getInMode() {
        return inMode;
    }

    public void setInMode(String inMode) {
        this.inMode = inMode;
    }

    @Override
    public String toString() {
        return "DormitoryBedStu{" +
        "id=" + id +
        ", studentId=" + studentId +
        ", bedId=" + bedId +
        ", operatorId=" + operatorId +
        ", operatDate=" + operatDate +
        ", state=" + state +
        ", beginDate=" + beginDate +
        ", endDate=" + endDate +
        ", inMode=" + inMode +
        "}";
    }
}
