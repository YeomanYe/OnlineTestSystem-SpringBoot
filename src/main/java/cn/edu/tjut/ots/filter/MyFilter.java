package cn.edu.tjut.ots.filter;


import cn.edu.tjut.ots.po.UserLog;
import cn.edu.tjut.ots.services.UserLogService;
import cn.edu.tjut.ots.services.SubjectService;
import cn.edu.tjut.ots.utils.EmptyUtil;
import cn.edu.tjut.ots.utils.LogMap;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by KINGBOOK on 2017/3/16.
 */
@WebFilter(initParams = {
        @WebInitParam(name="excludeStr",value = ".jpg;.png;.js;.css;.ico")},
        filterName = "myFilter",urlPatterns = "/*")
public class MyFilter implements Filter{
    @Resource
    private SubjectService subjectServiceImpl;

    @Resource
    private UserLogService userLogServiceImpl;

    private String[] excludeFilterArr = null;

    private Logger filterLogger = Logger.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterLogger.debug("过滤器初始化。。。");
        excludeFilterArr = filterConfig.getInitParameter("excludeStr").split(";");
        filterLogger.debug("过滤器初始化完成。。。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        String username = (String)session.getAttribute("username");
        //请求图片js,css等资源直接放行
        for (String s : excludeFilterArr) {
            if(req.getRequestURI().endsWith(s)){
                filterChain.doFilter(servletRequest,servletResponse);
                //请求完资源后直接放行
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
        filterLogger.info("请求的URI:"+req.getRequestURI());
        filterLogger.info("请求的URL:"+req.getRequestURL());
        String operation = LogMap.getValue(req.getRequestURI());
        if(!EmptyUtil.isObjEmpty(operation)){
            UserLog userLog = new UserLog();
            userLog.setIp(getRemoteHost(req));
            userLog.setUuid(UUID.randomUUID().toString().replace("-",""));
            userLog.setUserName(username);
            userLog.setOperation(operation);
            userLogServiceImpl.addLog(userLog);
        }
    }

    /**
     * 获取IP
     * @param request
     * @return
     */
    public String getRemoteHost(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    @Override
    public void destroy() {

    }
}
