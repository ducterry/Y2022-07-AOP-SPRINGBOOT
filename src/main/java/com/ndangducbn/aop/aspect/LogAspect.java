package com.ndangducbn.aop.aspect;

import com.ndangducbn.aop.annotation.Log;
import com.ndangducbn.aop.dao.SysLogDao;
import com.ndangducbn.aop.model.SysLog;
import com.ndangducbn.aop.utils.HttpContextUtils;
import com.ndangducbn.aop.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAspect {
    private final SysLogDao sysLogDao;

    public LogAspect(SysLogDao sysLogDao) {
        this.sysLogDao = sysLogDao;
    }

    @Pointcut("@annotation(com.ndangducbn.aop.annotation.Log)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint point) {
        long beginTime = System.currentTimeMillis();
        try {
            point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - beginTime;
        saveLog(point, time);
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        SysLog sysLog = new SysLog();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            sysLog.setOperation(logAnnotation.value());
        }
        String methodString = new StringBuilder()
                .append(joinPoint.getTarget().getClass().getName())
                .append(".")
                .append(signature.getName())
                .append("()")
                .toString();
        sysLog.setMethod(methodString);


        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer variable = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = variable.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            sysLog.setParams(params);
        }
        sysLog.setIp(IPUtils.getIpAddress(HttpContextUtils.getHttpServletRequest()));
        sysLog.setUsername("duc-terry");
        sysLog.setTime((int) time);
        sysLog.setCreateTime(new Date());
        this.sysLogDao.saveSysLog(sysLog);
    }
}
