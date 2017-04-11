package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.UserInfo;

import java.io.OutputStream;
import java.util.List;
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

    /**
     * 查询信息用于统计
     * @param type
     * @return
     */
    public List<Map<String,Object>> queryForSta(String type);

    /**
     * 查询全部的用户信息
     * @return
     */
    public List<UserInfo> queryAllUserInfo();

    /**
     * 导出用户信息
     * @param os
     */
    public void exportUserInfo(OutputStream os);
}
