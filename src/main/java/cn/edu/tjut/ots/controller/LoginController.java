package cn.edu.tjut.ots.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 登陆控制器
 */
@Controller
public class LoginController {
    @RequestMapping(path = "/login.html")
    public String loginPage() {
        return "login";
    }

    /**
     * 登陆
     *
     * @return
     */
    @PostMapping(path = "/login")
    public String login(@RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = false) String password,
                        HttpSession session) {
        if (username != null && !"".equals(username)) {
            session.setAttribute("username","admin");
            return "teacher/index";
        }
        return "login";
    }
}
