package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
@Mapper
public interface UsersDao {
    //查询信息用于表格展示
    @Select("SELECT to_char(updateWhen,'yyyy-mm-dd') AS updateWhenStr,updateBy,userName FROM USERS")
    public List<Users> queryUsers();
    //添加用户
    @Insert("INSERT INTO users values(#{userName},#{pass},#{createBy},to_date(to_char(sysdate,'yyyy/mm/dd')," +
            "'yyyy/mm/dd'),#{updateBy},to_date(to_char(sysdate,'yyyy/mm/dd'),'yyyy/mm/dd'))")
    public void addUsers(Users users);
    //根据用户名查找用户
    @Select("select * from users where userName = #{param}")
    public Users queryUsersByUserName(String userName);
    //删除用户
    public void deleteUsers(String[] userNames);
    //添加用户-角色关系到表中
    public void addUserRoleRel(List<Map<String,String>> maps);
    //根据用户ID，清除用户角色关联表中的数据
    @Delete("delete from user_role where userId=#{param}")
    public void deleteUserRoleRel(String userId);
    //根据用户ID查询用户权限集合
    @Select("SELECT r.resourceCode FROM resources r JOIN (SELECT resourcesId FROM role_resources a JOIN " +
            "(SELECT roleId FROM user_role WHERE userId=#{param}) b ON a.roleid = b.roleid ) c " +
            "ON r.uuid = c.resourcesId")
    public Set<String> queryUserAuth(String userId);
    //查询用户的密码
    @Select("select pass from users where username=#{param}")
    public String queryPassByUsername(String userName);
}
