package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.Role;
import cn.edu.tjut.ots.services.RoleService;
import cn.edu.tjut.ots.services.UsersService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
@Controller
@Scope("prototype")
@RequestMapping("permission")
public class PermissionController {

    @Resource
    RoleService roleServiceImpl;

    @Resource
    UsersService usersServiceImpl;

    @RequestMapping("listPermissionPage")
    public String listPermissionPage() {
        return "teacher/permission";
    }

    @ResponseBody
    @RequestMapping("refreshUserList")
    public List refreshUserList() {
        return usersServiceImpl.queryUsers();
    }

    @ResponseBody
    @RequestMapping("refreshRoleList")
    public List refreshRoleList() {
        return roleServiceImpl.queryRole();
    }

    @ResponseBody
    @RequestMapping("mergeRole")
    public String mergeRole(Role role, HttpSession session){
        return roleServiceImpl.mergeRole(role,(String)session.getAttribute("username"));
    }

    @ResponseBody
    @RequestMapping("deleteRole")
    public boolean deleteRole(@RequestParam("roleIds")String[] ids){
        boolean bool = false;
        roleServiceImpl.deleteRole(ids);
        bool = true;
        return bool;
    }
}
