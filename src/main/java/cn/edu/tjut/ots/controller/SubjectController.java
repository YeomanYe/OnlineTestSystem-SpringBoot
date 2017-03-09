package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.services.SubjectItemService;
import cn.edu.tjut.ots.services.SubjectService;
import cn.edu.tjut.ots.utils.EmptyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
     *
     * @param req
     * @return
     */
    @RequestMapping(path = "querySubject")
    public String querySubject(HttpServletRequest req) {
        List<Object> subjectList = subjectServiceImpl.queryBriefSubject();
        req.setAttribute("subjects", subjectList);
        return "teacher/subject_table";
    }

    /**
     * 查询试题所有详细的信息
     *
     * @param uuid
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "querySubjectInfo")
    public List querySubjectInfo(@RequestParam(name = "uuid") String uuid) {
        List<SubjectItem> subjectItems = subjectItemServiceImpl.querySubjectItem(uuid);
        return subjectItems;
    }

    /**
     * 添加试题页
     *
     * @return
     */
    @RequestMapping("addSubjectPage")
    public String addSubjectPage() {
        return "teacher/subject_add";
    }

    /**
     * 添加试题
     *
     * @param req
     * @param answers
     * @return
     */
    @ResponseBody
    @RequestMapping("mergeSubject")
    public Map mergeSubject(
            HttpServletRequest req,
            @RequestParam(value = "answers") boolean[] answers,
            @RequestParam(value = "subjectId") String subjectId,
            @RequestParam(value = "subjectName") String subjectName,
            @RequestParam(value = "subjectType") String subjectType,
            @RequestParam(value = "subjectScore") int subjectScore,
            @RequestParam(value = "subjectParse") String subjectParse) {
        boolean isUpdate = true;
        //组合选项实体为一个list
        List<String> subjectItemNames = new ArrayList<>();
        List<String> subjectItemIds = new ArrayList<>();
        for (int i = 0, len = answers.length; i < len; i++) {
            String subjectItemName = req.getParameter("subjectItem" + i);
            String subjectItemId = req.getParameter("subjectItemId" + i);
            subjectItemIds.add(subjectItemId);
            subjectItemNames.add(subjectItemName);
        }
        //返回map
        Map retMap = null;
        if (EmptyUtil.isFieldEmpty(subjectId)) {
            retMap = subjectServiceImpl.addSubject(subjectId, subjectType, subjectName, subjectScore,
                    subjectParse, subjectItemIds, subjectItemNames, answers);
        } else {
            retMap = subjectServiceImpl.updateSubject(subjectId, subjectType, subjectName, subjectScore,
                    subjectParse, subjectItemIds, subjectItemNames, answers);
        }

        return retMap;
    }

    /**
     * 返回用于更新的试题页面
     *
     * @return
     */
    @RequestMapping("updateSubjectPage")
    public String updateSubjectPage(HttpServletRequest req, @RequestParam("subjectId") String subjectId) {
        Subject subject = subjectServiceImpl.querySubjectById(subjectId);
        req.setAttribute("subject", subject);
        return "teacher/subject_add";
    }

    /**
     * 查询试题项用于更新页面
     *
     * @param subjectId
     * @return
     */
    @ResponseBody
    @RequestMapping("updateSubjectItem")
    public List updateSubjectItem(@RequestParam("subjectId") String subjectId) {
        return subjectItemServiceImpl.querySubjectItem(subjectId);
    }
    @ResponseBody
    @RequestMapping("deleteSubject")
    public boolean deleteSubjectById(@RequestParam("subjectIds") String[] subjectIds){
        boolean bool = false;
        subjectServiceImpl.deleteSubjectByIds(subjectIds);
        bool = true;
        return bool;
    }
}
