package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
@Mapper
public interface SubjectDao {
    @Select("select * from subject")
    public List<Subject> querySubject();
}
