package cn.edu.tjut.ots.po;

/**
 * Created by KINGBOOK on 2017/3/2.
 */
public class SubjectItem {
    //id
    private String uuid;
    //选项描述
    private String name;
    //试题编号
    private String subjectId;
    //是否为答案
    private boolean isAnswer;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    @Override
    public String toString() {
        return "SubjectItem{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", isAnswer=" + isAnswer +
                '}';
    }
}
