package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.SubjectDao;
import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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

    public List<Subject> querySubject(){
        return subjectDao.querySubject();
    }
}
