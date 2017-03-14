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
    @Select("select s.uuid as uuid,s.subjectName as subjectName,s.subjectScore as subjectScore," +
            "to_char(s.updateWhen,'yyyy-mm-dd') as updateWhenStr,b.name as subjectType " +
            "FROM subject s right join basedata b on s.subjecttype = b.uuid")
    public List<Subject> queryBriefSubject();
    //查询试题详细信息
    @Select("select s.uuid as uuid,s.subjectName as subjectName,s.subjectScore as subjectScore," +
            "s.subjectType as subjectType,s.subjectParse as subjectParse" +
            "FROM subject s where s.uuid = #{param}")
    public Subject queryDetailSubject(String uuid);
    //查询试题对应的试题类型ID
    @Select("select subjectType from subject where uuid = #{param}")
    public String querySubjectType(String uuid);
    //查询类型为了统计
    @Select("SELECT COUNT(*) AS CONT,b.name AS name " +
            "FROM subject s JOIN basedata b ON s.subjecttype = b.uuid " +
            "GROUP BY b.name")
    public List<Subject> queryTypeForSta();
    //查询日期为了统计
    @Select("SELECT COUNT(*) AS CONT,to_char(s.updateWhen,'yyyy-MM-dd') AS NAME FROM subject  s " +
            "GROUP BY s.updateWhen ORDER BY s.updateWhen")
    public List<Subject> queryUpdateWhenForSta();
    //查询更新者为了统计
    @Select("SELECT COUNT(*) AS CONT,s.updateBy AS NAME FROM subject  s GROUP BY s.updateBy")
    public List<Subject> queryUpdateByForSta();
    //查询分数为了统计
    @Select("SELECT COUNT(*) AS CONT,s.subjectScore AS NAME FROM subject  s GROUP BY s.subjectScore")
    public List<Subject> queryScoreForSta();
}
