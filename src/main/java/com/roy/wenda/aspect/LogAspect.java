package com.roy.wenda.aspect;





import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.roy.wenda.*.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        for(Object k:joinPoint.getArgs()){
            sb.append("args:"+String.valueOf(k)+"|");
        }
        logger.info("hello"+sb.toString());
    }

    @After("execution(* com.roy.wenda.controller.*.*(..))")
    public void afterMethod(){
        logger.info("world");
    }
}