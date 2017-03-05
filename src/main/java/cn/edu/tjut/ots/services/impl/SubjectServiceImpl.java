package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.SubjectDao;
import cn.edu.tjut.ots.dao.SubjectItemDao;
import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.services.SubjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Resource
    private SubjectDao subjectDao;
    @Resource
    private SubjectItemDao subjectItemDao;

    public List<Subject> querySubject(){
        return subjectDao.querySubject();
    }

    /**
     * 添加试题
     */
    public void addSubject(Subject subject, List<SubjectItem> subjectItems){
        subjectDao.insertSubject(subject);
        for (SubjectItem subjectItem : subjectItems) {
            subjectItemDao.insertSubjectItem(subjectItem);
        }
    }

    @Override
    public void updateSubject(Subject subject, List<SubjectItem> subjectItems, List<Boolean> isExists) {
        subjectDao.updateSubject(subject);
        int i = 0;
        for (Boolean isExist : isExists) {
            if(isExist){
                subjectItemDao.updateSubjectItem(subjectItems.get(i));
            }else{
                subjectItemDao.insertSubjectItem(subjectItems.get(i));
            }
            i++;
        }
    }

    @Override
    public Subject querySubjectById(String uuid) {
        return subjectDao.querySubjectById(uuid);
    }
}
