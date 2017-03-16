package cn.edu.tjut.ots.filter;


import cn.edu.tjut.ots.services.SubjectService;
import cn.edu.tjut.ots.utils.EmptyUtil;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by KINGBOOK on 2017/3/16.
 */
@WebFilter(initParams = {
        @WebInitParam(name="excludeStr",value = ".jpg;.png;.js;.css;.ico")},
        filterName = "myFilter",urlPatterns = "/*")
public class MyFilter implements Filter{
    @Resource
    private SubjectService subjectServiceImpl;

    private String[] excludeFilterArr = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeFilterArr = filterConfig.getInitParameter("excludeStr").split(";");
        System.out.println("初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
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
        if(EmptyUtil.isFieldEmpty(username)){
            session.setAttribute("username","admin");
        }
        System.out.println("URI:" + req.getRequestURI());
        System.out.println("URL:" + req.getRequestURL());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
