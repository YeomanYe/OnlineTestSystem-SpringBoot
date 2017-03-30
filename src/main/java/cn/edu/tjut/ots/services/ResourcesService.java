package cn.edu.tjut.ots.services;

import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/30.
 */
public interface ResourcesService {
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
}
