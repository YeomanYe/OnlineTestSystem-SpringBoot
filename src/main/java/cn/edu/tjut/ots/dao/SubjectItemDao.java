package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.SubjectItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/2.
 */
@Mapper
public interface SubjectItemDao {
    //根据ID查询试题项
    @Select("select * from subject_item where subjectId = #{param}")
    public List<SubjectItem> querySubjectItem(String uuid);
}
