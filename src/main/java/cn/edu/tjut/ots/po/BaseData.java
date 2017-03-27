package cn.edu.tjut.ots.po;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
public class BaseData {
    //id
    private String uuid;
    //数据类型
    private String dataType;
    //名称
    private String name;
    //数据类型名称表示
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
