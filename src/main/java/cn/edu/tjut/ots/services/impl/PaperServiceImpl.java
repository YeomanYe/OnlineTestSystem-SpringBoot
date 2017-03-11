package cn.edu.tjut.ots.services.impl;

import cn.edu.tjut.ots.dao.PaperDao;
import cn.edu.tjut.ots.services.PaperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/11.
 */
@Service
public class PaperServiceImpl implements PaperService {

    @Resource
    PaperDao paperDao;

    @Override
    public List queryPaper() {
        return paperDao.queryBrifPaper();
    }
}
