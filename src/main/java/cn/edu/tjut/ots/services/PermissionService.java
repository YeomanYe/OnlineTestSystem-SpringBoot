package cn.edu.tjut.ots.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by KINGBOOK on 2017/3/30.
 */
public interface PermissionService {
    /**
     * 查询全部resources用于展示权限树
     * @return
     */
    public List<Map<String,Object>> queryPermissionTree();

    /**
     * 添加权限
     * @param roleId
     * @param resourceIds
     */
    public void addAuth(String roleId, String[] resourceIds);

    /**
     * 查询权限
     * @param roleId
     * @return
     */
    public List queryAuth(String roleId);

    /**
     * 查询角色用于角色树的展示
     * @return
     */
    public List queryRoleTree();

    /**
     * 查询用户关联的角色
     * @param userId
     * @return
     */
    public List queryRefRole(String userId);

    /**
     * 添加用户角色关联到表中
     * @param userId
     * @param roleIds
     */
    public void addUserRoleRel(String userId,String[] roleIds);

    /**
     * 查询用户的权限
     * @param userId
     */
    public Set queryUserAuth(String userId);
}
