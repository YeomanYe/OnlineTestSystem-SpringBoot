package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.utils.BooleanTypeHandler;
import org.apache.ibatis.annotations.TypeDiscriminator;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
public interface SubjectService {
    public List<Subject> querySubject();

    public void addSubject(Subject subject, List<SubjectItem> subjectItems);

    /**
     * 更新试题
     * @param subject
     * @param subjectItems
     * @param isExist 判断试题选项添加还是更新的标志位,true更新，false添加
     */
    public void updateSubject(Subject subject,List<SubjectItem> subjectItems,List<Boolean> isExist);

    /**
     * 根据ID查询试题
     * @param uuid
     * @return
     */
    public Subject querySubjectById(String uuid);
}
