package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Image;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/23.
 */
@Mapper
public interface ImageDao {
    //通过ID查找image信息
    @Select("SELECT to_char(i.updateWhen,'yyyy-mm-dd') as updateWhenStr,i.uuid,i.absPath," +
            "i.relPath,i.formerName,i.presentName,i.fileType,i.fileSize,i.createBy," +
            "to_date(to_char(i.createWhen,'yyyy-mm-dd'),'yyyy-mm-dd') as createWhen,i.updateBy, " +
            "to_date(to_char(i.createWhen,'yyyy-mm-dd'),'yyyy-mm-dd') as updateWhen " +
            " FROM image i WHERE uuid = #{param}")
    public List<Image> queryImageById(String imageId);
    //保存image
    @Insert("INSERT INTO image (uuid,absPath,formerName,presentName," +
            "fileType,fileSize,createBy,createWhen,updateBy,updateWhen,relPath) " +
            " VALUES(#{uuid},#{absPath},#{formerName},#{presentName},#{fileType}," +
            "#{fileSize},#{createBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd')," +
            "#{updateBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'),#{relPath})")
    public void addImage(Image image);
    //删除图片通过ID
    @Delete("DELETE FROM image WHERE uuid = #{param}")
    public void deleteImage(String imageId);
}
