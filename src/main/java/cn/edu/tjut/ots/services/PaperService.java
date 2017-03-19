package cn.edu.tjut.ots.services;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
public interface PaperService {
    /**
     * 查询paper列表
     * @return
     */
    public List queryPaper();

    /**
     * 添加试卷
     * @param paperName
     * @param paperType
     * @param paperDesc
     * @param ansTime
     * @param paperScore
     * @param subjectCnt
     * @param subjectIds
     * @return
     */
    public String addPaper(String paperName, String paperType, String paperDesc, int ansTime, int paperScore, int subjectCnt, String[] subjectIds,String username);
}
