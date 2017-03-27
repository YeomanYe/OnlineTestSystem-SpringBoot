package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.BaseDataDao;
import cn.edu.tjut.ots.po.BaseData;
import cn.edu.tjut.ots.services.BaseDataService;
import cn.edu.tjut.ots.utils.EmptyUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
@Service
@Transactional
@Scope("singleton")
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

    @Override
    public List<BaseData> queryBaseDataType() {
        return baseDataDao.queryBaseDataType();
    }

    @Override
    public String mergeBaseData(String baseDataId, String baseDataType, String baseDataName) {
        BaseData baseData = new BaseData();
        boolean isUpdate = true;
        if(EmptyUtil.isFieldEmpty(baseDataId)){
            baseDataId = UUID.randomUUID().toString().replace("-", "");
            isUpdate = false;
        }
        baseData.setUuid(baseDataId);
        switch (baseDataType) {
            case "paperType":
                baseData.setTypeName("试卷类型");
                break;
            case "subjectType":
                baseData.setTypeName("试题类型");
                break;
        }
        baseData.setDataType(baseDataType);
        baseData.setName(baseDataName);
        if(isUpdate){
            baseDataDao.updateBaseData(baseData);
        }else{
            baseDataDao.addBaseData(baseData);
        }
        return baseDataId;
    }

    @Override
    public void deleteBaseData(String[] ids) {
        baseDataDao.deleteBaseDataByIds(ids);
    }
}
