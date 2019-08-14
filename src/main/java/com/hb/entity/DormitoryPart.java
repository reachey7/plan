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
 * @since 2019-08-09
 */
public class DormitoryPart implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * ID
     */
         @TableId("ID")
    private String id;

        /**
     * 划分编码
     */
         @TableField("PART_CODE")
    private String partCode;

        /**
     * 计划ID
     */
         @TableField("PLAN_ID")
    private String planId;

        /**
     * 划分名称
     */
         @TableField("PART_NAME")
    private String partName;

        /**
     * 年级
     */
         @TableField("GRADE")
    private String grade;

        /**
     * 创建日期
     */
         @TableField("CREATE_TIME")
    private String createTime;

        /**
     * 创建人
     */
         @TableField("CREATE_PERSON_ID")
    private String createPersonId;

        /**
     * 分配人ID
     */
         @TableField("CURRENT_PERSON_ID")
    private String currentPersonId;

        /**
     * 划分开始时间
     */
         @TableField("PART_BEGIN")
    private String partBegin;

        /**
     * 划分结束时间
     */
         @TableField("PART_END")
    private String partEnd;

        /**
     * 见参数表
     */
         @TableField("PART_STATUS")
    private String partStatus;

        /**
     * 分配规则
     */
         @TableField("PART_RULE")
    private String partRule;

    @TableField("CURRENT_PERSON")
    private String currentPerson;

        /**
     * 学生数量
     */
         @TableField("STUDENT_NUMBER")
    private Integer studentNumber;

        /**
     * 床位数量
     */
         @TableField("BED_NUMBER")
    private Integer bedNumber;

        /**
     * 描述
     */
         @TableField("REMARK")
    private String remark;


    /**
     * TASK_ID
     */
    @TableField("TASK_ID")
    private String taskId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCurrentPersonId() {
        return currentPersonId;
    }

    public void setCurrentPersonId(String currentPersonId) {
        this.currentPersonId = currentPersonId;
    }

    public String getPartBegin() {
        return partBegin;
    }

    public void setPartBegin(String partBegin) {
        this.partBegin = partBegin;
    }

    public String getPartEnd() {
        return partEnd;
    }

    public void setPartEnd(String partEnd) {
        this.partEnd = partEnd;
    }

    public String getPartStatus() {
        return partStatus;
    }

    public void setPartStatus(String partStatus) {
        this.partStatus = partStatus;
    }

    public String getPartRule() {
        return partRule;
    }

    public void setPartRule(String partRule) {
        this.partRule = partRule;
    }

    public String getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(String currentPerson) {
        this.currentPerson = currentPerson;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(Integer bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "DormitoryPart{" +
        "id=" + id +
        ", partCode=" + partCode +
        ", planId=" + planId +
        ", partName=" + partName +
        ", grade=" + grade +
        ", createTime=" + createTime +
        ", createPersonId=" + createPersonId +
        ", currentPersonId=" + currentPersonId +
        ", partBegin=" + partBegin +
        ", partEnd=" + partEnd +
        ", partStatus=" + partStatus +
        ", partRule=" + partRule +
        ", currentPerson=" + currentPerson +
        ", studentNumber=" + studentNumber +
        ", bedNumber=" + bedNumber +
        ", remark=" + remark +
        "}";
    }
}
