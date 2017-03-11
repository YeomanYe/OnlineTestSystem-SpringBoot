package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Paper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
@Mapper
public interface PaperDao {
    public List<Paper> queryBrifPaper();
}
