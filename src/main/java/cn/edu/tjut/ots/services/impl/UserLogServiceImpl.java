package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.UserLogDao;
import cn.edu.tjut.ots.po.UserLog;
import cn.edu.tjut.ots.services.UserLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
@Service
@Scope("singleton")
public class UserLogServiceImpl implements UserLogService {
    @Resource
    UserLogDao userLogDao;

    @Override
    public void addLog(UserLog userLog) {
        userLogDao.addLog(userLog);
    }

    @Override
    public List queryAllUserLog() {
        return userLogDao.queryAllUserLog();
    }

    @Override
    public List queryForSta(String type) {
        List list = null;
        switch (type) {
            case "userName":
                list = userLogDao.queryUserNameForSta();
                break;
            case "operation":
                list = userLogDao.queryOperationForSta();
                break;
            case "date":
                list = userLogDao.queryDateForSta();
                break;
        }
        return list;
    }

    @Override
    public void deleteUserLogByIds(String[] ids) {
        userLogDao.deleteUserLogByIds(ids);
    }
}
