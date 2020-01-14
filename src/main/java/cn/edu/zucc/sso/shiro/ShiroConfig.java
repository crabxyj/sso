package cn.edu.zucc.sso.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author crabxyj
 * @date 2019/12/24 21:10
 */
@ComponentScan(basePackages = "cn.edu.zucc.sso.service.impl")
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * 不加这个注解不生效，具体不详
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 账号密码验证
     */
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    /**
     * token 验证
     */
    @Bean
    public JwtRealm jwtRealm() {
        return new JwtRealm();
    }

    /**
     * 配置Shiro核心 安全管理器 SecurityManager
     * SecurityManager安全管理器：所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；负责与后边介绍的其他组件进行交互。
     * （类似于SpringMVC中的DispatcherServlet控制器）
     * 权限管理，配置主要是Realm的管理认证
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将自定义的realm交给SecurityManager管理
        //一定要用方法导入不能用new 不然无法注入

//        securityManager.setRealms(Arrays.asList(customRealm(), jwtRealm()));
        securityManager.setRealm(jwtRealm());

        //自定义的shiro session 缓存管理器
//        securityManager.setSessionManager(sessionManager());
        //将缓存对象注入到SecurityManager中
        //defaultWebSecurityManager.setCacheManager(ehCacheManager());

        // 关闭shiro自带的session，详情见文档
        // http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        log.info("ShiroConfig.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //自定义拦截器
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
//        filterMap.put("corsAuthenticationFilter",new CorsAuthenticationFilter());
        // 添加自己的过滤器并且取名为jwt
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

//      配置哪些页面需要受保护.以及访问这些页面需要的权限.
//          1). anon 可以被匿名访问
//          2). authc 必须认证(即登录)后才可能访问的页面.
//          3). logout 登出.
//          4). roles 角色过滤器
//        Anon：表示可以不用登录直接访问
//        Authc：表示需要登录之后才可以访问
//        Perms：表示权限
//        Roles：表示角色
//        User：表示用户

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/login/**", "anon");

        filterChainDefinitionMap.put("/system/**", "perms[\"\"]");

        filterChainDefinitionMap.put("/**.js", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        //<!-- 过滤链定义，从上向下顺序执行，一般将放在最为下边
        filterChainDefinitionMap.put("/**", "jwt");
//        filterChainDefinitionMap.put("/**/*", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

//    /**
//     * 自定义的 shiro session 缓存管理器
//     */
//    @Bean
//    public SessionManager sessionManager(){
//        //将我们继承后重写的shiro session 注册
//        ShiroSession shiroSession = new ShiroSession();
//        //如果后续考虑多tomcat部署应用，可以使用shiro-redis开源插件来做session 的控制，或者nginx 的负载均衡
//        shiroSession.setSessionDAO(new EnterpriseCacheSessionDAO());
//        return shiroSession;
//    }

}