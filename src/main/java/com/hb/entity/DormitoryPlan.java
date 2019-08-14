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
 * @since 2019-08-06
 */
public class DormitoryPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("ID")
    private String id;

    /**
     * 计划编码
     */
    @TableField("PLAN_CODE")
    private String planCode;

    /**
     * 计划名称
     */
    @TableField("PLAN_NAME")
    private String planName;

    /**
     * 已划分人数
     */
    @TableField("PARTITION_NUMBER")
    private Integer partitionNumber;

    /**
     * 已分配人数
     */
    @TableField("DISTRIBUTE_NUMBER")
    private Integer distributeNumber;

    /**
     * 创建日期
     */
    @TableField("CREATE_TIME")
    private String createTime;
    /**
     * 更新日期
     */
    @TableField("UPDATE_TIME")
    private String updateTime;
    /**
     * 创建人
     */
    @TableField("CREATE_PERSON_ID")
    private String createPersonId;

    @TableField("CREATE_PERSON_NAME")
    private String createPersonName;

    /**
     * 描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 1.已实施0.未实施
     */
    @TableField("PLAN_STATUS")
    private String planStatus;

    /**
     * 上级实体ID
     */
    @TableField("SUBJECT_ID")
    private String subjectId;

    @TableField("PLAN_STU_NUMBER")
    private Integer planStuNumber;

    /**
     * 已划分人数
     */
    @TableField("HF_OVER_NUMBER")
    private Integer hfOverNumber;

    /**
     * 已分配人数
     */
    @TableField("FP_OVER_NUMBER")
    private Integer fpOverNumber;

    /**
     * 当前处理人ID
     */
    @TableField("CURRENT_PERSON_ID")
    private String currentPersonId;

    /**
     * 当前处理人姓名
     */
    @TableField("CURRENT_PERSON_NAME")
    private String currentPersonName;

    /**
     * 学生类型
     */
    @TableField("STU_TYPE")
    private String stuType;

    /**
     * 工作流ID
     */
    @TableField("ACTIVITY_ID")
    private String activityId;


    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private String current;

    @TableField(exist = false)
    private String size;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getPartitionNumber() {
        return partitionNumber;
    }

    public void setPartitionNumber(Integer partitionNumber) {
        this.partitionNumber = partitionNumber;
    }

    public Integer getDistributeNumber() {
        return distributeNumber;
    }

    public void setDistributeNumber(Integer distributeNumber) {
        this.distributeNumber = distributeNumber;
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

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getPlanStuNumber() {
        return planStuNumber;
    }

    public void setPlanStuNumber(Integer planStuNumber) {
        this.planStuNumber = planStuNumber;
    }

    public Integer getHfOverNumber() {
        return hfOverNumber;
    }

    public void setHfOverNumber(Integer hfOverNumber) {
        this.hfOverNumber = hfOverNumber;
    }

    public Integer getFpOverNumber() {
        return fpOverNumber;
    }

    public void setFpOverNumber(Integer fpOverNumber) {
        this.fpOverNumber = fpOverNumber;
    }

    public String getCurrentPersonId() {
        return currentPersonId;
    }

    public void setCurrentPersonId(String currentPersonId) {
        this.currentPersonId = currentPersonId;
    }

    public String getCurrentPersonName() {
        return currentPersonName;
    }

    public void setCurrentPersonName(String currentPersonName) {
        this.currentPersonName = currentPersonName;
    }

    public String getStuType() {
        return stuType;
    }

    public void setStuType(String stuType) {
        this.stuType = stuType;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "DormitoryPlan{" +
                "id=" + id +
                ", planCode=" + planCode +
                ", planName=" + planName +
                ", partitionNumber=" + partitionNumber +
                ", distributeNumber=" + distributeNumber +
                ", createTime=" + createTime +
                ", createPersonId=" + createPersonId +
                ", createPersonName=" + createPersonName +
                ", description=" + description +
                ", planStatus=" + planStatus +
                ", subjectId=" + subjectId +
                ", planStuNumber=" + planStuNumber +
                ", hfOverNumber=" + hfOverNumber +
                ", fpOverNumber=" + fpOverNumber +
                ", currentPersonId=" + currentPersonId +
                ", currentPersonName=" + currentPersonName +
                ", stuType=" + stuType +
                "}";
    }
}
