package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.Subject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
public interface SubjectService {
    /**
     * 查询试题列表
     * @return
     */
    public List<Subject> querySubject();

    public Map addSubject(String subjectId, String subjectType, String subjectName, int subjectScore, String subjectParse, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers, String username);

    /**
     * 更新试题
     * @param subjectId
     * @param subjectType
     * @param subjectName
     * @param subjectScore
     * @param subjectParse
     * @param subjectItemIds
     * @param subjectItemNames
     * @param answers
     * @param username
     * @return
     */
    public Map<String, Object> updateSubject(String subjectId, String subjectType, String subjectName, int subjectScore, String subjectParse, List<String> subjectItemIds, List<String> subjectItemNames, boolean[] answers, String username);

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
    public List<Subject> queryBriefSubject();
    /**
     * 通过ID删除试题
     * @param uuids
     * @param realPath
     */
    public boolean deleteSubjectByIds(String[] uuids, String realPath);

    /**
     * 查询试题为了更新
     * @param uuid
     * @return
     */
    public Map querySubject4Update(String uuid);

    /**
     * 通过ID查询试题类型
     * @param uuid
     * @return
     */
    public String querySubjectType(String uuid);

    /**
     * 导入Excel
     */
    public void imporExcel(InputStream is, String username);

    /**
     * 导出Excel
     * @param os
     */
    public void exportExcel(OutputStream os);

    /**
     * 查询信息为了统计
     * @param type
     * @return
     */
    public List queryForSta(String type);

    /**
     * 查询试题用于展示
     * @param subjectId
     * @return
     */
    public Map querySubject4Show(String subjectId);
}
