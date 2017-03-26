package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.UserLog;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
public interface UserLogService {
    /**
     * 添加用户日志
     * @param userLog
     */
    public void addLog(UserLog userLog);

    /**
     * 查询全部的用户日志
     * @return
     */
    public List queryAllUserLog();

    /**
     * 查询数据用于统计
     * @param type
     * @return
     */
    public List queryForSta(String type);

    /**
     * 批量删除用户日志
     * @param ids
     */
    public void deleteUserLogByIds(String[] ids);
}
