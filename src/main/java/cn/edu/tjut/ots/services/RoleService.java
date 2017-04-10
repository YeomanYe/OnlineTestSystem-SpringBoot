package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
public interface RoleService {
    /**
     * 查询所有角色用于列表显示
     * @return
     */
    public List queryRole();

    /**
     * 更新或添加角色
     * @param role
     * @param userName
     */
    public String mergeRole(Role role, String userName);

    /**
     * 批量删除角色
     * @param ids
     */
    public void deleteRole(String[] ids);

    /**
     * 查询资源数最多的角色
     * @param username
     * @return
     */
    public Map<String,String> queryMaxResRole(String username);
}
