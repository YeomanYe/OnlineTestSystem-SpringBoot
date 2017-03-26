package cn.edu.tjut.ots.listener;

import cn.edu.tjut.ots.utils.LogMap;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Created by KINGBOOK on 2017/3/24.
 */
public class ApplicationListener implements ServletContextListener {
    Logger listenLogger = Logger.getLogger(this.getClass());
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        listenLogger.debug("上下文监听器初始化。。。");
        //将log映射文件中的键值对添加到LogMap中
        FileInputStream fis = null;
        File f = new File("src/main/resources/userLog_map.properties");
        InputStreamReader isr = null;
        Properties p = new Properties();
        try {
            fis = new FileInputStream(f);
            isr = new InputStreamReader(fis,"utf-8");
            p.load(isr);
            Set nameSet =  p.stringPropertyNames();
            Iterator iterator = nameSet.iterator();
            Map<String,String> map = new HashMap();
            listenLogger.debug("初始化日志映射文件");
            while(iterator.hasNext()){
                String key = (String)iterator.next();
                map.put(key,p.getProperty(key));
                listenLogger.info("url:"+key+",name:"+p.getProperty(key));
            }
            listenLogger.debug("日志映射文件初始化完成");
            LogMap.setMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listenLogger.debug("上下文监听器初始化完成。。。。");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
