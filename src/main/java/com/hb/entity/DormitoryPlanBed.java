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
 * @since 2019-08-09
 */
public class DormitoryPlanBed implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

        /**
     * 计划ID
     */
         @TableField("PLAN_ID")
    private String planId;

        /**
     * 创建时间
     */
         @TableField("CREATE_DATE")
    private LocalDateTime createDate;

        /**
     * 状态0失效1生效
     */
         @TableField("STATE")
    private String state;

        /**
     * 操作人
     */
         @TableField("OPERATOR_ID")
    private String operatorId;

        /**
     * 床ID
     */
         @TableField("BED_ID")
    private String bedId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    @Override
    public String toString() {
        return "DormitoryPlanBed{" +
        "id=" + id +
        ", planId=" + planId +
        ", createDate=" + createDate +
        ", state=" + state +
        ", operatorId=" + operatorId +
        ", bedId=" + bedId +
        "}";
    }
}
