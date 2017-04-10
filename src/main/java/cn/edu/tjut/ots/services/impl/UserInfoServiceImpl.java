package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.UserInfoDao;
import cn.edu.tjut.ots.po.UserInfo;
import cn.edu.tjut.ots.services.UserInfoService;
import cn.edu.tjut.ots.utils.EmptyUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/4/9.
 */
@Service
@Scope("singleton")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public void mergeUserInfo(UserInfo userInfo, String username) {
        UserInfo info = userInfoDao.queryUserInfoByUsername(username);
        userInfo.setUserName(username);
        if(EmptyUtil.isObjEmpty(info)){
            userInfoDao.addUserInfo(userInfo);
        }else{
            userInfoDao.updateUserInfo(userInfo);
        }
    }

    @Override
    public UserInfo queryUserInfoByUsername(String username) {
        return userInfoDao.queryUserInfoByUsername(username);
    }

    @Override
    public Map<String, String> queryAvaterAndProfile(String username) {
        return userInfoDao.queryAvaterAndProfile(username);
    }
}
