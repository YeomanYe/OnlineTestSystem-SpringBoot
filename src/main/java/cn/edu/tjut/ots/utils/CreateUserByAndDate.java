package cn.edu.tjut.ots.utils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by KINGBOOK on 2017/3/5.
 */
public class CreateUserByAndDate {
    /**
     * obj1是要保存的对象
     * obj2是已经保存的对象，用于保持obj1的创建时间、创建用户
     * @param obj1
     * @param obj2
     * @param userBy
     */
    public static void setUserAndDate(Object obj1, Object obj2, String userBy){
        try {
            Method setCreateBy = obj1.getClass().getMethod("setCreateBy", String.class);
            Method setCreateWhen = obj1.getClass().getMethod("setCreateWhen", Date.class);
            Method setUpdateBy = obj1.getClass().getMethod("setUpdateBy", String.class);
            Method setUpdateWhen = obj1.getClass().getMethod("setUpdateWhen", Date.class);
            if(EmptyUtil.isObjEmpty(obj2) ){
                setCreateBy.invoke(obj1, userBy);
                setCreateWhen.invoke(obj1, new Date());
                setUpdateBy.invoke(obj1, userBy);
                setUpdateWhen.invoke(obj1, new Date());
            }else{
                setCreateBy.invoke(obj1, obj2.getClass().getMethod("getCreateBy").invoke(obj2));
                setCreateWhen.invoke(obj1, obj2.getClass().getMethod("getCreateWhen").invoke(obj2));
                setUpdateBy.invoke(obj1, userBy);
                setUpdateWhen.invoke(obj1, new Date());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
