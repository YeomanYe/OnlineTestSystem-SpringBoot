package cn.edu.tjut.ots.po;

import java.util.Date;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
public class UserLog {
    //ID
    private String uuid;
    //用户名
    private String userName;
    //IP地址
    private String ip;
    //操作
    private String operation;
    //时间
    private Date time;
    //以字符串形式表示的时间戳
    private String timeStr;
    //用于统计的辅助字段,名字
    private String name;
    //用于统计的辅助字段,数量
    private String cont;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
