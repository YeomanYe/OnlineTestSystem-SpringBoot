package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.utils.BooleanTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
@Mapper
public interface SubjectDao {
    //查询所有的试题
    @Select("select * from subject")
    public List<Subject> querySubject();
    //插入试题
    @Insert("insert into subject values(" +
            "#{uuid},#{subjectType},#{subjectName},#{subjectScore}," +
            "#{subjectParse},#{createBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'),#{updateBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'))")
    public void insertSubject(Subject subject);
    //根据ID更新试题
    @Update("update subject set subjectType=#{subjectType},subjectName=#{subjectName}," +
            "subjectScore=#{subjectScore},subjectParse=#{subjectParse}," +
            "updateBy=#{updateBy},updateWhen=to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd') where uuid=#{uuid}")
    public void updateSubject(Subject subject);
    //根据ID查询试题
    @Select("select * from subject where uuid = #{param}")
    public Subject querySubjectById(String uuid);
}
