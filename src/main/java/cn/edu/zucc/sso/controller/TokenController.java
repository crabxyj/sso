package cn.edu.zucc.sso.controller;

import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanAccount;
import cn.edu.zucc.sso.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2019/12/23 15:21
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Resource(name = "accountServiceImpl")
    private AccountService service;

    @RequestMapping("/login")
    @ResponseBody
    public BeanAccount login(String account,String pwd) throws BaseException {
        System.out.println(account);
        System.out.println(pwd);
        return service.checkLogin(account,pwd);
    }
}
