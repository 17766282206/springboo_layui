package com.cxwmpt.demo.controller.system;



import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.cxwmpt.demo.annotation.SysLog;
import com.cxwmpt.demo.common.result.ResultCodeEnum;
import com.cxwmpt.demo.common.result.ResultMessage;
import com.cxwmpt.demo.common.util.MD5Util;
import com.cxwmpt.demo.model.system.SysUser;
import com.cxwmpt.demo.model.system.SysUserRole;
import com.cxwmpt.demo.service.api.system.SysUserRoleService;
import com.cxwmpt.demo.service.api.system.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static org.apache.shiro.SecurityUtils.getSubject;

/**
 * @author Administrator
 */
@Controller
public class SysUserController {

    private final SysUserService sysUserService;

    private final SysUserRoleService sysUserRoleService;

    public SysUserController(SysUserRoleService sysUserRoleService, SysUserService sysUserService) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysUserService = sysUserService;
    }


    /**
     * 页面
     *
     * @return
     */
    @SysLog("打开用户管理窗口")
    @RequestMapping("/html/system/user/page")
    public String page() {
        return "systemSetup/userCenter/user/Page";
    }

    /**
     * 新增索引页面
     * @return
     */
    @SysLog("打开新增用户管理窗口")
    @RequestMapping("/html/system/user/addPage")
    public String addPage() {
        return "systemSetup/userCenter/user/Add";
    }
    /**
     * 修改密码
     * @param model
     * @return
     */
    @SysLog("打开修改用户密码窗口")
    @RequestMapping("/html/system/user/UpdatePasswordPage")
    public String updatePassWordPage(Model model) {
        return "systemSetup/userCenter/user/UpdatePasswordPage";
    }
    /**
     * TbAdmin加载新增form页面
     */
    @SysLog("打开编辑用户管理窗口")
    @RequestMapping("/html/system/user/updatePage")
    public String updatePage(Model model, String id, String action) {
        model.addAttribute("action", action);
        String userRoleIds = "";
        if (StringUtils.isNotBlank(id)) {
            model.addAttribute("entity", sysUserService.getById(id));
            QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", id);
            wrapper.eq("del_flag", false);
            List<SysUserRole> sysUserRoleList = sysUserRoleService.list(wrapper);
            for (int i = 0; i < sysUserRoleList.size(); i++) {
                SysUserRole sysUserRole = sysUserRoleList.get(i);
                if (i == sysUserRoleList.size() - 1) {
                    userRoleIds += sysUserRole.getRoleId();
                } else {
                    userRoleIds += sysUserRole.getRoleId() + ",";
                }
            }
        }
        model.addAttribute("userRoleIds",userRoleIds);
        return "systemSetup/userCenter/user/Update";
    }


    /**
     *
     *
     * @param map
     * @return
     */
    @SysLog("分页查询用户管理信息")
    @RequestMapping("/api/auth/user/pageList")
    @ResponseBody
    public ResultMessage pageList(@RequestParam Map map) {
        if (map.containsKey("page") && map.containsKey("limit")) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("limit").toString()));
        }
        //判断是否有分页数据传过来
        List<SysUser> list = sysUserService.getAllList(map);
        PageInfo<SysUser> info = new PageInfo<>(list);
        return ResultMessage.success(info.getList(), (int) info.getTotal());
    }


    @SysLog("删除用户信息")
    @RequestMapping("/api/auth/user/deletes")
    @ResponseBody
    public ResultMessage deletes(@RequestParam("ids[]") List<String> ids) {
        for (String data : ids) {
            sysUserService.removeById(data);
            QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", data);
            wrapper.eq("del_flag", false);
            sysUserRoleService.remove(wrapper);
        }
        return ResultMessage.success("删除数据成功");
    }

    @SysLog("保存用户信息")
    @RequestMapping("/api/auth/user/save")
    @ResponseBody
    public ResultMessage save(@RequestParam("form") String form, @RequestParam("ArrayIds") String ArrayIds) {
        SysUser sysUser = JSONObject.parseObject(form, SysUser.class);
        List<String> array = JSONObject.parseArray(ArrayIds, String.class);
        //获取登录人信息
        SysUser loginUser = (SysUser) getSubject().getPrincipal();
        //查询有没有重复的字段
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("account", sysUser.getAccount());
        wrapper.eq("del_flag", false);
        int count = sysUserService.count(wrapper);
        //添加新用户验证loginID是否相同
        if (sysUser.getId() != null && sysUser.getId().length() > 0) {
            //修改
            //原数据库数据
            SysUser old = sysUserService.getById(sysUser.getId());

            if (!sysUser.getAccount().equals(old.getAccount())) {
                if (count != 0) {
                    return ResultMessage.error(-1, "对不起，你修改的用户登录帐号重复，请重新创建");
                }
            }
            sysUser.setUpdateId(loginUser.getId());
            if(sysUser.getPassword()!=null&&sysUser.getPassword()!=""){
                sysUser.setPassword(MD5Util.md5(sysUser.getPassword()));
            }else {
                sysUser.setPassword(old.getPassword());
            }
            if (sysUserService.updateById(sysUser)) {
                //删除原来权限
                QueryWrapper<SysUserRole> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("user_id", sysUser.getId());
                wrapper1.eq("del_flag", false);
                sysUserRoleService.remove(wrapper1);
                for (String data : array) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(data);
                    sysUserRole.setCreateId(loginUser.getId());
                    sysUserRoleService.save(sysUserRole);
                }
                return ResultMessage.success();
            }
            return ResultMessage.error(-1, "修改数据失败");
        } else {
            //新增
            if (count != 0) {
                return ResultMessage.error(-1, "对不起，你创建的用户登录帐号重复，请重新创建");
            }
            sysUser.setPassword(MD5Util.md5(sysUser.getPassword()));
            sysUser.setCreateId(loginUser.getId());
            if (sysUserService.save(sysUser)) {
                for (String data : array) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRole.setRoleId(data);
                    sysUserRole.setCreateId(loginUser.getId());
                    sysUserRoleService.save(sysUserRole);
                }
                return ResultMessage.success();
            }
            return ResultMessage.error(-1, "新增数据失败");
        }
    }

    /**
     * 保存新密码
     * @return
     */
    @SysLog("保存新密码")
    @RequestMapping("/api/auth/user/saveNewPassword")
    @ResponseBody
    public ResultMessage saveNewPassword(String oldPassword, String newPassword){
        //获取登录人信息
        SysUser loginUser = (SysUser) getSubject().getPrincipal();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("id", loginUser.getId());
        wrapper.eq("del_flag", 0);
        SysUser sysUser = sysUserService.getOne(wrapper);
        if (sysUser == null) {
            return ResultMessage.error(ResultCodeEnum.USER_IS_NULL);
        }
        //md5比较
        if (!sysUser.getPassword().equals(MD5Util.md5(oldPassword))) {
            return ResultMessage.error(ResultCodeEnum.OLD_PASSWORD_NOT_NEW_PASSWORD);
        }
        sysUser.setPassword(MD5Util.md5(newPassword));
        //修改密码成功返回修改后的数据
        sysUser.setUpdateId(loginUser.getId());
        if (sysUserService.updateById(sysUser)) {
            return  ResultMessage.success();
        }
        return ResultMessage.error(ResultCodeEnum.UPDATE_PASSWORD_ERROR);
    }
}
