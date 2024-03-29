package cn.wtk.mp.common.base.web;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * AOP https://blog.csdn.net/lmb55/article/details/82470388
 * 打印Web请求
 * @author wtk
 * @date 2021-10-27
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = "mp.common", name = "web-log", havingValue = "true")
public class WebLogAspect {
    /**
     * 声明切点，我这里启动Controller层下的所有类下的方法就可以打印，
     * 可以使用*来代表任意字符，用..来表示任意个参数
     */
    private final String operateLogPoint = "execution(* com.nowander.*.controller.*.*(..))";

    @Pointcut(operateLogPoint)
    public void webLog() {
    }

    //在方法横向的插入到切点方法执行前
    @Before(value = "webLog()")
    public void beforeControll(JoinPoint joinPoint) {
        if (!log.isInfoEnabled()) {
            return;
        }
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Date date = new Date();
        request.setAttribute("requestTime", date);
        // 记录下请求内容
        String msg = "--------新的请求--------\n请求URL: {}\n请求时间：{}\n请求方式 : {}\nIP : {}\n类与方法 : {}\n 参数 : {}";
        msg = MessageFormat.format(msg,  request.getRequestURL(),
                date,
                request.getMethod(),
                request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        log.info(msg);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void afterControl(Object ret) {
        if (!log.isInfoEnabled()) {
            return;
        }
        // 处理完请求，返回内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Date start = (Date) request.getAttribute("requestTime");
        Date date = new Date();
        String pattern = "--------请求结束--------\n请求结束！\n请求耗时：{}ms\n请求URL: {}，请求方式：{}\n请求时间：{}\n完成时间：{}\nIP：{}";
        pattern = MessageFormat.format(pattern,
                date.getTime() - start.getTime(),
                request.getRequestURL(),
                request.getMethod(),
                start,
                date,
                request.getRemoteAddr());
        log.info(pattern);
    }

    @AfterThrowing(pointcut = "webLog()")
    public void afterThrowing() {
        if (!log.isInfoEnabled()) {
            return;
        }
        // 处理完请求，返回内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Date start = (Date) request.getAttribute("requestTime");
        Date date = new Date();
        String pattern = "--------请求失败--------\n请求结束！\n请求耗时：{}ms\n请求URL: {}，请求方式：{}\n请求时间：{}\n完成时间：{}\nIP：{}";
        pattern = MessageFormat.format(pattern,
                date.getTime() - start.getTime(),
                request.getRequestURL(),
                request.getMethod(),
                start,
                date,
                request.getRemoteAddr());
        log.info(pattern);
    }
}
