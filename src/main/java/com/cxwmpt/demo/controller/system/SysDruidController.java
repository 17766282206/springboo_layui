package com.cxwmpt.demo.controller.system;

import com.cxwmpt.demo.annotation.SysLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author admin
 * @date 2020/4/9
 */
@Controller
public class SysDruidController {

    /**
     * druid 数据监控页面
     * @return
     */
    @SysLog("打开数据库状态管理窗口")
    @RequestMapping(value = "/html/system/druid/page")
    public String druid() {
        return "systemSetup/userCenter/druid/index";
    }

}
