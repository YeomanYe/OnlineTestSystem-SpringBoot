package cn.edu.tjut.ots.utils;

import java.lang.reflect.Method;

/**
 * Created by KINGBOOK on 2017/3/5.
 */
public class CreateUserBy {
    /**
     * obj1是要保存的对象
     * obj2是已经保存的对象，用于保持obj1的创建时间、创建用户
     * @param obj1
     * @param obj2
     * @param userBy
     */
    public static void setUser(Object obj1, Object obj2, String userBy){
        try {
            Method setCreateBy = obj1.getClass().getMethod("setCreateBy", String.class);
            Method setUpdateBy = obj1.getClass().getMethod("setUpdateBy", String.class);
            if(EmptyUtil.isObjEmpty(obj2) ){
                setCreateBy.invoke(obj1, userBy);
                setUpdateBy.invoke(obj1, userBy);
            }else{
                setUpdateBy.invoke(obj1, userBy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
