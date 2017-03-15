package cn.edu.tjut.ots.po;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Subject实体类
 * Created by KINGBOOK on 2017/3/1.
 */
public class Subject {
    //id
    private String uuid;
    //试题类型
    private String subjectType;
    //试题描述
    private String subjectName;
    //试题分数
    private int subjectScore;
    //试题解析
    private String subjectParse;
    //创建者
    private String createBy;
    //创建时间
    private Date createWhen;
    //最后更新人
    private String updateBy;
    //更新时间
    private Date updateWhen;
    //图片路径地址
    private String imgPath;
    //更新时间字符串表示
    private String updateWhenStr;
    //数量,辅助变量,用于统计图
    private int cont;
    //名称，辅助变量，用于统计图
    private String name;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public String getUpdateWhenStr() {
        return updateWhenStr;
    }

    public void setUpdateWhenStr(String updateWhenStr) {
        this.updateWhenStr = updateWhenStr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectScore() {
        return subjectScore;
    }

    public void setSubjectScore(int subjectScore) {
        this.subjectScore = subjectScore;
    }

    public String getSubjectParse() {
        return subjectParse;
    }

    public void setSubjectParse(String subjectParse) {
        this.subjectParse = subjectParse;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateWhen() {
        return createWhen;
    }

    public void setCreateWhen(Date createWhen) {
        this.createWhen = createWhen;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateWhen() {
        return updateWhen;
    }

    public void setUpdateWhen(Date updateWhen) {
        this.updateWhen = updateWhen;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "uuid='" + uuid + '\'' +
                ", subjectType='" + subjectType + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", subjectScore=" + subjectScore +
                ", subjectParse='" + subjectParse + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createWhen=" + createWhen +
                ", updateBy='" + updateBy + '\'' +
                ", updateWhen=" + updateWhen +
                '}';
    }

    public Subject(String uuid, String subjectType, String subjectName, int subjectScore, String subjectParse) {
        this.uuid = uuid;
        this.subjectType = subjectType;
        this.subjectName = subjectName;
        this.subjectScore = subjectScore;
        this.subjectParse = subjectParse;
    }

    public Subject() {
    }
}
