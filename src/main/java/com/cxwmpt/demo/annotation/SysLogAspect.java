package com.cxwmpt.demo.annotation;

import com.alibaba.fastjson.JSONObject;

import com.cxwmpt.demo.common.util.IpUtil;
import com.cxwmpt.demo.common.util.ToolUtil;
import com.cxwmpt.demo.service.api.system.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @program: backend
 * @description 系统日志注解功能具体实现
 * @author: zhoudi
 * @create: 2020-04-03 09:54
 **/
@Aspect
@Component
public class SysLogAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Autowired
    private SysLogService sysLogService;

    private com.cxwmpt.demo.model.system.SysLog sysLog = null;

    @Pointcut("@annotation(com.cxwmpt.demo.annotation.SysLog)")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        sysLog = new com.cxwmpt.demo.model.system.SysLog();
        sysLog.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        sysLog.setHttpMethod(request.getMethod());
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object o = args[i];
            if(o instanceof ServletRequest || (o instanceof ServletResponse) || o instanceof MultipartFile){
                args[i] = o.toString();
            }
        }
        String str = JSONObject.toJSONString(args);
        sysLog.setParams(str.length()>5000? JSONObject.toJSONString("请求参数数据过长不与显示"):str);
        String ip = IpUtil.getIpAddr(request);
        if("0.0.0.0".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip) || "127.0.0.1".equals(ip)){
            ip = "127.0.0.1";
        }
        sysLog.setRemoteAddr(ip);
        sysLog.setRequestUri(request.getRequestURL().toString());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog mylog = method.getAnnotation(SysLog.class);
        if(mylog != null){
            //注解上的描述
            sysLog.setTitle(mylog.value());
        }

        Map<String,String> browserMap = ToolUtil.getOsAndBrowserInfo(request);
        sysLog.setBrowser(browserMap.get("os")+"-"+browserMap.get("browser"));

        if(!"127.0.0.1".equals(ip)){

            Map<String,String>  map = ToolUtil.getAddressByIP(ToolUtil.getClientIp(request));

            sysLog.setArea(map.get("area"));
            sysLog.setProvince(map.get("province"));
            sysLog.setCity(map.get("city"));
            sysLog.setIsp(map.get("isp"));
        }
        sysLog.setType(ToolUtil.isAjax(request)?"Ajax请求":"普通请求");

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            sysLog.setException(e.getMessage());
            throw e;
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {

        String retString = JSONObject.toJSONString(ret);
        sysLog.setResponse(retString.length()>5000? JSONObject.toJSONString("请求参数数据过长不与显示"):retString);
        sysLog.setUseTime(System.currentTimeMillis() - startTime.get());
        sysLogService.save(sysLog);
    }

}
