package cn.edu.tjut.ots.po;

import java.util.Date;

/**
 * Created by KINGBOOK on 2017/3/23.
 */
public class Image {
    //ID
    private String uuid;
    //相对路径
    private String relPath;
    //绝对路径
    private String absPath;
    //上传时的名字
    private String formerName;
    //现在的名字
    private String presentName;
    //文件类型
    private String fileType;
    //文件大小
    private long fileSize;
    //创建者
    private String createBy;
    //创建时间
    private Date createWhen;
    //更新者
    private String updateBy;
    //更新时间
    private Date updateWhen;
    //更新时间的字符串表示
    private String updateWhenStr;

    public String getUpdateWhenStr() {
        return updateWhenStr;
    }

    public void setUpdateWhenStr(String updateWhenStr) {
        this.updateWhenStr = updateWhenStr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRelPath() {
        return relPath;
    }

    public void setRelPath(String relPath) {
        this.relPath = relPath;
    }

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    public String getPresentName() {
        return presentName;
    }

    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
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
        return "Image{" +
                "uuid='" + uuid + '\'' +
                ", relPath='" + relPath + '\'' +
                ", absPath='" + absPath + '\'' +
                ", formerName='" + formerName + '\'' +
                ", presentName='" + presentName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                ", createBy='" + createBy + '\'' +
                ", createWhen=" + createWhen +
                ", updateBy='" + updateBy + '\'' +
                ", updateWhen=" + updateWhen +
                '}';
    }
}
