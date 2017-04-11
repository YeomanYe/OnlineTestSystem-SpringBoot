package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.UserInfo;
import cn.edu.tjut.ots.services.UserInfoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

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

    @ResponseBody
    @RequestMapping("queryForSta")
    public List<Map<String,Object>> queryForSta(@RequestParam("type")String type){
        return userInfoServiceImpl.queryForSta(type);
    }

    @ResponseBody
    @RequestMapping("refreshUserInfo")
    public List<UserInfo> refreshUserInfo(){
        return userInfoServiceImpl.queryAllUserInfo();
    }

    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse response){
        OutputStream os = null;
        try {
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename="+new String("用户信息.xlsx".getBytes("utf-8"), "ISO8859-1"));
            os = response.getOutputStream();
            userInfoServiceImpl.exportUserInfo(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
