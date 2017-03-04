package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.services.SubjectItemService;
import cn.edu.tjut.ots.services.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    private SubjectService subjectServiceImpl;

    @Resource
    private SubjectItemService subjectItemServiceImpl;

    /**
     * 查询所有试题
     * @param req
     * @return
     */
    @RequestMapping(path="querySubject")
    public String querySubject(HttpServletRequest req){
        List<Subject> subjectList = subjectServiceImpl.querySubject();
        req.setAttribute("subjects",subjectList);
        return "teacher/subject_table";
    }

    /**
     * 查询试题所有详细的信息
     * @param uuid
     * @return
     */
    @ResponseBody
    @RequestMapping(path="querySubjectInfo")
    public List querySubjectInfo(@RequestParam(name = "uuid") String uuid){
        List<SubjectItem> subjectItems = subjectItemServiceImpl.querySubjectItem(uuid);
        return subjectItems;
    }
}
