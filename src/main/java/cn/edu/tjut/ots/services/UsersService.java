package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.Users;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
public interface UsersService {
    /**
     * 查询所有用户用于列表显示
     * @return
     */
    public List<Users> queryUsers();
}
