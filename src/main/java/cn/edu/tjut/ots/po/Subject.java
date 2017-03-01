package cn.edu.tjut.ots.po;

import org.apache.ibatis.type.Alias;

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
    private String subjectDescription;
    //试题答案
    private String subjectAnswer;
    //试题分数
    private int subjectScore;
    //试题选项
    private String subjectOptionA;
    private String subjectOptionB;
    private String subjectOptionC;
    private String subjectOptionD;
    //试题解析
    private String subjectParse;

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

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public String getSubjectAnswer() {
        return subjectAnswer;
    }

    public void setSubjectAnswer(String subjectAnswer) {
        this.subjectAnswer = subjectAnswer;
    }

    public int getSubjectScore() {
        return subjectScore;
    }

    public void setSubjectScore(int subjectScore) {
        this.subjectScore = subjectScore;
    }

    public String getSubjectOptionA() {
        return subjectOptionA;
    }

    public void setSubjectOptionA(String subjectOptionA) {
        this.subjectOptionA = subjectOptionA;
    }

    public String getSubjectOptionB() {
        return subjectOptionB;
    }

    public void setSubjectOptionB(String subjectOptionB) {
        this.subjectOptionB = subjectOptionB;
    }

    public String getSubjectOptionC() {
        return subjectOptionC;
    }

    public void setSubjectOptionC(String subjectOptionC) {
        this.subjectOptionC = subjectOptionC;
    }

    public String getSubjectOptionD() {
        return subjectOptionD;
    }

    public void setSubjectOptionD(String subjectOptionD) {
        this.subjectOptionD = subjectOptionD;
    }

    public String getSubjectParse() {
        return subjectParse;
    }

    public void setSubjectParse(String subjectParse) {
        this.subjectParse = subjectParse;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "uuid='" + uuid + '\'' +
                ", subjectType='" + subjectType + '\'' +
                ", subjectDescription='" + subjectDescription + '\'' +
                ", subjectAnswer='" + subjectAnswer + '\'' +
                ", subjectScore='" + subjectScore + '\'' +
                ", subjectOptionA='" + subjectOptionA + '\'' +
                ", subjectOptionB='" + subjectOptionB + '\'' +
                ", subjectOptionC='" + subjectOptionC + '\'' +
                ", subjectOptionD='" + subjectOptionD + '\'' +
                ", subjectParse='" + subjectParse + '\'' +
                '}';
    }
}
