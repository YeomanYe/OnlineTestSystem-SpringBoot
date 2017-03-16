package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.services.SubjectItemService;
import cn.edu.tjut.ots.services.SubjectService;
import cn.edu.tjut.ots.utils.EmptyUtil;
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
    public boolean deleteSubjectById(@RequestParam("subjectIds") String[] subjectIds){
        boolean bool = false;
        subjectServiceImpl.deleteSubjectByIds(subjectIds);
        bool = true;
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

    /**
     * 查询日期为了统计
     * @return
     */
    @ResponseBody
    @RequestMapping("queryTypeForSta")
    public List queryTypeForSta(){
        return subjectServiceImpl.queryTypeForSta();
    }

    /**
     * 查询分数为了统计
     * @return
     */
    @ResponseBody
    @RequestMapping("queryScoreForSta")
    public List queryScoreForSta(){
        return subjectServiceImpl.queryScoreForSTa();
    }

    /**
     * 查询更新者为了统计
     * @return
     */
    @ResponseBody
    @RequestMapping("queryUpdateByForSta")
    public List queryUpdateByForSta(){
        return subjectServiceImpl.queryUpdateByForSta();
    }

    /**
     * 查询更新时间为了统计
     * @return
     */
    @ResponseBody
    @RequestMapping("queryUpdateWhenForSta")
    public List queryUpdateWhenForSta(){
        return subjectServiceImpl.queryUpdateWhenForSta();
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
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=subject.xlsx");
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            subjectServiceImpl.exportExcel(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
