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
 * @since 2019-09-03
 */
public class SysBed implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("ID")
    private String id;

    /**
     * 宿舍ID
     */
    @TableField("DORM_ID")
    private String dormId;

    /**
     * 床位编码
     */
    @TableField("BED_CODE")
    private Integer bedCode;

    /**
     * 床位序号
     */
    @TableField("BED_NUMBER")
    private String bedNumber;

    /**
     * 床位状态：1空闲、2已入住、3计划中、4申请中
     */
    @TableField("BED_STATUS")
    private Integer bedStatus;

    /**
     * 描述
     */
    @TableField("REMARK")
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDormId() {
        return dormId;
    }

    public void setDormId(String dormId) {
        this.dormId = dormId;
    }

    public Integer getBedCode() {
        return bedCode;
    }

    public void setBedCode(Integer bedCode) {
        this.bedCode = bedCode;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public Integer getBedStatus() {
        return bedStatus;
    }

    public void setBedStatus(Integer bedStatus) {
        this.bedStatus = bedStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysBed{" +
        "id=" + id +
        ", dormId=" + dormId +
        ", bedCode=" + bedCode +
        ", bedNumber=" + bedNumber +
        ", bedStatus=" + bedStatus +
        ", remark=" + remark +
        "}";
    }
}
