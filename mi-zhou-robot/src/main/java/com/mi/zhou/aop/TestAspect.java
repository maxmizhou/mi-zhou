package com.mi.zhou.aop;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Objects;
import java.util.UUID;


/**
 * @author miZhou
 * @version 1.0
 * @date 2023/5/8
 */
@Aspect
@Component
public class TestAspect {
    private final static Logger log = LoggerFactory.getLogger(TestAspect.class);


    @Pointcut("execution(* com.mi.zhou.controller.*.*(..))")
    public void cutMethod() {
    }


    @Around("cutMethod()")
    public Object around(ProceedingJoinPoint point) {
        String traceId = UUID.randomUUID().toString();
        Object result = null;
        String number = null;
        try {
            number = getNumber(point);
            log.info("traceId:{},number:{},目标方法所属类的类名:{}", traceId, number, point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
            result = point.proceed();
        } catch (Throwable e) {
            log.error("traceId:{},number:{},异常信息:{}", traceId, number, e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("traceId:{},number:{},返回结果:{}", traceId, number, JSON.toJSONString(result));
        return result;
    }

    private String getNumber(ProceedingJoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        //1.header中获取参数
        String number = request.getHeader("number");
        if (StringUtils.isNotBlank(number)) {
            return number;
        }
        //2.请求url中获取参数
        number = request.getParameter("number");
        if (StringUtils.isNotBlank(number)) {
            return number;
        }
        //3.body 中获取
        Object[] args = point.getArgs();
        for (Object arg : args) {
            Object objNumber = JSON.parseObject(JSON.toJSONString(arg)).get("number");
            if (objNumber != null) {
                return objNumber.toString();
            }
        }
        return null;
    }
}
