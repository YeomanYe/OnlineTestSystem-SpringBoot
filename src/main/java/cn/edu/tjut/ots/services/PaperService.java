package cn.edu.tjut.ots.services;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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

    /**
     * 更新试卷
     * @param paperId
     * @param paperName
     * @param paperType
     * @param paperDesc
     * @param ansTime
     * @param paperScore
     * @param subjectCnt
     * @param subjectIds
     * @param username
     * @return
     */
    public String updatePaper(String paperId,String paperName, String paperType, String paperDesc, int ansTime, int paperScore, int subjectCnt, String[] subjectIds,String username);

    /**
     * 通过id批量删除试题
     * @param paperIds
     */
    public void deletePaperByIds(String[] paperIds);

    /**
     * 查询试卷为了更新
     * @param paperId
     * @return
     */
    public Map queryPaper4Update(String paperId);

    /**
     * 查询为了统计
     * @param type
     * @return
     */
    public List queryForSta(String type);
}
