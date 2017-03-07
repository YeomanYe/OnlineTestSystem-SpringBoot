package cn.edu.tjut.ots.controller;

import cn.edu.tjut.ots.services.BaseDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by KINGBOOK on 2017/3/7.
 */
@Controller
@RequestMapping("/basedata")
public class BaseDataController {
    @Resource
    BaseDataService baseDataServiceImpl;

    @ResponseBody
    @RequestMapping("queryByType")
    public List queryByType(@RequestParam("baseDataType") String baseDataType){
        return baseDataServiceImpl.queryBaseDataByType(baseDataType);
    }
}
