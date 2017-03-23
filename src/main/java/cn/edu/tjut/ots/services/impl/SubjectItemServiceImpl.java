package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.SubjectItemDao;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.services.SubjectItemService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/2.
 */
@Service
@Transactional
@Scope("singleton")
public class SubjectItemServiceImpl implements SubjectItemService {
    @Resource
    private SubjectItemDao subjectItemDao;
    @Override
    public List<SubjectItem> querySubjectItem(String uuid) {
        return subjectItemDao.querySubjectItem(uuid);
    }
}
