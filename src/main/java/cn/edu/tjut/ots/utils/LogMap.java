package cn.edu.tjut.ots.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by KINGBOOK on 2017/3/24.
 */
public class LogMap {
    private static Map map = null;

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        LogMap.map = map;
    }

    public static Collection getValues(){
        return map.values();
    }

    public static Set getKeys(){
        return map.keySet();
    }

    public static String getValue(String key){
        return (String)map.get(key);
    }
}
