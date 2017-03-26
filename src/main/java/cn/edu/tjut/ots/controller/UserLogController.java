package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.UserLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/26.
 */
@Controller
@RequestMapping("/userLog")
@Scope("prototype")
public class UserLogController {
    @Resource
    private UserLogService userLogServiceImpl;

    @RequestMapping("listUserLogPage")
    public String listUserLogPage(){
        return "teacher/userLog_list";
    }

    @ResponseBody
    @RequestMapping("refreshUserLog")
    public List refreshUserLog(){
        return userLogServiceImpl.queryAllUserLog();
    }

    @ResponseBody
    @RequestMapping("queryForSta")
    public List queryForSta(@RequestParam("type")String type){
        return userLogServiceImpl.queryForSta(type);
    }

    @ResponseBody
    @RequestMapping("deleteUserLog")
    public boolean deleteUserLog(@RequestParam("userLogIds")String[] ids){
        boolean bool = false;
        userLogServiceImpl.deleteUserLogByIds(ids);
        bool = true;
        return bool;
    }
}
