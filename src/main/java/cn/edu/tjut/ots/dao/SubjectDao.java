package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Subject;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

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
    //根据ID删除试题
    public void deleteSubjectByIds(String[] uuids);
    //查询试题简要信息
    public List<Object> queryBriefSubject();
    //查询试题详细信息
    public Object queryDetailSubject(String uuid);
    //查询试题对应的试题类型ID
    @Select("select subjectType from subject where uuid = #{param}")
    public String querySubjectType(String uuid);
    //查询日期与类型为了统计
    @Select("SELECT COUNT(*) AS CONT,b.name AS subjectType " +
            "FROM subject s JOIN basedata b ON s.subjecttype = b.uuid " +
            "GROUP BY b.name")
    public List<Subject> queryDateAndType();
}
