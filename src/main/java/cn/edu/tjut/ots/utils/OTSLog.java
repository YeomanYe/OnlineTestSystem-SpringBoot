package cn.edu.tjut.ots.utils;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class OTSLog {
    Logger errorLog = Logger.getLogger(this.getClass());
    Logger contextLog = Logger.getLogger("operator");
    @Pointcut("execution (* cn.edu.tjut.ots..*.*(..))")
    private void pointCut() {
    }
    /**
     * 获取异常并计入日志
     * @param e 异常
     */
    @AfterThrowing(pointcut="pointCut()", throwing="e")
    public void throwAdvice(Exception e) {
        String newline = System.getProperty("line.separator");
        StackTraceElement[] elements = e.getStackTrace();
        StringBuffer errMsg = new StringBuffer();
        errMsg.append(e.getMessage());
        errMsg.append(newline);
        for (StackTraceElement element : elements) {
            errMsg.append(element.toString());
            errMsg.append(newline);
        }
        errorLog.error(errMsg);
    }

    /**
     * 记录操作信息
     * @param pjp
     * @return 正在执行的对象
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getName();
        String method = pjp.getSignature().getName();
        contextLog.info("开始调用：" + className + "中的：" + method + "方法");
        Object result = pjp.proceed();
        contextLog.info("结束调用：" + className + "中的：" + method + "方法");
        return result;
    }
}
