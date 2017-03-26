package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.UserLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
@Mapper
public interface UserLogDao {
    //添加用户日志
    @Insert("insert into userLog values (#{uuid},#{userName},#{ip},#{operation}," +
            "to_date(to_char(sysdate,'yyyy-mm-dd HH24:MI:SS'),'yyyy-mm-dd HH24:MI:SS'))")
    public void addLog(UserLog userLog);
    //查询全部用户日志
    @Select("select uuid,userName,ip,operation,to_char(time,'yyyy-mm-dd HH24:MI:SS') AS timeStr from userlog")
    public List<UserLog> queryAllUserLog();
    //查询日期为了统计
    @Select("select COUNT(*) AS CONT,to_char(time,'yyyy-mm-dd') AS NAME from userlog GROUP BY to_char(TIME,'yyyy-mm-dd')")
    public List<UserLog> queryDateForSta();
    //查询用户名为了统计
    @Select("select count(*) as cont,username as name from userlog group by username")
    public List<UserLog> queryUserNameForSta();
    //查询操作为了统计
    @Select("SELECT COUNT(*) AS CONT,operation AS NAME FROM userlog GROUP BY operation")
    public List<UserLog> queryOperationForSta();
    //批量删除日志记录
    public void deleteUserLogByIds(String[] ids);
}
