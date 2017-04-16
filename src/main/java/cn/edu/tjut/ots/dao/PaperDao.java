package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Paper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
@Mapper
public interface PaperDao {
    //查询试卷简要信息
    @Select("select t.uuid,t.paperName,b.name AS paperType,t.subjectCnt,t.ansTime,to_char(t.updatewhen,'yyyy-mm-dd') AS updateWhenStr" +
            " from PAPER t JOIN BASEDATA b ON t.paperType = b.uuid")
    public List<Paper> queryBrifPaper();
    //查询试卷详细信息
    @Select("SELECT p.uuid,p.paperName,p.paperDesc,b.name AS paperType,p.subjectCnt,p.paperScore," +
            "p.ansTime,p.createBy,p.createWhen,p.updateBy,p.updateWhen " +
            " FROM paper p JOIN basedata b ON p.paperType = b.uuid")
    public List<Paper> queryDetailPaper();
    //添加试卷
    @Insert("insert into paper (uuid,paperName,paperDesc,paperType," +
            "paperScore,ansTime,subjectCnt,createBy,createWhen,updateBy,updateWhen) " +
            "values(#{uuid},#{paperName},#{paperDesc},#{paperType},#{paperScore},#{ansTime}," +
            "#{subjectCnt},#{createBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd')," +
            "#{updateBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'))")
    public void addPaper(Paper paper);
    //更新试卷
    @Update("update paper set paperName=#{paperName},paperDesc=#{paperDesc},paperType=#{paperType}," +
            "paperScore=#{paperScore},ansTime=#{ansTime},subjectCnt=#{subjectCnt},updateBy=#{updateBy}," +
            "updateWhen=to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd') where uuid=#{uuid}")
    public void updatePaper(Paper paper);
    //清空试卷下的所有题目
    @Delete("delete from paper_subject where paperId = #{param}")
    public void deleteSubject(String paperId);
    //根据id批量删除试卷
    public void deletePaperByIds(String[] paperIds);
    //向试卷添加试题
    public void addPaperSubject(List maps);
    //查询试卷对应的试题id列表
    @Select("SELECT subjectId FROM paper_subject WHERE paperid = #{param}")
    public List<String> querySubjectIdsByPaperId(String paperId);
    //通过ID查询试卷信息
    @Select("SELECT * FROM paper WHERE uuid = #{param}")
    public Paper queryPaperById(String paperId);
    //查询类型为了统计
    @Select("SELECT b.name AS \"name\",COUNT(*) AS \"cont\" FROM paper p " +
            "JOIN basedata b ON p.papertype = b.uuid GROUP BY b.name")
    public List<Map<String,Object>> queryTypeForSta();
    //查询更新日期为了统计
    @Select("SELECT to_char(p.updateWhen,'yyyy-mm-dd') AS \"name\",COUNT(*) AS \"cont\" FROM paper p GROUP BY p.updateWhen")
    public List<Map<String,Object>> queryUpdateWhenForSta();
    //查询试题量为了统计
    @Select("SELECT p.subjectCnt AS \"name\",COUNT(*) AS \"cont\" FROM paper p GROUP BY p.subjectCnt")
    public List<Map<String,Object>> querySubjectCntForSta();
    //查询答题时间为了统计
    @Select("SELECT p.ansTime AS \"name\",COUNT(*) AS \"cont\" FROM paper p GROUP BY p.ansTime")
    public List<Map<String,Object>> queryAnsTimeForSta();
    @Select("SELECT p.paperName,p.paperDesc,p.paperScore,p.anstime,p.subjectcnt,b.name AS paperType FROM paper p " +
            "JOIN basedata b ON p.papertype = b.uuid WHERE p.uuid = #{param}")
    public Paper queryPaperInfoById(String paperId);
}
