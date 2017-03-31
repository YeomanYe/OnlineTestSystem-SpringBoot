package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.UserLogDao;
import cn.edu.tjut.ots.po.UserLog;
import cn.edu.tjut.ots.services.UserLogService;
import cn.edu.tjut.ots.utils.ExcelUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
@Service
@Transactional
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

    @Override
    public void exportExcel(OutputStream os) {
        List<UserLog> userLogs = userLogDao.queryAllUserLog();
        try {
            ExcelUtil.excelExport(UserLog.class,os,userLogs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
