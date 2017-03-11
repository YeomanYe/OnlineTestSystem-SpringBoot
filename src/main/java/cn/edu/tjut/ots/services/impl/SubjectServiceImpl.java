package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.SubjectDao;
import cn.edu.tjut.ots.dao.SubjectItemDao;
import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.services.SubjectService;
import cn.edu.tjut.ots.utils.CreateUserBy;
import cn.edu.tjut.ots.utils.EmptyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Resource
    private SubjectDao subjectDao;
    @Resource
    private SubjectItemDao subjectItemDao;

    public List<Subject> querySubject() {
        return subjectDao.querySubject();
    }

    /**
     * 添加试题
     *  @param subjectId
     * @param subjectType
     * @param subjectName
     * @param subjectScore
     * @param subjectParse
     * @param subjectItemIds
     * @param subjectItemNames
     * @param answers
     */
    public Map addSubject(String subjectId, String subjectType, String subjectName, int subjectScore, String subjectParse, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers) {

        //存储保存的UUID用于返回
        Map<String, Object> retMap = new HashMap<>();
        //新建个Subject
        subjectId = UUID.randomUUID().toString().replace("-", "");
        Subject subject = new Subject(subjectId, subjectType, subjectName, subjectScore, subjectParse);
        CreateUserBy.setUser(subject, null, "admin");
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
    public Map<String,Object> updateSubject(String subjectId, String subjectType, String subjectName, int subjectScore, String subjectParse, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers) {
        //存储保存的UUID用于返回
        Map<String, Object> retMap = new HashMap<>();
        //新建个Subject
        Subject subject = new Subject(subjectId, subjectType, subjectName, subjectScore, subjectParse);
        CreateUserBy.setUser(subject, "update", "admin");
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
    public List<Object> queryBriefSubject() {
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
        map.put("subject",detailSubject);
        List items = subjectItemDao.querySubjectItem(uuid);
        map.put("items",items);
        return map;
    }

    @Override
    public String querySubjectType(String uuid) {
        return subjectDao.querySubjectType(uuid);
    }
}
