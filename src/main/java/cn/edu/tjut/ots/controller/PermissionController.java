package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.Role;
import cn.edu.tjut.ots.services.PermissionService;
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
    PermissionService permissionServiceImpl;

    /**
     * 返回权限页
     * @return
     */
    @RequestMapping("listPermissionPage")
    public String listPermissionPage() {
        return "teacher/permission";
    }

    /**
     * 刷新用户列表
     * @return
     */
    @ResponseBody
    @RequestMapping("refreshUserList")
    public List refreshUserList() {
        return usersServiceImpl.queryUsers();
    }

    /**
     * 刷新角色列表
     * @return
     */
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

    /**
     * 删除角色
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteRole")
    public boolean deleteRole(@RequestParam("roleIds")String[] ids){
        boolean bool = false;
        roleServiceImpl.deleteRole(ids);
        bool = true;
        return bool;
    }

    /**
     * 添加用户
     * @param userName
     * @param pass
     * @param againPassword
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("addUsers")
    public boolean mergeUser(@RequestParam("userName")String userName,
                             @RequestParam("password")String pass,
                             @RequestParam("againPassword")String againPassword,
                             HttpSession session){
        boolean bool = usersServiceImpl.addUsers(userName,pass,againPassword,(String)session.getAttribute("username"));
        return bool;
    }

    /**
     * 删除用户
     * @param userNames
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteUsers")
    public boolean deleteUsers(@RequestParam("userNames")String[] userNames){
        boolean bool = false;
        usersServiceImpl.deleteUsers(userNames);
        bool = true;
        return bool;
    }

    /**
     * 查询权限树使用的数据
     * @return
     */
    @ResponseBody
    @RequestMapping("queryPermissionTree")
    public List queryPermissionTree(){
        return permissionServiceImpl.queryPermissionTree();
    }

    /**
     * 查询角色树使用的数据
     * @return
     */
    @ResponseBody
    @RequestMapping("queryRoleTree")
    public List queryRoleTree(){
        return permissionServiceImpl.queryRoleTree();
    }

    /**
     * 添加角色权限
     * @param roleId
     * @param resourcesIds
     * @return
     */
    @ResponseBody
    @RequestMapping("addAuth")
    public boolean addAuth(@RequestParam("roleId")String roleId,
                           @RequestParam("resourcesIds")String[] resourcesIds){
        boolean bool = false;
        permissionServiceImpl.addAuth(roleId,resourcesIds);
        bool = true;
        return bool;
    }

    /**
     * 查询角色对应的权限树
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("queryAuth")
    public List queryAuth(@RequestParam("roleId") String roleId){
        return permissionServiceImpl.queryAuth(roleId);
    }

    /**
     * 查询用户关联的角色
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("queryRelRole")
    public List queryRelRole(@RequestParam("userId")String userId){
        return permissionServiceImpl.queryRefRole(userId);
    }

    @ResponseBody
    @RequestMapping("addUserRoleRel")
    public boolean addUserRoleRel(
            @RequestParam("userId")String userId,
            @RequestParam("roleIds")String[] roleIds){
        boolean bool = false;
        permissionServiceImpl.addUserRoleRel(userId,roleIds);
        bool = true;
        return bool;
    }
}
