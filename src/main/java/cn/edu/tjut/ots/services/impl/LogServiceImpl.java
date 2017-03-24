package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.LogDao;
import cn.edu.tjut.ots.po.Log;
import cn.edu.tjut.ots.services.LogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
@Service
@Scope("singleton")
public class LogServiceImpl implements LogService{
    @Resource
    LogDao logDao;
    @Override
    public void addLog(Log log) {
        logDao.addLog(log);
    }
}
