package cn.edu.tjut.ots.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 登陆控制器
 * Created by KINGBOOK on 2017/2/20.
 */
@Controller
public class LoginController {
    @RequestMapping(path = "/")
    public String loginPage(){
        System.out.println("success");
        return "login";
    }

    /**
     * 登陆
     * @return
     */
    @PostMapping(path="/login")
    public String login(){
        return null;
    }
}
