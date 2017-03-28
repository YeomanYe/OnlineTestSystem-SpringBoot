package cn.edu.tjut.ots.po;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
public class Resources {
    //ID
    private String uuid;
    //权限名称
    private String name;
    //资源代码
    private String resourceCode;
    //父资源ID
    private String parentId;

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

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
