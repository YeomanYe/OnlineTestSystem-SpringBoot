package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
@Mapper
public interface RoleDao {
    //查询角色信息用于表格显示
    @Select("SELECT uuid,to_char(updateWhen,'yyyy-mm-dd') AS updateWhenStr,name,updateBy FROM ROLE")
    public List<Role> queryRole();
    //添加角色
    @Insert("insert into role values (#{uuid},#{name},#{createBy},to_date(to_char(sysdate,'yyyy/mm/dd')," +
            "'yyyy/mm/dd'),#{updateBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'))")
    public void addRole(Role role);
    //更新角色
    @Update("update role set name=#{name},updateBy=#{updateBy},updateWhen=to_date(to_char(sysdate," +
            "'yyyy/mm/dd'),'yyyy/mm/dd') where uuid=#{uuid}")
    public void updateRole(Role role);
    //批量删除角色
    public void deleteRoleByIds(String[] ids);
}
