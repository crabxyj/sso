package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.pojo.BeanAccount;
import cn.edu.zucc.sso.pojo.BeanUserInfo;
import cn.edu.zucc.sso.service.impl.AccountServiceImpl;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author crabxyj
 * @date 2019/12/23 12:52
 */
@SpringBootTest
class AccountServiceTest {
    @Resource(name = "accountServiceImpl")
//    @Autowired
    private AccountServiceImpl service;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    void testList(){
        List<BeanUserInfo> list = userInfoService.list();
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    void testNull(){
        BeanAccount account = service.getById(1);
        System.out.println(JSON.toJSONString(account));
        account = service.getById(2);
        System.out.println(JSON.toJSONString(account));
    }

    @Test
    void testWhere(){
        QueryWrapper<BeanAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("user_phone","123");
        List<BeanAccount> list = service.list(wrapper);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    void testListFor(){
        Pattern r = Pattern.compile("^0?1[1|2|3|4|5|6|7|8|9][0-9]\\d{8}$");
        Matcher matcher = r.matcher("15968812312");
        if (matcher.find()){
            System.out.println("ok");
        }else{
            System.out.println("err");
        }
        System.out.println();
    }
}
