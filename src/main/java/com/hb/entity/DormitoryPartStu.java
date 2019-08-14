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
public class DormitoryPartStu implements Serializable {

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
     * 划分ID
     */
    @TableField("PART_ID")
    private String partId;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
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
        return "DormitoryPartStu{" +
        "id=" + id +
        ", studentId=" + studentId +
        ", partId=" + partId +
        ", createDate=" + createDate +
        ", operatorId=" + operatorId +
        ", state=" + state +
        "}";
    }
}
