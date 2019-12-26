package cn.edu.zucc.sso.shiro.realm;

import cn.edu.zucc.sso.pojo.BeanPermission;
import cn.edu.zucc.sso.pojo.BeanRole;
import cn.edu.zucc.sso.pojo.BeanUserInfo;
import cn.edu.zucc.sso.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2019/12/25 15:44
 * 自定义Realm用于查询用户的角色和权限信息并保存到权限管理器
 */
public class CustomRealm extends AuthorizingRealm {

    @Resource(name = "userInfoServiceImpl")
    private UserInfoService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户id
        String userName = (String)principalCollection.getPrimaryPrincipal();
        BeanUserInfo userInfo = service.getByUserName(userName, true, true);

        SimpleAuthorizationInfo authorInfo = new SimpleAuthorizationInfo();
        //添加角色
        for (BeanRole role : userInfo.getRoles()){
            authorInfo.addRole(role.getRoleName());
        }
        //添加权限
        for (BeanPermission permission : userInfo.getPermissions()){
            authorInfo.addStringPermission(permission.getName());
        }
        return authorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        String username =(String) authenticationToken.getPrincipal();
        if (username == null) {
            return null;
        }
        //获取用户信息
        System.out.println(service);
        BeanUserInfo userInfo = service.getByUserName(username);
        if (userInfo != null) {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            return new SimpleAuthenticationInfo(username, userInfo.getPassword(), getName());
        }
        //这里返回后会报出对应异常
        return null;
    }
}
