package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.SubjectItemService;
import cn.edu.tjut.ots.services.SubjectService;
import cn.edu.tjut.ots.utils.EmptyUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by KINGBOOK on 2017/3/1.
 */
@Controller
@Scope("prototype")
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    private SubjectService subjectServiceImpl;

    @Resource
    private SubjectItemService subjectItemServiceImpl;

    /**
     * 查询试题页
     *
     * @return
     */
    @RequestMapping(path = "listSubjectPage")
    public String getSubjectListPage() {
        return "teacher/subject_list";
    }

    /**
     * 查询试题所有详细的信息
     *
     * @param uuid
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "querySubjectInfo")
    public Map querySubjectInfo(@RequestParam(name = "uuid") String uuid) {
        return subjectServiceImpl.querySubject4Show(uuid);
    }

    /**
     * 添加试题页
     *
     * @return
     */
    @RequestMapping("addSubjectPage")
    public String getSubjectAddPage() {
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
            @RequestParam(value = "subjectParse") String subjectParse,
            HttpSession session) {
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
                    subjectParse, subjectItemIds, subjectItemNames, answers, (String)session.getAttribute("username"));
        } else {
            retMap = subjectServiceImpl.updateSubject(subjectId, subjectType, subjectName, subjectScore,
                    subjectParse, subjectItemIds, subjectItemNames, answers,(String) session.getAttribute("username"));
        }

        return retMap;
    }

    /**
     * 查询试题用于更新页面
     *
     * @param subjectId
     * @return
     */
    @ResponseBody
    @RequestMapping("querySubject4Update")
    public Map updateSubjectItem(@RequestParam("subjectId") String subjectId) {
        return subjectServiceImpl.querySubject4Update(subjectId);
    }

    /**
     * 删除试题
     * @param subjectIds
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteSubject")
    public boolean deleteSubjectById(
            @RequestParam("subjectIds") String[] subjectIds,
            HttpSession session){
        boolean bool = subjectServiceImpl.deleteSubjectByIds(subjectIds, session.getServletContext().getRealPath(""));
        return bool;
    }

    /**
     * 刷新试题
     * @return
     */
    @ResponseBody
    @RequestMapping("refreshSubject")
    public List refreshSubject(){
        List subjects = subjectServiceImpl.queryBriefSubject();
        return subjects;
    }

    @ResponseBody
    @RequestMapping("queryForSta")
    public List queryForSta(@RequestParam("type")String type){
        return subjectServiceImpl.queryForSta(type);
    }

    @ResponseBody
    @RequestMapping("uploadExcel")
    public boolean excelImport(@RequestParam("subjectImportExcel")MultipartFile file,
                               HttpSession session){
        boolean flag = false;
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        subjectServiceImpl.imporExcel(is, (String)session.getAttribute("username"));
        flag = true;
        return flag;
    }

    @RequestMapping("downloadExcel")
    public void excelExport(HttpServletResponse response){
        response.setHeader("Content-Disposition", "attachment; filename=subject.xlsx");
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            subjectServiceImpl.exportExcel(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
