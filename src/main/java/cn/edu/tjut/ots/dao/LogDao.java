package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
@Mapper
public interface LogDao {
    @Insert("insert into log values (#{uuid},#{userName},#{ip},#{operation}," +
            "to_date(to_char(sysdate,'yyyy-mm-dd HH24:MI:SS'),'yyyy-mm-dd HH24:MI:SS'))")
    public void addLog(Log log);
}
