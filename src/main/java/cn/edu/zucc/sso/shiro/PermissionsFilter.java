package cn.edu.zucc.sso.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author crabxyj
 * @date 2020/1/14 16:29
 */
public class PermissionsFilter extends AuthorizationFilter {
    /**
     * 最高权限
     */
    private static final String SUPER_ROOT = "超管";

    /**
     * 重写成包含关系,有一个通过即可
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[])mappedValue;
        System.out.printf("PermissionsFilter %s\n", JSON.toJSONString(perms));
        if (perms!=null &&perms.length>0){
            if (subject.isPermitted(SUPER_ROOT)){
                return true;
            }
            for (String perm : perms){
                System.out.println(perm);
                if(subject.isPermitted(perm)){
                    return true;
                }
            }
        }
        return false;
    }

}
