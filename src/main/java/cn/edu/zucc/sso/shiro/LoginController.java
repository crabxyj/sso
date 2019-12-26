package cn.edu.zucc.sso.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author crabxyj
 * @date 2019/12/25 16:31
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login")
    public String login(String username,String password){
        System.out.println(username);
        System.out.println(password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token);
            subject.checkRole("管理员");
        }catch (AuthenticationException e){
            e.printStackTrace();
            return "账号密码错误";
        }catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有权限";
        }
        return "login success";
    }
}
