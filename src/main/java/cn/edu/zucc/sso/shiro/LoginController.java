package cn.edu.zucc.sso.shiro;

import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanUserInfo;
import cn.edu.zucc.sso.resultformat.ResultFormatUtils;
import cn.edu.zucc.sso.service.UserInfoService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2019/12/25 16:31
 */
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class LoginController {

    @Resource(name = "userInfoServiceImpl")
    private UserInfoService service;

    @CrossOrigin
    @RequestMapping("/login")
    public JSONObject login(String username, String password) throws BaseException {
        log.info(String.format("username : %s , password : %s",username,password));

        BeanUserInfo userInfo = service.getByUserName(username);

        if (userInfo!=null&&!userInfo.getPassword().equals(password)){
            throw new BaseException("账号密码错误");
        }

        return ResultFormatUtils.ressetResult(JwtUtil.sign(username,password));

//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        try{
//            subject.login(token);
//            subject.checkRole("管理员");
//
//        }catch (AuthenticationException e){
//            e.printStackTrace();
//            return "账号密码错误";
//        }catch (AuthorizationException e) {
//            e.printStackTrace();
//            return "没有权限";
//        }
//        return "login success";
    }
}
