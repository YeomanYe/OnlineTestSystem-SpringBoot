package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    SubjectService subjectServiceImpl;

    @RequestMapping(path="querySubject")
    public String querySubject(HttpServletRequest req){
        List<Subject> subjectList = subjectServiceImpl.querySubject();
        req.setAttribute("subjects",subjectList);
        return "table";
    }
}
