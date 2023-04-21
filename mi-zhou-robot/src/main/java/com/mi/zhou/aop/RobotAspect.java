package com.mi.zhou.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author miZhou
 * @version 1.0
 * @date 2023/3/28
 */
@Slf4j
@Aspect
@Component
public class RobotAspect {

    @Pointcut("execution(public * net.lz1998.pbbot.bot.ApiSender.*(..))")
    private void pointcut() {
    }

    @Around("pointcut()")
    private Object logHandler(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            log.error("Handling error");
            throw e;
        }
    }

}
