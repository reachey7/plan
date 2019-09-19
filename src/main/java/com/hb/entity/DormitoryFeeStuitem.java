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
public class DormitoryFeeStuitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * FEE_STU表中的ID
     */
    @TableField("FEE_STU_ID")
    private String feeStuId;

    /**
     * 操作时间
     */
    @TableField("CREATE_DATE")
    private String createDate;

    /**
     * 操作人ID
     */
    @TableField("CREATE_OPERATE_PERSON_ID")
    private String createOperatePersonId;

    /**
     * 操作人姓名
     */
    @TableField("CREATE_OPERATE_PERSON_NAME")
    private String createOperatePersonName;

    /**
     * 状态0失效1有效
     */
    @TableField("STATE")
    private String state;

    /**
     * 描述
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 本次缴费金额
     */
    @TableField("ITEM_PAY_FEE")
    private String itemPayFee;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeeStuId() {
        return feeStuId;
    }

    public void setFeeStuId(String feeStuId) {
        this.feeStuId = feeStuId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateOperatePersonId() {
        return createOperatePersonId;
    }

    public void setCreateOperatePersonId(String createOperatePersonId) {
        this.createOperatePersonId = createOperatePersonId;
    }

    public String getCreateOperatePersonName() {
        return createOperatePersonName;
    }

    public void setCreateOperatePersonName(String createOperatePersonName) {
        this.createOperatePersonName = createOperatePersonName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getItemPayFee() {
        return itemPayFee;
    }

    public void setItemPayFee(String itemPayFee) {
        this.itemPayFee = itemPayFee;
    }

    @Override
    public String toString() {
        return "DormitoryFeeStuitem{" +
        "id=" + id +
        ", feeStuId=" + feeStuId +
        ", createDate=" + createDate +
        ", createOperatePersonId=" + createOperatePersonId +
        ", createOperatePersonName=" + createOperatePersonName +
        ", state=" + state +
        ", remark=" + remark +
        ", itemPayFee=" + itemPayFee +
        "}";
    }
}
