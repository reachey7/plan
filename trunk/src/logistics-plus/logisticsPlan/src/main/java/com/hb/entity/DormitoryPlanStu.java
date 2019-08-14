package com.hb.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 学生与计划关系表
 * </p>
 *
 * @author lirc
 * @since 2019-08-09
 */
public class DormitoryPlanStu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ID")
    private String id;

    @TableField("STUDENT_ID")
    private String studentId;

    @TableField("PLAN_ID")
    private String planId;

    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @TableField("STATE")
    private String state;

    @TableField("OPERATOR_ID")
    private String operatorId;


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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "DormitoryPlanStu{" +
        "id=" + id +
        ", studentId=" + studentId +
        ", planId=" + planId +
        ", createDate=" + createDate +
        ", state=" + state +
        ", operatorId=" + operatorId +
        "}";
    }
}
