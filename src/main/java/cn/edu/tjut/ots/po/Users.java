package cn.edu.tjut.ots.po;

import java.security.MessageDigest;
import java.util.Date;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
public class Users {
    //用户名
    private String userName;
    //密码
    private String pass;
    //创建者
    private String createBy;
    //创建时间
    private Date createWhen;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateWhen;
    //更新时间字符串表示
    private String updateWhenStr;

    public String getUpdateWhenStr() {
        return updateWhenStr;
    }

    public void setUpdateWhenStr(String updateWhenStr) {
        this.updateWhenStr = updateWhenStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
}
