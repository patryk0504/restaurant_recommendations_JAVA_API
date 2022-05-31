package com.project.ZTI.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ServiceAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

    @Autowired
    private ObjectMapper mapper;

    @Pointcut("within(com.project.ZTI.service..*)" )
    public void pointcut() { }

//    @Before("pointcut()")
//    public void logMethod(JoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getStaticPart().getSignature();
//        logger.info("SERVICE INFO ==> method: {}, arguments: {} ",
//                signature.toShortString(), signature.getParameterNames());
//
//    }

    @AfterReturning(pointcut = "pointcut()")
    public void logMethodAfter(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        logger.info("SERVICE INFO <== method: {}, retuning: {}",
                signature.toShortString(), signature.getReturnType());
    }
}
