package com.cxwmpt.demo.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxwmpt.demo.common.result.ResultMessage;
import com.cxwmpt.demo.model.system.SysLog;
import com.cxwmpt.demo.service.api.system.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @program: backend
 * @description
 * @author: YouName
 * @create: 2020-05-19 14:45
 **/
@Controller
public class SysLogController {

    private final SysLogService sysLogService;

    public SysLogController(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    /**
     * 日志列表页面
     * @return
     */
    @com.cxwmpt.demo.annotation.SysLog("打开日志管理窗口")
    @GetMapping("/html/system/syslog/page")
    public String page() {
        return "systemSetup/userCenter/log/Page";
    }

    /**
     *
     * @param params
     * @return
     */
    @com.cxwmpt.demo.annotation.SysLog("分页查询日志管理信息")
    @PostMapping("/api/auth/log/pageList")
    @ResponseBody
    public ResultMessage selectData(@RequestParam Map<String, Object> params) {

        if (params.containsKey("page") && params.containsKey("limit")) {
            PageHelper.startPage(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        }

        QueryWrapper<SysLog> wrapper = new QueryWrapper<>();
        if (params.get("classMethod") != null && !("").equals(params.get("classMethod"))) {
            wrapper.like("class_method", params.get("classMethod"));
        }
        wrapper.orderByDesc("create_date");

        List<SysLog> list = sysLogService.list(wrapper);
        PageInfo<SysLog> info = new PageInfo<>(list);
        return ResultMessage.success(info.getList(), (int) info.getTotal());
    }

    /**
     * 删除日志信息
     * @param ids
     * @return
     */
    @com.cxwmpt.demo.annotation.SysLog("删除日志信息")
    @PostMapping("/api/auth/log/deletes")
    @ResponseBody
    public ResultMessage deletes(@RequestParam("ids[]") List<String> ids) {
        for (String id : ids) {
            sysLogService.removeById(id);
        }
        return ResultMessage.success();
    }


}
