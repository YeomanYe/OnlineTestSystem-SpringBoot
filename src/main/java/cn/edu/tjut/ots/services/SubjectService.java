package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.Subject;

import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
public interface SubjectService {
    public List<Subject> querySubject();

    public Map addSubject(String subjectId, String subjectType, String subjectName, int subjectScore, String subjectParse, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers);

    /**
     * 更新试题
     * @param subject
     * @param subjectItems
     * @param isExist 判断试题选项添加还是更新的标志位,true更新，false添加
     */
    public Map<String, Object> updateSubject(String subjectId, String subjectType, String subjectName, int subjectScore, String subjectParse, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers);

    /**
     * 根据ID查询试题
     * @param uuid
     * @return
     */
    public Subject querySubjectById(String uuid);

    /**
     * 查询试题简要信息
     * @return
     */
    public List<Object> queryBriefSubject();
    /**
     * 通过ID删除试题
     * @param uuids
     */
    public void deleteSubjectByIds(String[] uuids);
}
