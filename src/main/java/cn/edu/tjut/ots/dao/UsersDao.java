package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
@Mapper
public interface UsersDao {

    @Select("SELECT to_char(updateWhen,'yyyy-mm-dd') AS updateWhenStr,updateBy,userName FROM USERS")
    public List<Users> queryUsers();
}
