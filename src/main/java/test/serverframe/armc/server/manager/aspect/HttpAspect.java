/********************************************
 * 切面，日志统一处理
 *
 * @author zwq
 * @create 2018-07-28
 *********************************************/

package test.serverframe.armc.server.manager.aspect;


import test.serverframe.armc.server.manager.common.exception.ExceptionHandle;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {

    private static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @SuppressWarnings("rawtypes")
    @Autowired
    private ExceptionHandle exceptionHandle;

    @Pointcut("execution(public * test.serverframe.armc.server.manager.controller.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        logger.info("url={}", request.getRequestURL());
        //method
        logger.info("method={}", request.getMethod());
        //ip
        logger.info("id={}", request.getRemoteAddr());
        //class_method
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        //args[]
        logger.info("args={}", joinPoint.getArgs());
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            return exceptionHandle.exceptionGet(e);
        }
//        return proceedingJoinPoint.proceed();
    }

    @AfterReturning(pointcut = "log()", returning = "object")//打印输出结果
    public void doAfterReturing(Object object) {
        logger.info("response={}", object.toString());
    }
}
