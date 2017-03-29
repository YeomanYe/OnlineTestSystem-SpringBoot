package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.UsersDao;
import cn.edu.tjut.ots.po.Users;
import cn.edu.tjut.ots.services.UsersService;
import cn.edu.tjut.ots.utils.CreateUserBy;
import cn.edu.tjut.ots.utils.EmptyUtil;
import cn.edu.tjut.ots.utils.MD5Util;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
@Service
@Scope("singleton")
public class UsersServiceImpl implements UsersService {
    @Resource
    UsersDao usersDao;

    @Override
    public List<Users> queryUsers() {
        return usersDao.queryUsers();
    }

    @Override
    public boolean addUsers(String userName, String pass, String againPass, String createBy) {
        if(!pass.equals(againPass)) return false;
        if(!EmptyUtil.isObjEmpty(usersDao.queryUsersByUserName(userName))) return false;
        Users users = new Users();
        users.setUserName(userName);
        users.setPass(MD5Util.getPassword(pass));
        CreateUserBy.setUser(users,null,createBy);
        usersDao.addUsers(users);
        return true;
    }

    @Override
    public void deleteUsers(String[] userNames) {
        usersDao.deleteUsers(userNames);
    }
}
