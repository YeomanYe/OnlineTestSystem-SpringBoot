package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.BaseData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
@Mapper
public interface BaseDataDao {
    //根据数据类型名查询基础数据
    @Select("select * from basedata where dataType=#{param}")
    public List<BaseData> queryBaseDataByType(String dataType);
    @Select("select * from basedata")
    public List<BaseData> queryBaseDataList();
}
