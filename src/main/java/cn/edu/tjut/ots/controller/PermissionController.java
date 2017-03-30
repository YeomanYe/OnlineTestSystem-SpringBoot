package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.Role;
import cn.edu.tjut.ots.services.ResourcesService;
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

    @Resource
    ResourcesService resourcesServiceImpl;

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

    /**
     * 添加或更新角色
     * @param role
     * @param session
     * @return
     */
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

    @ResponseBody
    @RequestMapping("addUsers")
    public boolean mergeUser(@RequestParam("userName")String userName,
                             @RequestParam("password")String pass,
                             @RequestParam("againPassword")String againPassword,
                             HttpSession session){
        boolean bool = usersServiceImpl.addUsers(userName,pass,againPassword,(String)session.getAttribute("username"));
        return bool;
    }

    @ResponseBody
    @RequestMapping("deleteUsers")
    public boolean deleteUsers(@RequestParam("userNames")String[] userNames){
        boolean bool = false;
        usersServiceImpl.deleteUsers(userNames);
        bool = true;
        return bool;
    }

    @ResponseBody
    @RequestMapping("queryPermissionTree")
    public List queryPermissionTree(){
        return resourcesServiceImpl.queryPermissionTree();
    }

    @ResponseBody
    @RequestMapping("addAuth")
    public boolean addAuth(@RequestParam("roleId")String roleId,
                           @RequestParam("resourcesIds")String[] resourcesIds){
        boolean bool = false;
        resourcesServiceImpl.addAuth(roleId,resourcesIds);
        bool = true;
        return bool;
    }

    @ResponseBody
    @RequestMapping("queryAuth")
    public List queryAuth(@RequestParam("roleId") String roleId){
        return resourcesServiceImpl.queryAuth(roleId);
    }
}
