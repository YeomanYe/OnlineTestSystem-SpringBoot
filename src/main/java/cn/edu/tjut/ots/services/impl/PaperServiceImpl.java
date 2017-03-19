package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.PaperDao;
import cn.edu.tjut.ots.po.Paper;
import cn.edu.tjut.ots.services.PaperService;
import cn.edu.tjut.ots.utils.CreateUserBy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
@Service
@Transactional
public class PaperServiceImpl implements PaperService {

    @Resource
    PaperDao paperDao;

    @Override
    public List queryPaper() {
        return paperDao.queryBrifPaper();
    }

    @Override
    public String addPaper(String paperName, String paperType, String paperDesc, int ansTime, int paperScore, int subjectCnt, String[] subjectIds, String username) {
        Paper paper = new Paper();
        paper.setPaperName(paperName);
        paper.setPaperType(paperType);
        paper.setPaperDesc(paperDesc);
        paper.setAnsTime(ansTime);
        paper.setPaperScore(paperScore);
        paper.setSubjectCnt(subjectCnt);
        CreateUserBy.setUser(paper,null,username);
        String paperId = UUID.randomUUID().toString().replace("-","");
        paperDao.addPaper(paper);
        List<Map> maps = new LinkedList();
        for (String subjectId : subjectIds) {
            Map<String,String> map = new HashMap();
            map.put("subjectId",subjectId);
            map.put("paperId",paperId);
            maps.add(map);
        }
        paperDao.addPaperSubject(maps);
        return paperId;
    }
}
