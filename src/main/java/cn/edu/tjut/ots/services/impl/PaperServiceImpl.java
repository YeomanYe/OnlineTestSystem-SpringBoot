package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.PaperDao;
import cn.edu.tjut.ots.po.Paper;
import cn.edu.tjut.ots.services.PaperService;
import cn.edu.tjut.ots.utils.CreateUserBy;
import cn.edu.tjut.ots.utils.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
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
        paper.setUuid(paperId);
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

    @Override
    public String updatePaper(String paperId, String paperName, String paperType, String paperDesc, int ansTime, int paperScore, int subjectCnt, String[] subjectIds, String username) {
        Paper paper = new Paper();
        paper.setPaperName(paperName);
        paper.setPaperType(paperType);
        paper.setPaperDesc(paperDesc);
        paper.setAnsTime(ansTime);
        paper.setPaperScore(paperScore);
        paper.setSubjectCnt(subjectCnt);
        paper.setUuid(paperId);
        CreateUserBy.setUser(paper,"update",username);
        paperDao.updatePaper(paper);
        paperDao.deleteSubject(paperId);
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

    @Override
    public void deletePaperByIds(String[] paperIds) {
        paperDao.deletePaperByIds(paperIds);
    }

    @Override
    public Map queryPaper4Update(String paperId) {
        Map<String,Object> maps = new HashMap();
        List<String> subjectIds = paperDao.querySubjectIdsByPaperId(paperId);
        Paper paper = paperDao.queryPaperById(paperId);
        maps.put("subjectIds",subjectIds);
        maps.put("paper",paper);
        return maps;
    }

    @Override
    public List queryForSta(String type) {
        List list = null;
        switch (type){
            case "type":list = paperDao.queryTypeForSta();break;
            case "updateWhen":list = paperDao.queryUpdateWhenForSta();break;
            case "ansTime":list = paperDao.queryAnsTimeForSta();break;
            case "subjectCnt":list = paperDao.querySubjectCntForSta();break;
        }
        return list;
    }

    @Override
    public void exportPaper(OutputStream os) {
        List<Paper> papers = paperDao.queryDetailPaper();
        try {
            ExcelUtil.excelExport(Paper.class,os,papers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
