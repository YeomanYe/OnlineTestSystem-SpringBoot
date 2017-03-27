package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.BaseData;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
public interface BaseDataService {
    /**
     * 通过数据类型查询基础数据
     * @param dataType
     * @return
     */
    public List<BaseData> queryBaseDataByType(String dataType);

    /**
     * 查询全部的基础数据
     * @return
     */
    public List<BaseData> queryBaseData();

    /**
     * 查询全部基础数据类型
     * @return
     */
    public List<BaseData> queryBaseDataType();

    /**
     * 添加基础数据
     * @param baseDataId
     * @param baseDataType
     * @param baseDataName
     */
    public String mergeBaseData(String baseDataId, String baseDataType, String baseDataName);

    /**
     * 删除
     * @param ids
     */
    public void deleteBaseData(String[] ids);
}
