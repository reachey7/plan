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
 * @since 2019-09-03
 */
public class SysDorm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("ID")
    private String id;

    /**
     * 房间名称
     */
    @TableField("DORM_CODE")
    private String dormCode;

    /**
     * 房间名称
     */
    @TableField("DORM_NAME")
    private String dormName;

    /**
     * 楼层
     */
    @TableField("FLOOR")
    private Integer floor;

    /**
     * 房间规格ID
     */
    @TableField("SIZE_ID")
    private String sizeId;

    /**
     * 已使用床位数量
     */
    @TableField("USED_QUANTITY")
    private Integer usedQuantity;

    /**
     * 面积平方米
     */
    @TableField("SQM")
    private Double sqm;

    /**
     * 房间类型ID
     */
    @TableField("ROOM_TYPE_ID")
    private String roomTypeId;

    /**
     * 是否学生使用：0否、1是
     */
    @TableField("IS_STUDENT")
    private Integer isStudent;

    /**
     * 宿舍状态：1空闲、2已入住、3.住满、4.计划中
     */
    @TableField("DORM_STATUS")
    private Integer dormStatus;

    /**
     * 导入系统时间
     */
    @TableField("REGISTER_TIME")
    private LocalDateTime registerTime;

    /**
     * 描述
     */
    @TableField("REMARK")
    private String remark;

    @TableField("MAN_WOMEN")
    private String manWomen;

    /**
     * 已入住人数
     */
    @TableField("YZ_NUMBER")
    private String yzNumber;

    @TableField("IF_FULL")
    private String ifFull;

    /**
     * 上级实体ID
     */
    @TableField("CAMPUS_ID")
    private String campusId;

    @TableField("CAMPUS_NAME")
    private String campusName;

    /**
     * 上级实体ID
     */
    @TableField("AREA_ID")
    private String areaId;

    @TableField("AREA_NAME")
    private String areaName;

    @TableField("BUILDING_ID")
    private String buildingId;

    @TableField("BUILDING_NAME")
    private String buildingName;

    @TableField("STATE")
    private Integer state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDormCode() {
        return dormCode;
    }

    public void setDormCode(String dormCode) {
        this.dormCode = dormCode;
    }

    public String getDormName() {
        return dormName;
    }

    public void setDormName(String dormName) {
        this.dormName = dormName;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Integer usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public Double getSqm() {
        return sqm;
    }

    public void setSqm(Double sqm) {
        this.sqm = sqm;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Integer isStudent) {
        this.isStudent = isStudent;
    }

    public Integer getDormStatus() {
        return dormStatus;
    }

    public void setDormStatus(Integer dormStatus) {
        this.dormStatus = dormStatus;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getManWomen() {
        return manWomen;
    }

    public void setManWomen(String manWomen) {
        this.manWomen = manWomen;
    }

    public String getYzNumber() {
        return yzNumber;
    }

    public void setYzNumber(String yzNumber) {
        this.yzNumber = yzNumber;
    }

    public String getIfFull() {
        return ifFull;
    }

    public void setIfFull(String ifFull) {
        this.ifFull = ifFull;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SysDorm{" +
        "id=" + id +
        ", dormCode=" + dormCode +
        ", dormName=" + dormName +
        ", floor=" + floor +
        ", sizeId=" + sizeId +
        ", usedQuantity=" + usedQuantity +
        ", sqm=" + sqm +
        ", roomTypeId=" + roomTypeId +
        ", isStudent=" + isStudent +
        ", dormStatus=" + dormStatus +
        ", registerTime=" + registerTime +
        ", remark=" + remark +
        ", manWomen=" + manWomen +
        ", yzNumber=" + yzNumber +
        ", ifFull=" + ifFull +
        ", campusId=" + campusId +
        ", campusName=" + campusName +
        ", areaId=" + areaId +
        ", areaName=" + areaName +
        ", buildingId=" + buildingId +
        ", buildingName=" + buildingName +
        ", state=" + state +
        "}";
    }
}
