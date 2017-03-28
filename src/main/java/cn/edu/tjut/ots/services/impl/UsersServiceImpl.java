package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.UsersDao;
import cn.edu.tjut.ots.po.Users;
import cn.edu.tjut.ots.services.UsersService;
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
}
