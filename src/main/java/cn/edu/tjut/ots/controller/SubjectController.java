package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.po.Subject;
import cn.edu.tjut.ots.po.SubjectItem;
import cn.edu.tjut.ots.services.SubjectItemService;
import cn.edu.tjut.ots.services.SubjectService;
import cn.edu.tjut.ots.utils.CreateUserByAndDate;
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

    /**
     * 添加试题页
     * @return
     */
    @RequestMapping("addSubjectPage")
    public String addSubjectPage(){
        return "teacher/subject_add";
    }

    /**
     * 添加试题
     * @param req
     * @param answers
     * @return
     */
    @ResponseBody
    @RequestMapping("addSubject")
    public Map addSubject(
            HttpServletRequest req,
            @RequestParam(value="answers")boolean[] answers,
            @RequestParam(value="subjectId")String subjectId,
            @RequestParam(value="subjectName")String subjectName,
            @RequestParam(value="subjectType")String subjectType,
            @RequestParam(value="subjectScore")int subjectScore,
            @RequestParam(value="subjectParse")String subjectParse){
        boolean isUpdate = true;
        //存储保存的UUID用于返回
        Map<String,Object> retMap = new HashMap<>();
        //如果不存在，则新建个Subject
        if(EmptyUtil.isFieldEmpty(subjectId)){
            subjectId = UUID.randomUUID().toString().replace("-","");
            isUpdate = false;
        }
        Subject subject = new Subject(subjectId,subjectType,subjectName,subjectScore,subjectParse);
        if(isUpdate){
            Subject subject1 = subjectServiceImpl.querySubjectById(subjectId);
            CreateUserByAndDate.setUserAndDate(subject,subject1,"admin");
        }else{
            CreateUserByAndDate.setUserAndDate(subject,null,"admin");
        }
        //保存试题ID
        retMap.put("subjectId",subjectId);

        int len = answers.length;
        List<SubjectItem> subjectItems = new ArrayList<>();
        //用于保存试题项ID
        List<String> subjectItemIds = new ArrayList<>();
        retMap.put("subjectItemIds",subjectItemIds);
        List<Boolean> isExists = new ArrayList<Boolean>();
        for(int i=0;i<len;i++) {
            String subjectItemName = req.getParameter("subjectItem" + i);
            String subjectItemId = req.getParameter("subjectItemId" + i);
            boolean isAnswer = answers[i];
            //如果不存在ID则创建。
            if (EmptyUtil.isFieldEmpty(subjectItemId)) {
                subjectItemId = UUID.randomUUID().toString().replace("-","");
                isExists.add(new Boolean(false));
            }else {
                isExists.add(new Boolean(true));
            }
            SubjectItem subjectItem = new SubjectItem(subjectItemId, subjectItemName, subjectId, isAnswer);
            subjectItems.add(subjectItem);
            //保存试题项ID
            subjectItemIds.add(subjectItemId);
        }
        if(isUpdate){
            subjectServiceImpl.updateSubject(subject,subjectItems,isExists);
        }else{
            subjectServiceImpl.addSubject(subject,subjectItems);
        }
        return retMap;
    }
}
