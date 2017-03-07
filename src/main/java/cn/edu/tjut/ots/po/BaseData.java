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

    @Override
    public String toString() {
        return "BaseData{" +
                "uuid='" + uuid + '\'' +
                ", dataType='" + dataType + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
