package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.ResourcesDao;
import cn.edu.tjut.ots.services.ResourcesService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/30.
 */
@Service
@Scope("singleton")
public class ResourcesServiceImpl implements ResourcesService {
    @Resource
    ResourcesDao resourcesDao;

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
}
