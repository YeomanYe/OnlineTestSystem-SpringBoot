package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.UserLog;
import org.apache.ibatis.annotations.Delete;
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
    @Select("select COUNT(*) AS \"cont\",to_char(time,'yyyy-mm-dd') AS \"name\" from userlog GROUP BY to_char(TIME,'yyyy-mm-dd')")
    public List<Map<String,Object>> queryDateForSta();
    //查询用户名为了统计
    @Select("select count(*) as \"cont\",username as \"name\" from userlog group by username")
    public List<Map<String,Object>> queryUserNameForSta();
    //查询操作为了统计
    @Select("SELECT COUNT(*) AS \"cont\",operation AS \"name\" FROM userlog GROUP BY operation")
    public List<Map<String,Object>> queryOperationForSta();
    //批量删除日志记录
    public void deleteUserLogByIds(String[] ids);
    @Delete("delete from userLog")
    public void deleteAllUserLog();
}
