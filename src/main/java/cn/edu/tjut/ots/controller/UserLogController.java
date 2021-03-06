package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.UserLogService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
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

    @ResponseBody
    @RequestMapping("deleteAllUserLog")
    public boolean deleteAllUserLog(){
        boolean bool = false;
        userLogServiceImpl.deleteAllUserLog();
        bool = true;
        return bool;
    }

    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse response){
        response.setHeader("Content-Disposition", "attachment; filename=userLog.xlsx");
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            userLogServiceImpl.exportExcel(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
