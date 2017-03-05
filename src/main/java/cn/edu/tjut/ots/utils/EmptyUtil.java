package cn.edu.tjut.ots.utils;

/**
 * Created by KINGBOOK on 2017/3/5.
 */
public class EmptyUtil {

    /**
     * 判断对象是否为空
     * @param object
     * @return
     */
    public static boolean isObjEmpty(Object object){
        boolean flag = false;
        if(object == null){
            flag = true;
        }
        return flag;
    }
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isFieldEmpty(String str){
        boolean flag = false;
        if(str == null || "".equals(str.trim()) || "null".equals(str) ){
            flag = true;
        }
        return flag;
    }
}
