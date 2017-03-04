package cn.edu.tjut.ots.services;

import cn.edu.tjut.ots.po.SubjectItem;

import java.util.List;

/**
 * Created by KINGBOOK on 2017/3/2.
 */
public interface SubjectItemService {
    public List<SubjectItem> querySubjectItem(String uuid);
}
