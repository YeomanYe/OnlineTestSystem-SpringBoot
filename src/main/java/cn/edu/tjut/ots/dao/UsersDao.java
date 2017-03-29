package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
}
