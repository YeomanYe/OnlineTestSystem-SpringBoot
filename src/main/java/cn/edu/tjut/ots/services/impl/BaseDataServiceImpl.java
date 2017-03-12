package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.BaseDataDao;
import cn.edu.tjut.ots.po.BaseData;
import cn.edu.tjut.ots.services.BaseDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
@Service
public class BaseDataServiceImpl implements BaseDataService {
    @Resource
    BaseDataDao baseDataDao;
    @Override
    public List<BaseData> queryBaseDataByType(String dataType) {
        return baseDataDao.queryBaseDataByType(dataType);
    }

    @Override
    public List<BaseData> queryBaseData() {
        return baseDataDao.queryBaseDataList();
    }
}
