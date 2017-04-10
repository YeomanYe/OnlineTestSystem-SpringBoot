package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.UserInfo;
import cn.edu.tjut.ots.services.UserInfoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by KINGBOOK on 2017/4/9.
 */
@Controller
@Scope("prototype")
@RequestMapping("/userInfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoServiceImpl;

    @RequestMapping("userInfoPage")
    public String userInfoPage(){
        return "teacher/userInfo";
    }

    @ResponseBody
    @RequestMapping("mergeUserInfo")
    public boolean mergeUserInfo(
            UserInfo userInfo,
            HttpSession session){
        boolean bool = false;
        userInfoServiceImpl.mergeUserInfo(userInfo,(String) session.getAttribute("username"));
        bool = true;
        return bool;
    }

    @ResponseBody
    @RequestMapping("queryUserInfoByUsername")
    public UserInfo queryUserInfoForUpdate(HttpSession session){
        return userInfoServiceImpl.queryUserInfoByUsername((String)session.getAttribute("username"));
    }

}
