package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.BaseData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
@Mapper
public interface BaseDataDao {
    //根据数据类型名查询基础数据
    @Select("select * from basedata where dataType=#{param}")
    public List<BaseData> queryBaseDataByType(String dataType);
    //查询基础数据列表
    @Select("select * from basedata")
    public List<BaseData> queryBaseDataList();
    //查询与名称对应的试题类型的UUID
    @Select("SELECT uuid FROM basedata WHERE datatype = 'subjectType' AND NAME=#{param}")
    public String querySubjectTypeIdByName(String name);
    //查询所有基本数据类型
    @Select("SELECT DISTINCT typeName,dataType FROM baseData")
    public List<BaseData> queryBaseDataType();
    //添加基础数据
    @Insert("INSERT INTO baseData values (#{uuid},#{dataType},#{name},#{typeName})")
    public void addBaseData(BaseData baseData);
    //更改基础数据类型
    @Update("update baseData set dataType=#{dataType},name=#{name},typeName=#{typeName} where uuid = #{uuid}")
    public void updateBaseData(BaseData baseData);
    //批量删除基础数据
    public void deleteBaseDataByIds(String[] ids);
}
