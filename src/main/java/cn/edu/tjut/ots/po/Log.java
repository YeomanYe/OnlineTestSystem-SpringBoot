package cn.edu.tjut.ots.po;

import java.util.Date;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
public class Log {
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
