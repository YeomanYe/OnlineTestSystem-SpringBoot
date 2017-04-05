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

    /**
     * 添加用户
     * @param userName
     * @param pass
     * @param againPass
     * @param createBy
     */
    public boolean addUsers(String userName, String pass, String againPass, String createBy);

    /**
     * 删除用户
     * @param userNames
     */
    public void deleteUsers(String[] userNames);

    /**
     * 通过用户名查询密码
     * @param userName
     * @return
     */
    public String queryPassByName(String userName);

}
