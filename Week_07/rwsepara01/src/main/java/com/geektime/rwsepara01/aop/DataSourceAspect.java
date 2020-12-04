package com.geektime.rwsepara01.aop;

import com.geektime.rwsepara01.anno.WriteDateSourceSource;
import com.geektime.rwsepara01.constant.DataSourceNameConstants;
import com.geektime.rwsepara01.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class DataSourceAspect implements Ordered {

    @Pointcut("@annotation(com.geektime.rwsepara01.anno.WriteDateSourceSource)")
    public void dataSourcePointCut() {}

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        WriteDateSourceSource writeDateSourceSource = method.getAnnotation(WriteDateSourceSource.class);
        if (writeDateSourceSource == null) {
            DynamicDataSource.setDataSource(DataSourceNameConstants.FIRST);
        } else {
            DynamicDataSource.setDataSource(writeDateSourceSource.name());
        }
        log.debug("set datasource is: ", writeDateSourceSource.name());

        try {
            return joinPoint.proceed();
        }  finally {
            DynamicDataSource.cleanDataSource();
            log.debug("clean DataSource");
        }
    }


    @Override
    public int getOrder() {
        return 1;
    }
}
