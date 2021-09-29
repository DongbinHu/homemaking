package com.company.homemaking.business.conf;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 胡东斌
 * @create 2020/4/8
 */
@Configuration
public class ShiroConfig {
    private static final String CACHE_KEY = "homemaking:cache:";
    private static final String SESSION_KEY = "homemaking:session:";
    private static final String NAME = "JSESSIONID";
    private static final String VALUE = "/";

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean(name = "factoryBean")
    public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置安全管理  这个属性是必须的。
        Map<String, Filter> filters = new HashMap<String, Filter>();//获取filters
        //将自定义 的ShiroFilterFactoryBean注入shiroFilter
        filters.put("authc", new ShiroLoginFilter());
        factoryBean.setFilters(filters);
        factoryBean.setSecurityManager(securityManager);
        /*
        Shiro内置过滤器, 可以实现权限相关的拦截器
        常用的过滤器:
           anon:表示可以匿名使用。
           authc:表示需要认证(登录)才能使用，没有参数
           roles：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"]
           每个参数通过才算通过，相当于hasAllRoles()方法。
           perms：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]
           当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
           rest：根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
           port：当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等
           serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
           authcBasic：没有参数表示httpBasic认证
           ssl:表示安全的url请求，协议为https
           user:当登入操作时不做检查
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/business/sysUser/**", "roles[管理员]");
//        filterMap.put("/business/building/**", "roles[运营管理]");
        //后台管理静态文件
        filterMap.put("/*.js", "anon");
        filterMap.put("/*.css", "anon");
        filterMap.put("/*.png", "anon");
        filterMap.put("/*.svg", "anon");
        filterMap.put("/scripts/**", "anon");
        filterMap.put("/icons/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/css/**", "anon");
        //登录、退出登录等接口
        filterMap.put("/business/sys/**", "anon");
        filterMap.put("/404", "anon");
        //小程序接口
        filterMap.put("/consumer/**", "anon");
        //其余接口全部登录后可访问
        filterMap.put("/**", "authc");
//        filterMap.put("/business/**","ShiroLoginFilter");
        /*
        loginUrl ：没有登录的用户请求需要登录的页面时自动跳转到登录页面，不是必须的属性
                    不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面。
        successUrl ：登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面
                    则在登录自动跳转到那个需要登录的页面。不跳转到此。
        unauthorizedUrl ：没有权限默认跳转的页面
         */
        factoryBean.setLoginUrl("/business/sys/toLogin");
        factoryBean.setSuccessUrl("/test");
        factoryBean.setUnauthorizedUrl("/business/sys/noAuth");

        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public SysUserRealm getRealm() {
        SysUserRealm userRealm = new SysUserRealm();
        return userRealm;
    }

//    @Bean
//    public ShiroLoginFilter shiroLoginFilter(){
//        return new ShiroLoginFilter();
//    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("userRealm") SysUserRealm userRealm, SessionManager sessionManager, RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setSessionManager(sessionManager);
        manager.setCacheManager(redisCacheManager);
        manager.setRealm(userRealm);
        return manager;
    }
    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */

    @Bean
    public RedisManager redisManager(JedisPool jedisPool) {
        RedisManager redisManager = new RedisManager();
        redisManager.setJedisPool(jedisPool);
        return redisManager;
    }


    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     *
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(RedisSessionDAO sessionDAO, SimpleCookie simpleCookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(NAME);
        simpleCookie.setValue(VALUE);
        return simpleCookie;
    }



    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


}
