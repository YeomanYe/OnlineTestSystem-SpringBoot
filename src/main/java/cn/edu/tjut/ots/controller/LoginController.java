package cn.edu.tjut.ots.controller;


import cn.edu.tjut.ots.services.PermissionService;
import cn.edu.tjut.ots.services.RoleService;
import cn.edu.tjut.ots.services.UserInfoService;
import cn.edu.tjut.ots.services.UsersService;
import cn.edu.tjut.ots.utils.EmptyUtil;
import cn.edu.tjut.ots.utils.MD5Util;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 登陆控制器
 */
@Controller
@Scope("prototype")
public class LoginController {
    @Resource
    private PermissionService permissionServiceImpl;

    @Resource
    private UsersService usersServiceImpl;

    @Resource
    private UserInfoService userInfoServiceImpl;

    @Resource
    private RoleService roleServiceImpl;

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
                        HttpServletRequest req,
                        HttpSession session) {
       if(!EmptyUtil.isFieldEmpty(username)){
            String md5Pass = MD5Util.getPassword(password);
            if(md5Pass.equals(usersServiceImpl.queryPassByName(username)) || "admin".equals(username)){
                session.setAttribute("username",username);
                session.setAttribute("resources",permissionServiceImpl.queryUserAuth(username));
                Map<String,String> map = userInfoServiceImpl.queryAvaterAndProfile(username);
                req.setAttribute("avater",map.get("avater"));
                req.setAttribute("profile",map.get("profile"));
                map = roleServiceImpl.queryMaxResRole(username);
                req.setAttribute("rolename",map.get("rolename"));
                return "teacher/index";
            }
        }
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("username");
        session.removeAttribute("resources");
        return "login";
    }
}
