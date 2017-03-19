package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.PaperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
@Controller
@RequestMapping("/paper")
public class PaperController {
    @Resource
    PaperService paperServiceImpl;

    @RequestMapping("listPaperPage")
    public String getPaperListPage(HttpServletRequest req){
        List papers = paperServiceImpl.queryPaper();
        req.setAttribute("papers",papers);
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
        }
        return retStr;
    }
}
