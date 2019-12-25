package cn.edu.zucc.sso.controller;

import cn.edu.zucc.sso.pojo.BeanAccount;
import cn.edu.zucc.sso.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author crabxyj
 * @date 2019/12/25 9:51
 */
@Api(tags = "地址操作接口")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource(name = "accountServiceImpl")
    private AccountService service;

    @ApiOperation(value = "获取地址",notes = "通过用户获取地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名",required = true),
            @ApiImplicitParam(name = "key",value = "key值")
    })
    @RequestMapping(value = "/address",method = {RequestMethod.POST,RequestMethod.GET})
    public String getAddress(String name,@RequestParam(required = false) int key){
        System.out.println(name);
        System.out.println(key);
        return  "address:\t"+name;
    }


    @ApiOperation(value = "获取name",notes = "通过用户获取name2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名",required = true),
            @ApiImplicitParam(name = "key",value = "key值")
    })
    @RequestMapping(value = "/name",method = {RequestMethod.POST,RequestMethod.GET})
    public List<BeanAccount> getName(String name, @RequestParam(required = false) int key){
        System.out.println(name);
        System.out.println("13");
        System.out.println(key);
        return  service.list();
    }
}
