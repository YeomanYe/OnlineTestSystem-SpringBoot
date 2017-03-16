package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.PaperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
}
