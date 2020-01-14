package cn.edu.zucc.sso.shiro;

import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author crabxyj
 * @date 2019/12/24 20:41
 */
@Getter
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token=token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public String toString() {
        return token;
    }
}
