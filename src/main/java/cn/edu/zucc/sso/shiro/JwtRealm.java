package cn.edu.zucc.sso.shiro;

import cn.edu.zucc.sso.pojo.BeanPermission;
import cn.edu.zucc.sso.pojo.BeanRole;
import cn.edu.zucc.sso.pojo.BeanUserInfo;
import cn.edu.zucc.sso.service.UserInfoService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2020/1/13 15:28
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {
    @Resource(name = "userInfoServiceImpl")
    private UserInfoService service;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = JwtUtil.getUsername(principalCollection.toString());
        BeanUserInfo userInfo = service.getByUserName(username, true, true);
        log.info(String.format("JwtRealm : userInfo : %s", JSON.toJSONString(userInfo)));
        SimpleAuthorizationInfo authorInfo = new SimpleAuthorizationInfo();
        if (userInfo != null) {
            //添加角色
            for (BeanRole role : userInfo.getRoles()) {
                authorInfo.addRole(role.getRoleName());
            }
            //添加权限
            for (BeanPermission permission : userInfo.getPermissions()) {
                authorInfo.addStringPermission(permission.getName());
            }
        }
        log.info(" userInfo {}",JSON.toJSONString(userInfo));
        return authorInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authenticationToken;
        String t = token.getToken();
        if (t==null){
            throw new AuthenticationException("用户未登录");
        }
        String username = JwtUtil.getUsername(t);
        if (username == null) {
            throw new AuthenticationException("token无效");
        }
        BeanUserInfo userInfo = service.getByUserName(username);
        log.info(JSON.toJSONString(userInfo));
        if (userInfo == null) {
            throw new AuthenticationException("用户不存在!");
        }
        if (!JwtUtil.verify(token.getToken(), username, userInfo.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        log.info("verify accept ");
        return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
    }
}
