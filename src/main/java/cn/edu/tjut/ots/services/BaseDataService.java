package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.BaseData;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
public interface BaseDataService {
    public List<BaseData> queryBaseDataByType(String dataType);
}
