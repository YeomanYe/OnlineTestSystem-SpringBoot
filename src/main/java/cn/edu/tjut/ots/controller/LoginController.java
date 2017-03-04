package cn.edu.tjut.ots.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                        @RequestParam(value = "password", required = false) String password) {
        if (username != null && !"".equals(username)) {
            return "teacher/index";
        }
        return "login";
    }
}
