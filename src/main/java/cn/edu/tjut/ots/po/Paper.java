package cn.edu.tjut.ots.po;

import java.util.Date;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
public class Paper {
    //ID
    private String uuid;
    //试卷名
    private String paperName;
    //试卷描述
    private String paperDesc;
    //试卷类型ID
    private String paperType;
    //试卷分数
    private int paperScore;
    //答题时间
    private int ansTime;
    //试卷题量
    private int subjectCnt;
    //创建者
    private String createBy;
    //创建时间
    private Date createWhen;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateWhen;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperDesc() {
        return paperDesc;
    }

    public void setPaperDesc(String paperDesc) {
        this.paperDesc = paperDesc;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public int getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(int paperScore) {
        this.paperScore = paperScore;
    }

    public int getAnsTime() {
        return ansTime;
    }

    public void setAnsTime(int ansTime) {
        this.ansTime = ansTime;
    }

    public int getSubjectCnt() {
        return subjectCnt;
    }

    public void setSubjectCnt(int subjectCnt) {
        this.subjectCnt = subjectCnt;
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
        return "Paper{" +
                "uuid='" + uuid + '\'' +
                ", paperName='" + paperName + '\'' +
                ", paperDesc='" + paperDesc + '\'' +
                ", paperType='" + paperType + '\'' +
                ", paperScore=" + paperScore +
                ", ansTime=" + ansTime +
                ", subjectCnt=" + subjectCnt +
                ", createBy='" + createBy + '\'' +
                ", createWhen=" + createWhen +
                ", updateBy='" + updateBy + '\'' +
                ", updateWhen=" + updateWhen +
                '}';
    }

    public Paper(String uuid, String paperName, String paperDesc, String paperType, int paperScore, int ansTime, int subjectCnt, String createBy, Date createWhen, String updateBy, Date updateWhen) {
        this.uuid = uuid;
        this.paperName = paperName;
        this.paperDesc = paperDesc;
        this.paperType = paperType;
        this.paperScore = paperScore;
        this.ansTime = ansTime;
        this.subjectCnt = subjectCnt;
        this.createBy = createBy;
        this.createWhen = createWhen;
        this.updateBy = updateBy;
        this.updateWhen = updateWhen;
    }

    public Paper() {
    }
}
