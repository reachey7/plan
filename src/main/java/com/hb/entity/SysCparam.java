package com.hb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author lirc
 * @since 2019-08-05
 */
public class SysCparam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("KEY_CODE")
    private String keyCode;

    @TableField("VALUE1")
    private String value1;

    @TableField("VALUE2")
    private String value2;

    @TableField("VRESULT")
    private String vresult;

    @TableField("CREATE_PERSON")
    private String createPerson;

    @TableField("CREATE_DATE")
    private Date createDate;

    @TableField("TYPE_CODE")
    private String typeCode;

    @TableField("REMARK")
    private String remark;

    @TableField("INDEX_CODE")
    private Integer indexCode;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getVresult() {
        return vresult;
    }

    public void setVresult(String vresult) {
        this.vresult = vresult;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(Integer indexCode) {
        this.indexCode = indexCode;
    }

    @Override
    public String toString() {
        return "SysCparam{" +
        "id=" + id +
        ", keyCode=" + keyCode +
        ", value1=" + value1 +
        ", value2=" + value2 +
        ", vresult=" + vresult +
        ", createPerson=" + createPerson +
        ", createDate=" + createDate +
        ", typeCode=" + typeCode +
        ", remark=" + remark +
        ", indexCode=" + indexCode +
        "}";
    }
}
