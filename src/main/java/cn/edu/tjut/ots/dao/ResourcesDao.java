package cn.edu.tjut.ots.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/30.
 */
@Mapper
public interface ResourcesDao {
    //根据父类ID查询资源
    @Select("SELECT uuid AS \"uuid\",name AS \"text\",uuid AS \"value\" FROM resources WHERE parentId=#{param}")
    public List<Map<String,Object>> queryResourcesByParentId(String parentId);
    //查询父类ID为空的资源
    @Select("SELECT uuid AS \"uuid\",name AS \"text\",uuid AS \"value\" FROM resources WHERE parentId IS NULL")
    public List<Map<String,Object>> queryResourcesOfNullParentId();
    //根据roleId删除所有权限
    @Delete("delete from role_resources where roleId=#{param}")
    public void deleteAuth(String roleId);
    //添加权限
    public void addAuth(List<Map<String,String>> maps);
    //通过角色ID查询权限
    @Select("SELECT r.name AS \"text\",r.uuid AS \"resourcesId\" FROM resources r " +
            "JOIN (SELECT s.resourcesid FROM role_resources s " +
            "where s.roleid=#{param}) t ON r.uuid = t.resourcesId")
    public List<Map<String,String>> queryAuth(String roleId);
}
