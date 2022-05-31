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
public class RestControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger(RestControllerAspect.class);

    @Autowired
    private ObjectMapper mapper;

    @Pointcut("within(com.project.ZTI.controller..*)" +
    "&& @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void pointcut() { }

    @Before("pointcut()")
    public void logMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getStaticPart().getSignature();
        GetMapping mapping = signature.getMethod().getAnnotation(GetMapping.class);
        Class<?>[] parameters = signature.getParameterTypes();
        try {
            logger.info("CONTROLLER INFO ==> path(s): {}, method(s): {}, arguments: {} ",
                    mapping.value(), "GET", mapper.writeValueAsString(parameters));
        } catch (JsonProcessingException e) {
            logger.error("Error while converting", e);
        }
    }

    @AfterReturning(pointcut = "pointcut()", returning = "entity")
    public void logMethodAfter(JoinPoint joinPoint, ResponseEntity<?> entity) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        GetMapping mapping = signature.getMethod().getAnnotation(GetMapping.class);

        try {
            logger.info("CONTROLLER INFO <== path(s): {}, method(s): {}, retuning: {}",
                    mapping.value(), "GET", mapper.writeValueAsString(entity.getStatusCode()));
        } catch (JsonProcessingException e) {
            logger.error("Error while converting", e);
        }
    }
}
