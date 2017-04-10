package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.UserInfo;

import java.util.Map;

/**
 * Created by KINGBOOK on 2017/4/9.
 */
public interface UserInfoService {
    /**
     * 添加或更新用户信息
     * @param userInfo
     * @param username
     */
    public void mergeUserInfo(UserInfo userInfo,String username);

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    public UserInfo queryUserInfoByUsername(String username);

    /**
     * 查询头像和个性签名
     * @param username
     * @return
     */
    public Map<String,String> queryAvaterAndProfile(String username);
}
