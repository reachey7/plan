package com.hb.entity;

import java.time.LocalDate;
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
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * ID
     */
         @TableId("ID")
    private String id;

        /**
     * 学号
     */
         @TableField("STUDENT_NO")
    private String studentNo;

    @TableField("NAME")
    private String name;

        /**
     * 女、男
     */
         @TableField("SEX")
    private Integer sex;

        /**
     * 民族
     */
         @TableField("NATION")
    private String nation;

        /**
     * 政治面貌：1党员、2团员、3其他
     */
         @TableField("POLTICAL_STATUS")
    private Integer polticalStatus;

        /**
     * 婚姻情况：0否、1是
     */
         @TableField("MARITAL_STATUS")
    private Integer maritalStatus;

        /**
     * 出生日期
     */
         @TableField("BIRTHDAY")
    private LocalDate birthday;

        /**
     * 籍贯
     */
         @TableField("POB")
    private String pob;

    @TableField("ID_CARD")
    private String idCard;

    @TableField("PHONE")
    private String phone;

        /**
     * 头像照片
     */
         @TableField("PHOTO_ID")
    private String photoId;

        /**
     * 学院ID,college表
     */
         @TableField("college_ID")
    private String collegeId;

        /**
     * 专业ID，marjor表
     */
         @TableField("MAJOR_ID")
    private String majorId;

    @TableField("GRADE")
    private Integer grade;

        /**
     * 见参数表,STU_TYPE
     */
         @TableField("DEGREE_TYPE")
    private String degreeType;

        /**
     * 家庭地址
     */
         @TableField("HOME_ADDRESS")
    private String homeAddress;

        /**
     * 家庭电话
     */
         @TableField("HOME_PHONE")
    private String homePhone;

        /**
     * 是否入住：1是、0否
     */
         @TableField("IS_CHECKIN")
    private Integer isCheckin;

        /**
     * 是否留学
     */
         @TableField("STUDY_ABROAD")
    private Integer studyAbroad;

        /**
     * 是否毕业：0否、1是
     */
         @TableField("IS_GRADUATION")
    private Integer isGraduation;

        /**
     * 在校状态：1在校、2离校
     */
         @TableField("SCHOOL_STATUS")
    private Integer schoolStatus;

        /**
     * 1.普通2.寝室长3.班长
     */
         @TableField("TYPE")
    private Integer type;

        /**
     * 区域ID
     */
         @TableField("AREA_ID")
    private String areaId;

    @TableField("AREA_NAME")
    private String areaName;

    @TableField("CAMPUS_NAME")
    private String campusName;

        /**
     * 校区ID
     */
         @TableField("CAMPUS_ID")
    private String campusId;

    @TableField("GRADUATE_YEAR")
    private Integer graduateYear;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getPolticalStatus() {
        return polticalStatus;
    }

    public void setPolticalStatus(Integer polticalStatus) {
        this.polticalStatus = polticalStatus;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public Integer getIsCheckin() {
        return isCheckin;
    }

    public void setIsCheckin(Integer isCheckin) {
        this.isCheckin = isCheckin;
    }

    public Integer getStudyAbroad() {
        return studyAbroad;
    }

    public void setStudyAbroad(Integer studyAbroad) {
        this.studyAbroad = studyAbroad;
    }

    public Integer getIsGraduation() {
        return isGraduation;
    }

    public void setIsGraduation(Integer isGraduation) {
        this.isGraduation = isGraduation;
    }

    public Integer getSchoolStatus() {
        return schoolStatus;
    }

    public void setSchoolStatus(Integer schoolStatus) {
        this.schoolStatus = schoolStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public Integer getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(Integer graduateYear) {
        this.graduateYear = graduateYear;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
        "id=" + id +
        ", studentNo=" + studentNo +
        ", name=" + name +
        ", sex=" + sex +
        ", nation=" + nation +
        ", polticalStatus=" + polticalStatus +
        ", maritalStatus=" + maritalStatus +
        ", birthday=" + birthday +
        ", pob=" + pob +
        ", idCard=" + idCard +
        ", phone=" + phone +
        ", photoId=" + photoId +
        ", collegeId=" + collegeId +
        ", majorId=" + majorId +
        ", grade=" + grade +
        ", degreeType=" + degreeType +
        ", homeAddress=" + homeAddress +
        ", homePhone=" + homePhone +
        ", isCheckin=" + isCheckin +
        ", studyAbroad=" + studyAbroad +
        ", isGraduation=" + isGraduation +
        ", schoolStatus=" + schoolStatus +
        ", type=" + type +
        ", areaId=" + areaId +
        ", areaName=" + areaName +
        ", campusName=" + campusName +
        ", campusId=" + campusId +
        ", graduateYear=" + graduateYear +
        "}";
    }
}
