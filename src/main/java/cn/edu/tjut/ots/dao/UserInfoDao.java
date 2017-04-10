package cn.edu.tjut.ots.dao;

import cn.edu.tjut.ots.po.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * Created by KINGBOOK on 2017/4/9.
 */
@Mapper
public interface UserInfoDao {
    //插入用户信息
    @Insert("insert into user_info values(#{userName},#{nickName},#{job},#{sex},#{phone},#{qq},#{email}," +
            "#{birthday},#{profile},'')")
    public void addUserInfo(UserInfo userInfo);
    //更新用户信息
    @Update("update user_info set nickName=#{nickName},job=#{job},sex=#{sex},phone=#{phone},qq=#{qq}," +
            "email=#{email},birthday=#{birthday},profile=#{profile} where username=#{userName}")
    public void updateUserInfo(UserInfo userInfo);
    //根据用户名查询用户信息
    @Select("select * from user_info where username=#{param}")
    public UserInfo queryUserInfoByUsername(String username);
    //更新头像图片信息
    @Update("update user_info set avaterId=#{avaterId} where username=#{userName}")
    public void updateAvaterId(UserInfo userInfo);
    //查询头像和个性签名
    @Select("SELECT i.relpath AS \"avater\",u.PROFILE AS \"profile\" FROM user_info u " +
            " JOIN image i ON u.avaterid = i.uuid WHERE username=#{param}")
    public Map<String,String> queryAvaterAndProfile(String username);

}
