package cn.edu.zucc.sso.shiro;

import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanPermission;
import cn.edu.zucc.sso.pojo.BeanRole;
import cn.edu.zucc.sso.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author crabxyj
 * @date 2020/1/14 17:05
 */
@Slf4j
@Service("shiroServiceImpl")
public class ShiroServiceImpl {
    private static final String PERMS = "jwt,perms";

    @Resource(name = "permissionServiceImpl")
    private PermissionService service;

    /**
     * 初始化权限 -> 拿全部权限
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String,String> loadFilterChainDefinitionMap(){
        // 必须有序
        // 权限控制map  【注：map不能存放相同key】 map结构后者会覆盖前者
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 从数据库或缓存中查取出来的url与角色对应权限映射关系
        // 获取所有地址 及其 可访问角色名称
        List<BeanPermission> permissions = service.load("port", true);
        for(BeanPermission permission : permissions){
            Set<BeanRole> roles = permission.getRoles();
            if (roles==null){
                continue;
            }
            String perms = roles.stream()
                    .map(BeanRole::getRoleName)
                    .filter(name -> !"超管".equals(name))
                    .collect(Collectors.joining(",", "jwt,perms[", "]"));
            log.info(" filter chain key : {} value : {}",permission.getName(),perms);
            filterChainDefinitionMap.put(permission.getName(),perms);
        }

        // 配置过滤:不会被拦截的链接 -> 放行 start ----------------------------------------------------------

        // 放行Swagger2 页面
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/swagger/**","anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/v2/**","anon");
        filterChainDefinitionMap.put("/static/**", "anon");

//        // 登陆
        filterChainDefinitionMap.put("/api/auth/login/**", "anon");
//        // 三方登录
//        filterChainDefinitionMap.put("/api/auth/loginByQQ", "anon");
//        filterChainDefinitionMap.put("/api/auth/afterlogin.do", "anon");
//        // 退出
//        filterChainDefinitionMap.put("/api/auth/logout", "anon");
//        // 放行未授权接口，重定向使用
//        filterChainDefinitionMap.put("/api/auth/unauth", "anon");
//        // token过期接口
//        filterChainDefinitionMap.put("/api/auth/tokenExpired", "anon");
//        // 被挤下线
//        filterChainDefinitionMap.put("/api/auth/downline", "anon");
        // 放行 end ----------------------------------------------------------

//        filterChainDefinitionMap.put("/system/**", "jwt,perms[name1,name2]");

        // ⑤ token认证
        //<!-- 过滤链定义，从上向下顺序执行，一般将放在最为下边
        filterChainDefinitionMap.put("/**/*", "jwt");

        return filterChainDefinitionMap;
    }

    /**
     * 在对uri权限进行增删改操作时，需要调用此方法进行动态刷新加载数据库中的uri权限
     */
    void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean, Integer roleId, Boolean isRemoveSession) throws BaseException {
        synchronized (this){
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new BaseException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            assert shiroFilter != null;
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空拦截管理器中的存储
            manager.getFilterChains().clear();
            // 清空拦截工厂中的存储,如果不清空这里,还会把之前的带进去
            //            ps:如果仅仅是更新的话,可以根据这里的 map 遍历数据修改,重新整理好权限再一起添加
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            // 动态查询数据库中所有权限
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitionMap());
            // 重新构建生成拦截
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                manager.createChain(entry.getKey(), entry.getValue());
            }
            log.info("--------------- 动态生成url权限成功！ ---------------");

//            // 动态更新该角色相关联的用户shiro权限
//            if(roleId != null){
//                updatePermissionByRoleId(roleId,isRemoveSession);
//            }

        }
    }


//    void updatePermissionByRoleId(Integer roleId, Boolean isRemoveSession) {
//        // 查询当前角色的用户shiro缓存信息 -> 实现动态权限
//        List<User> userList = userMapper.selectUserByRoleId(roleId);
//        // 删除当前角色关联的用户缓存信息,用户再次访问接口时会重新授权 ; isRemoveSession为true时删除Session -> 即强制用户退出
//        if ( !CollectionUtils.isEmpty( userList ) ) {
//            for (User user : userList) {
//                ShiroUtils.deleteCache(user.getUsername(), isRemoveSession);
//            }
//        }
//        log.info("--------------- 动态修改用户权限成功！ ---------------");
//    }
}
