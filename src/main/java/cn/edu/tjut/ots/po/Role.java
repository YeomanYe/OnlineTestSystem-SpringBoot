package cn.edu.tjut.ots.po;

import java.util.Date;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
public class Role {
    //ID
    private String uuid;
    //角色名
    private String name;
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
}
