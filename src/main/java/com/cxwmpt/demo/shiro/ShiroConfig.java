package com.cxwmpt.demo.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.cxwmpt.demo.shiro.CustomRealm;
import com.cxwmpt.demo.shiro.ShiroSessionListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
@Configuration
public class ShiroConfig {



    // 下面两个方法对 注解权限起作用有很大的关系，请把这两个方法，放在配置的最上面
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    //将自己的验证方式加入容器

    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }
    //页面引用
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    //配置shiro session 的一个管理器
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new ShiroSessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionDAO(sessionDAO());
        // 去掉shiro登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //解决单点登录相互顶
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        //设置session过期时间
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager());
        // 设置自己的realm.
        defaultWebSecurityManager.setRealm(customRealm());

        //defaultWebSecurityManager.setSessionManager( getDefaultWebSessionManager() );
        return defaultWebSecurityManager;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 配置Shiro的Web过滤器，拦截浏览器请求并交给SecurityManager处理
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 没有登陆的用户只能访问登陆页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接,不配置则跳转至”/”，可以不配置，直接通过代码进行处理
        // shiroFilterFactoryBean.setSuccessUrl("/index");

        //没有权限默认跳转的页面，登录的用户访问了没有被授权的资源自动跳转到的页面。
        shiroFilterFactoryBean.setUnauthorizedUrl("/404");//调用的接口

        //配置拦截链 使用LinkedHashMap,因为LinkedHashMap是有序的，shiro会根据添加的顺序进行拦截
        // Map<K,V> K指的是拦截的url V值的是该url是否拦截
        Map<String, String> filterChainMap = new LinkedHashMap<String, String>(16);

        // 配置不会被拦截的链接 顺序判断 静态资源
        filterChainMap.put("/html/**", "anon");
        filterChainMap.put("/common/**", "anon");
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/img/**", "anon");
        filterChainMap.put("/js/**", "anon");
        filterChainMap.put("/layuiadmin/**", "anon");
        filterChainMap.put("/api/auth/account/_login", "anon");//登录接口
        filterChainMap.put("/api/auth/account/forgetPassword", "anon");//忘记密码接口



        filterChainMap.put("/api/filter/**", "anon");//过滤掉接口//设备访问接口

        //配置退出过滤器logout，由shiro实现
        filterChainMap.put("/api/auth/logout", "logout");
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问,先配置anon再配置authc。
        filterChainMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }





    /**
     * sessionDAO可以
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        MemorySessionDAO sessionDAO = new MemorySessionDAO();
        return sessionDAO;
    }
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie();
        // 设置Cookie名字，默认为JSESSIONID
        cookie.setName("WEBSID8088");
        //如果设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        cookie.setHttpOnly(true);

        // 设置Cookie的过期时间，秒为单位，默认-1表示关闭浏览器时过期Cookie
        // cookie.setMaxAge(18000);
        return cookie;
    }


}
