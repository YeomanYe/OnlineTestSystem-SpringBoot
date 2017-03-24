package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.BaseDataDao;
import cn.edu.tjut.ots.dao.ImageDao;
import cn.edu.tjut.ots.dao.SubjectDao;
import cn.edu.tjut.ots.dao.SubjectItemDao;
import cn.edu.tjut.ots.po.Image;
import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.services.SubjectService;
import cn.edu.tjut.ots.utils.CreateUserBy;
import cn.edu.tjut.ots.utils.EmptyUtil;
import cn.edu.tjut.ots.utils.ExcelUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
@Service
@Transactional
@Scope("singleton")
public class SubjectServiceImpl implements SubjectService {
    @Resource
    private SubjectDao subjectDao;
    @Resource
    private SubjectItemDao subjectItemDao;
    @Resource
    private BaseDataDao baseDataDao;
    @Resource
    private ImageDao imageDao;

    public List<Subject> querySubject() {
        return subjectDao.querySubject();
    }

    /**
     * 添加试题
     *
     * @param subjectId
     * @param subjectType
     * @param subjectName
     * @param subjectScore
     * @param subjectParse
     * @param subjectItemIds
     * @param subjectItemNames
     * @param answers
     * @param username
     */
    public Map addSubject(String subjectId, String subjectType, String subjectName, int subjectScore, String subjectParse, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers, String username) {

        //存储保存的UUID用于返回
        Map<String, Object> retMap = new HashMap<>();
        //新建个Subject
        subjectId = UUID.randomUUID().toString().replace("-", "");
        Subject subject = new Subject(subjectId, subjectType, subjectName, subjectScore, subjectParse);
        CreateUserBy.setUser(subject, null, username);
        //保存试题ID
        retMap.put("subjectId", subjectId);

        List<SubjectItem> subjectItems = createSubjectItems(subjectId, subjectItemIds, subjectItemNames, answers, retMap);
        //插入试题
        subjectDao.insertSubject(subject);
        //插入试题项
        for (SubjectItem subjectItem : subjectItems) {
            subjectItemDao.insertSubjectItem(subjectItem);
        }
        return retMap;
    }

    private List<SubjectItem> createSubjectItems(String subjectId, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers, Map<String, Object> retMap) {
        int len = answers.length;
        List<SubjectItem> subjectItems = new ArrayList<>();
        retMap.put("subjectItemIds", subjectItemIds);
        List<Boolean> isExists = new ArrayList<Boolean>();
        for (int i = 0; i < len; i++) {
            boolean isAnswer = answers[i];
            String subjectItemId = subjectItemIds.get(i);
            String subjectItemName = subjectItemNames.get(i);
            //如果不存在ID则创建。
            if (EmptyUtil.isFieldEmpty(subjectItemId)) {
                subjectItemId = UUID.randomUUID().toString().replace("-", "");
            }
            SubjectItem subjectItem = new SubjectItem(subjectItemId, subjectItemName, subjectId, isAnswer);
            subjectItems.add(subjectItem);
        }
        return subjectItems;
    }

    @Override
    public Map<String, Object> updateSubject(String subjectId, String subjectType, String subjectName, int subjectScore, String subjectParse, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers, String username) {
        //存储保存的UUID用于返回
        Map<String, Object> retMap = new HashMap<>();
        //新建个Subject
        Subject subject = new Subject(subjectId, subjectType, subjectName, subjectScore, subjectParse);
        CreateUserBy.setUser(subject, "update", username);
        //保存试题ID
        retMap.put("subjectId", subjectId);

        List<SubjectItem> subjectItems = createSubjectItems(subjectId, subjectItemIds, subjectItemNames, answers, retMap);
        //插入试题
        subjectDao.updateSubject(subject);
        //删除试题项
        subjectItemDao.deleteSubjectItemBySubjectId(subjectId);
        //插入试题项
        for (SubjectItem subjectItem : subjectItems) {
            subjectItemDao.insertSubjectItem(subjectItem);
        }
        return retMap;
    }

    @Override
    public Subject querySubjectById(String uuid) {
        return subjectDao.querySubjectById(uuid);
    }

    @Override
    public List<Subject> queryBriefSubject() {
        return subjectDao.queryBriefSubject();
    }

    @Override
    public void deleteSubjectByIds(String[] uuids) {
        subjectDao.deleteSubjectByIds(uuids);
    }

    @Override
    public Map querySubject4Update(String uuid) {
        Object detailSubject = subjectDao.queryDetailSubject(uuid);
        Map map = new HashMap();
        map.put("subject", detailSubject);
        List items = subjectItemDao.querySubjectItem(uuid);
        map.put("items", items);
        return map;
    }

    @Override
    public String querySubjectType(String uuid) {
        return subjectDao.querySubjectType(uuid);
    }

    @Override
    public void imporExcel(InputStream is, String username) {
        List<Map<String, Object>> maps = null;
        try {
            maps = ExcelUtil.excelImport(Subject.class, is);
            for (Map<String, Object> map : maps) {
                Subject subject = (Subject) map.get("subject");
                List<SubjectItem> items = (List) map.get("subjectItemList");
                String typeId = baseDataDao.querySubjectTypeIdByName(subject.getSubjectType());
                subject.setSubjectType(typeId);
                CreateUserBy.setUser(subject, null, username);
                subjectDao.insertSubject(subject);
                subjectItemDao.insertBatchItem(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportExcel(OutputStream os) {
        List<Subject> subjects = subjectDao.queryDetailSubjectList();
        List<Map<String, Object>> maps = new LinkedList<>();
        for (Subject subject : subjects) {
            List<SubjectItem> items = subjectItemDao.querySubjectItem(subject.getUuid());
            Map<String, Object> map = new HashMap<>();
            map.put("subject", subject);
            map.put("subjectItemList", items);
            maps.add(map);
        }
        try {
            ExcelUtil.excelExport(Subject.class, os, maps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List queryForSta(String type) {
        List retList = null;
        switch (type) {
            case "updateBy":
                retList = subjectDao.queryUpdateByForSta();
                break;
            case "updateWhen":
                retList = subjectDao.queryUpdateWhenForSta();
                break;
            case "type":
                retList = subjectDao.queryTypeForSta();
                break;
            case "score":
                retList = subjectDao.queryScoreForSta();
                break;
        }
        return retList;
    }

    @Override
    public Map querySubject4Show(String subjectId) {
        if(EmptyUtil.isFieldEmpty(subjectId)) return null;
        Map<String,Object> map = new HashMap();
        Subject subject = subjectDao.querySubjectById(subjectId);
        Image image = null;
        if (!EmptyUtil.isFieldEmpty(subject.getImgId()))
            image = imageDao.queryImageById(subject.getImgId()).get(0);
        //存入图片相对路径
        if(!EmptyUtil.isObjEmpty(image)) map.put("src",image.getRelPath());
        map.put("subjectName",subject.getSubjectName());
        map.put("subjectParse",subject.getSubjectParse());
        map.put("subjectItems",subjectItemDao.querySubjectItem(subjectId));
        return map;
    }
}
