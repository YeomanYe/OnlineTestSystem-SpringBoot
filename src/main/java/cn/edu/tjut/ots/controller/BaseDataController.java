package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.BaseDataService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
@Controller
@Scope("prototype")
@RequestMapping("/baseData")
public class BaseDataController {
    @Resource
    BaseDataService baseDataServiceImpl;

    /**
     * 根据数据类型查询基础数据信息
     * @param baseDataType
     * @return
     */
    @ResponseBody
    @RequestMapping("queryByType")
    public List queryByType(@RequestParam("dataType") String baseDataType){
        return baseDataServiceImpl.queryBaseDataByType(baseDataType);
    }

    /**
     * 返回基础数据列表页
     * @return
     */
    @RequestMapping("listBaseDataPage")
    public String getBaseDataPage(HttpServletRequest req){
        return "teacher/basedata_list";
    }

    @ResponseBody
    @RequestMapping("refreshBaseData")
    public List refreshBaseData(){
        return baseDataServiceImpl.queryBaseData();
    }
}
