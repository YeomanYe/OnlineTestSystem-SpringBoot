package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.ResourcesDao;
import cn.edu.tjut.ots.dao.RoleDao;
import cn.edu.tjut.ots.dao.UsersDao;
import cn.edu.tjut.ots.services.PermissionService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by KINGBOOK on 2017/3/30.
 */
@Service
@Transactional
@Scope("singleton")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    ResourcesDao resourcesDao;

    @Resource
    RoleDao roleDao;

    @Resource
    UsersDao usersDao;

    @Override
    public List<Map<String, Object>> queryPermissionTree() {
        List<Map<String, Object>> parentMaps = resourcesDao.queryResourcesOfNullParentId();
        queryPermissionTreeAid(parentMaps);
        return parentMaps;
    }

    /**
     * 查询权限树的助手方法
     *
     * @param maps
     * @return
     */
    private List<Map<String, Object>> queryPermissionTreeAid(List<Map<String, Object>> maps) {
        for (Map<String, Object> map : maps) {
            String uuid = (String) map.get("uuid");
            List<Map<String, Object>> retMaps = resourcesDao.queryResourcesByParentId(uuid);
            if (retMaps.size() == 0) continue;
            else {
                map.put("nodes", retMaps);
                queryPermissionTreeAid(retMaps);
            }
        }
        return null;
    }

    @Override
    public void addAuth(String roleId, String[] resourceIds) {
        resourcesDao.deleteAuth(roleId);
        List<Map<String,String>> maps = new ArrayList();
        for(int i=0,len=resourceIds.length;i<len;i++){
            Map<String,String> map = new HashMap();
            map.put("roleId",roleId);
            map.put("resourcesId",resourceIds[i]);
            maps.add(map);
        }
        resourcesDao.addAuth(maps);
    }

    @Override
    public List queryAuth(String roleId) {
        return resourcesDao.queryAuth(roleId);
    }

    @Override
    public List queryRoleTree() {
        return roleDao.queryRoleTree();
    }

    @Override
    public List queryRefRole(String userId) {
        return roleDao.queryRelRole(userId);
    }

    @Override
    public void addUserRoleRel(String userId, String[] roleIds) {
        //清空对应的用户角色关联
        usersDao.deleteUserRoleRel(userId);
        //添加用户角色关联到表中
        List<Map<String,String>> maps = new ArrayList();
        for (String roleId : roleIds) {
            Map<String,String> map = new HashMap();
            map.put("roleId",roleId);
            map.put("userId",userId);
            maps.add(map);
        }
        usersDao.addUserRoleRel(maps);
    }

    @Override
    public Set queryUserAuth(String userId) {
        return usersDao.queryUserAuth(userId);
    }
}
