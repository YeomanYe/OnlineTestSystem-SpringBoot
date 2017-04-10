package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.RoleDao;
import cn.edu.tjut.ots.po.Role;
import cn.edu.tjut.ots.services.RoleService;
import cn.edu.tjut.ots.utils.CreateUserBy;
import cn.edu.tjut.ots.utils.EmptyUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
@Service
@Transactional
@Scope("singleton")
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleDao roleDao;

    @Override
    public List queryRole() {
        return roleDao.queryRole();
    }

    @Override
    public String mergeRole(Role role, String userName) {
        if(EmptyUtil.isFieldEmpty(role.getUuid())){
            role.setUuid(UUID.randomUUID().toString().replace("-",""));
            CreateUserBy.setUser(role,null,userName);
            roleDao.addRole(role);
        }else{
            CreateUserBy.setUser(role,"update",userName);
            roleDao.updateRole(role);
        }
        return role.getUuid();
    }

    @Override
    public void deleteRole(String[] ids) {
        roleDao.deleteRoleByIds(ids);
    }

    @Override
    public Map<String, String> queryMaxResRole(String username) {
        return roleDao.queryMaxResRole(username);
    }
}
