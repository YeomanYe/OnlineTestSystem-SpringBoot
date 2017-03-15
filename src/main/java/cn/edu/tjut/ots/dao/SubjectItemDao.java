package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.utils.BooleanTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/2.
 */
@Mapper
public interface SubjectItemDao {
    //根据ID查询试题项
    @Select("select * from subject_item where subjectId = #{param}")
    public List<SubjectItem> querySubjectItem(String uuid);
    //插入试题选项
    @Insert("insert into subject_item values(#{uuid},#{name},#{subjectId},#{isAnswer,javaType=Boolean,jdbcType=CHAR})")
    public void insertSubjectItem(SubjectItem subjectItem);
    //根据试题选项ID更新试题
    @Update("update subject_item set name=#{name},subjectId=#{subjectId},isAnswer=#{isAnswer,javaType=Boolean,jdbcType=CHAR} where uuid = #{uuid}")
    public void updateSubjectItem(SubjectItem subjectItem);
    //根据试题ID删除试题选项
    @Delete("delete subject_item where subjectId = #{param}")
    public void deleteSubjectItemBySubjectId(String subjectId);
    //批量插入试题
    public void insertBatchItem(List<SubjectItem> items);
}
