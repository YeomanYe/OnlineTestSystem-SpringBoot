package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Paper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
@Mapper
public interface PaperDao {
    public List<Paper> queryBrifPaper();
    @Insert("insert into paper (uuid,paperName,paperDesc,paperType," +
            "paperScore,ansTime,subjectCnt,createBy,createWhen,updateBy,updateWhen) " +
            "values(#{uuid},#{paperName},#{paperDesc},#{paperType},#{paperScore},#{ansTime}," +
            "#{subjectCnt},#{createBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd')," +
            "#{updateBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'))")
    public void addPaper(Paper paper);
    public void addPaperSubject(List maps);
}
