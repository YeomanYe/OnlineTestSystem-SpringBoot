package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.PaperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
@Controller
@RequestMapping("/paper")
public class PaperController {
    @Resource
    PaperService paperServiceImpl;

    @RequestMapping("listPaperPage")
    public String getPaperListPage(){
        return "teacher/paper_list";
    }

    @RequestMapping("addPaperPage")
    public String getPaperAddPage(){
        return "teacher/paper_add";
    }

    @ResponseBody
    @RequestMapping("mergePaper")
    public String mergePaper(
            @RequestParam("paperId")String paperId,
            @RequestParam("paperName")String paperName,
            @RequestParam("paperType")String paperType,
            @RequestParam("paperDesc")String paperDesc,
            @RequestParam("ansTime")int ansTime,
            @RequestParam("paperScore")int paperScore,
            @RequestParam("subjectCnt")int subjectCnt,
            @RequestParam("subjectIds")String[] subjectIds,
            HttpSession session){
        String retStr = "";
        if(paperId == null || paperId.equals("")){
            retStr = paperServiceImpl.addPaper(paperName,paperType,paperDesc,
                    ansTime,paperScore,subjectCnt,subjectIds,(String)session.getAttribute("username"));
        }else{
            retStr = paperServiceImpl.updatePaper(paperId,paperName,paperType,paperDesc,
                    ansTime,paperScore,subjectCnt,subjectIds,(String)session.getAttribute("username"));
        }
        return retStr;
    }


    @ResponseBody
    @RequestMapping("refreshPaper")
    public List refreshPaper(){
        return paperServiceImpl.queryPaper();
    }

    @ResponseBody
    @RequestMapping("deletePaper")
    public boolean deletePaper(@RequestParam("paperIds") String[] paperIds){
        boolean bool = false;
        paperServiceImpl.deletePaperByIds(paperIds);
        bool = true;
        return bool;
    }

    @ResponseBody
    @RequestMapping("queryPaper4Update")
    public Map queryPaper4Update(@RequestParam("paperId")String paperId){
        return paperServiceImpl.queryPaper4Update(paperId);
    }

    @ResponseBody
    @RequestMapping("queryForSta")
    public List queryForSta(@RequestParam("type") String type){
        return paperServiceImpl.queryForSta(type);
    }

    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse rep){
        rep.setHeader("Content-Disposition", "attachment; filename=paper.xlsx");
        rep.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream os = null;
        try {
            os = rep.getOutputStream();
            paperServiceImpl.exportPaper(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
