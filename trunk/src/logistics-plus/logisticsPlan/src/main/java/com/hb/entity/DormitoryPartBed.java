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
public class DormitoryPartBed implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    @TableId("ID")
    private String id;

    /**
     * 床位ID
     */
    @TableField("BED_ID")
    private String bedId;

    /**
     * 划分ID
     */
    @TableField("PART_ID")
    private String partId;

    /**
     * 分配时间
     */
    @TableField("CREATE_DATE")
    private String createDate;

    /**
     * 操作人
     */
    @TableField("Operator_id")
    private String operatorId;

    /**
     * 状态0失效1生效
     */
    @TableField("STATE")
    private String state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DormitoryPartBed{" +
        "id=" + id +
        ", bedId=" + bedId +
        ", partId=" + partId +
        ", createDate=" + createDate +
        ", operatorId=" + operatorId +
        ", state=" + state +
        "}";
    }
}
