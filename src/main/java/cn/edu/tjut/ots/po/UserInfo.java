package cn.edu.tjut.ots.po;

import java.util.Date;

/**
 * Created by KINGBOOK on 2017/4/9.
 */
public class UserInfo {
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //职业
    private String job;
    //性别
    private String sex;
    //电话
    private String phone;
    //QQ
    private String qq;
    //Email
    private String email;
    //出生日期
    private Date birthday;
    //个性签名
    private String profile;
    //头像ID
    private String avaterId;
    //时间字符串，数据库不存在对应字段
    private String timeStr;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAvaterId() {
        return avaterId;
    }

    public void setAvaterId(String avaterId) {
        this.avaterId = avaterId;
    }
}
