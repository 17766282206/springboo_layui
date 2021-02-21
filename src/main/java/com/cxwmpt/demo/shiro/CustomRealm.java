package com.cxwmpt.demo.shiro;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxwmpt.demo.common.result.ResultCodeEnum;
import com.cxwmpt.demo.exception.ApiException;

import com.cxwmpt.demo.model.system.SysMenu;
import com.cxwmpt.demo.model.system.SysRole;
import com.cxwmpt.demo.model.system.SysUser;
import com.cxwmpt.demo.service.api.system.SysMenuService;
import com.cxwmpt.demo.service.api.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.apache.shiro.SecurityUtils.getSubject;

/**
 * @author Administrator
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    public SysUserService sysUserService;
    @Autowired
    public SysMenuService sysMenuService;

    @Autowired
    public SessionDAO sessionDAO;
    /**
     * 认证
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确.
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("————身份认证方法已经判断过账户和密码直接保存用户————");
        //获取用户的输入的账号.
        String account = (String) token.getPrincipal();
        //明文密码
        String password = new String((char[]) token.getCredentials());
        //去和数据库对比查询
        //查询用户信息
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        wrapper.eq("del_flag", 0);
        SysUser user = sysUserService.getOne(wrapper);
        if (user == null) {

            throw new ApiException(ResultCodeEnum.USER_IS_NULL);
        }
        if (!user.getPassword().equals(password)) {

            throw new ApiException(ResultCodeEnum.PASSWORD_ERROR);
        }
        if (!user.getStatus().equals("0")) {

            throw new ApiException(ResultCodeEnum.STATUS_ERROR);
        }
        //查询拥有的菜单权限
        //user.setMenus(sysMenuService.getLoginInfoPermission(user));
        //查询拥有的角色
        //user.setRoleLists(sysMenuService.getLoginInfoRole(user));
        // google浏览器上登陆了，再去 ie上登陆了，然后谷歌的就需要再次登陆了，成功的被挤下线了。
        if(account.equals("user")){
            log.info("特殊用户不需要单点登录");
        }else {
            // 获取所有session
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            for (Session session : sessions) {
                SysUser res = (SysUser) session.getAttribute("tbUserSession");
                // 如果session里面有当前登陆的，则证明是重复登陆的，则将其剔除
                if (res != null && user.equals(res)) {
                    session.setTimeout(0);

                }
            }
        }
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("tbUserSession", user);
        //就是我在 google浏览器上登陆了，再去 ie上登陆了，然后谷歌的就需要再次登陆了，成功的被挤下线了。
        //保存信息
        return new SimpleAuthenticationInfo(user, password, account);
    }

    /**
     * 添加用户接口权限授权
     * @param principalCollection
     * @return
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        log.info("开始授权(doGetAuthorizationInfo)");
        SysUser loginUser =  (SysUser) getSubject().getPrincipal();
        // List<MenuItem> tbMenuItemList = tbMenuItemService.listLogPersonSetRole(tbUser);
        //查询拥有的菜单权限

        //通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //查询拥有的菜单权限角色权限
        SysUser user = sysUserService.getPermissionsInfoByUserInfo(loginUser);
        Set<SysRole>  roles = user.getRoleLists();
        Set<String> roleNames = new HashSet<String>();
        for (SysRole role : roles) {
            if(StringUtils.isNotBlank(role.getRoleName())){
                roleNames.add(role.getRoleName());
            }
        }
        Set<SysMenu> menus = user.getMenus();
        Set<String> permissions =new HashSet<String>();
        for (SysMenu menu : menus) {
            if(StringUtils.isNotBlank(menu.getPermission())){
                permissions.add(menu.getPermission());
            }
        }
        info.setRoles(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }
}
