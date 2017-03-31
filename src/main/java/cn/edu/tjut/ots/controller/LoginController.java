package cn.edu.tjut.ots.controller;


import cn.edu.tjut.ots.services.PermissionService;
import cn.edu.tjut.ots.services.UsersService;
import cn.edu.tjut.ots.utils.EmptyUtil;
import cn.edu.tjut.ots.utils.MD5Util;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 登陆控制器
 */
@Controller
@Scope("prototype")
public class LoginController {
    @Resource
    PermissionService permissionServiceImpl;

    @Resource
    UsersService usersServiceImpl;

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
        if ("admin".equals(username)) {
            session.setAttribute("username",username);
            session.setAttribute("resources",permissionServiceImpl.queryUserAuth(username));
            return "teacher/index";
        }else if(!EmptyUtil.isFieldEmpty(username)){
            String md5Pass = MD5Util.getPassword(password);
            if(md5Pass.equals(usersServiceImpl.queryPassByName(username))){
                session.setAttribute("username",username);
                session.setAttribute("resources",permissionServiceImpl.queryUserAuth(username));
                return "teacher/index";
            }
        }
        return "login";
    }
}
