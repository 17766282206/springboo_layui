package com.cxwmpt.demo.annotation;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxwmpt.demo.common.result.ResultMessage;
import com.cxwmpt.demo.common.util.IpUtil;
import com.cxwmpt.demo.common.util.ObjConvertUtils;
import com.cxwmpt.demo.common.util.ToolUtil;
import com.cxwmpt.demo.model.system.SysDictComment;
import com.cxwmpt.demo.model.system.SysUser;
import com.cxwmpt.demo.service.api.system.SysDictCommentService;
import com.cxwmpt.demo.service.api.system.SysDictService;
import com.cxwmpt.demo.service.api.system.SysLogService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoleilu.hutool.lang.Dict;
import org.apache.commons.lang3.StringUtils;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: backend
 * @description 系统日志注解功能具体实现
 * @author: zhoudi
 * @create: 2020-04-03 09:54
 **/
@Aspect
@Component
public class SysDictAspect {
    private final SysDictCommentService sysDictCommentService;
    private final SysDictService sysDictService;
    public SysDictAspect(SysDictCommentService sysDictCommentService,SysDictService sysDictService) {
        this.sysDictCommentService = sysDictCommentService;
        this.sysDictService=sysDictService;
    }
    /**
     * 字典后缀
     */
    private static final String DICT_TEXT_SUFFIX = "Text";


    /**
     * 切点，切入 controller 子孙包下的所有类前缀为pageList所有方法
     * .*”表示包下的所有类，而“..*”表示包、子孙包下的所有类。
     */
    @Pointcut("execution( * com.cxwmpt.demo.controller..*pageList*(..))")
    public void dict() {

    }

    @Around("dict()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        if(pjp == null){
            return null;
        }
        /**
         * Signature 包含了方法名、申明类型以及地址等信息
         */
        String class_name = pjp.getTarget().getClass().getName();
        String method_name = pjp.getSignature().getName();

        Object result = pjp.proceed();
        if("pageList".equals(method_name)){
            this.parseDictText(result);
        }

        return result;

    }

    private void parseDictText(Object result) {
        if (result instanceof ResultMessage) {
            List<JSONObject> items = new ArrayList<>();
            ResultMessage resultMessage = (ResultMessage) result;
            if (resultMessage.getCount()!=null&&resultMessage.getCount()>0) {
                List<?> list = (List<?>) resultMessage.getData();
                for (Object record : list) {
                    ObjectMapper mapper = new ObjectMapper();
                    String json = "{}";
                    try {
// 解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
                        json = mapper.writeValueAsString(record);
                    } catch (JsonProcessingException e) {
                        System.out.println("Json解析失败：" + e);
                    }
                    JSONObject item = JSONObject.parseObject(json);
// 解决继承实体字段无法翻译问题
                    for (Field field : ObjConvertUtils.getAllFields(record)) {
//解决继承实体字段无法翻译问题
// 如果该属性上面有@Dict注解，则进行翻译
                        if (field.getAnnotation(SysDict.class) != null) {
// 拿到注解的dictDataSource属性的值
                            String dictType = field.getAnnotation(SysDict.class).dictCode();
// 拿到注解的dictText属性的值
                            String text = field.getAnnotation(SysDict.class).dictText();
//获取当前带翻译的值
                            String key = String.valueOf(item.get(field.getName()));
//翻译字典值对应的text值
                            String textValue = translateDictValue(dictType, key);

//如果给了文本名
                            if (!StringUtils.isBlank(text)) {
                                item.put(text, textValue);
                            } else {
// 走默认策略
                                item.put(field.getName() + DICT_TEXT_SUFFIX, textValue);
                            }
                        }
// date类型默认转换string格式化日期
                        if ("java.util.Date".equals(field.getType().getName())
                                && field.getAnnotation(JsonFormat.class) == null
                                && item.get(field.getName()) != null) {
                            SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
                        }
                    }
                    items.add(item);
                }
                resultMessage.setData(items);
            }
        }
    }
    private String translateDictValue(String dictCode, String code) {



     //如果key为空直接返回就好了
        if (ObjConvertUtils.isEmpty(dictCode)||ObjConvertUtils.isEmpty(code)) {
            return null;
        }
        //判断字典是否启动
        QueryWrapper<com.cxwmpt.demo.model.system.SysDict> SysDictwrapper = new QueryWrapper<>();
        SysDictwrapper.eq("code",dictCode);
        SysDictwrapper.eq("status","0");
        SysDictwrapper.eq("del_flag", false);
         int count= sysDictService.count(SysDictwrapper);
         if(count==0){
             return null;
         }
        StringBuffer textValue = new StringBuffer();
          //分割 key 值
        String[] keys = code.split(",");
       //循环 keys 中的所有值
        for (String k : keys) {
            String tmpValue = null;
            System.out.println(" 字典 key : " + k);
            if (k.trim().length() == 0) {
                continue; //跳过循环
            }
            QueryWrapper<SysDictComment> wrapper = new QueryWrapper<>();
            wrapper.eq("dict_code",dictCode);
            wrapper.eq("code",code);
            wrapper.eq("del_flag", false);
            try {
                SysDictComment sysDictComment = sysDictCommentService.getOne(wrapper);
                tmpValue = sysDictComment.getName();
            }catch (Exception ex){
                return null;
            }

            if (tmpValue != null) {
                if (!"".equals(textValue.toString())) {
                    textValue.append(",");
                }
                textValue.append(tmpValue);
            }
        }
        return textValue.toString();
    }
}
