package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.pojo.BeanRole;
import cn.edu.zucc.sso.service.impl.RoleServiceImpl;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 14:35
 */
@SpringBootTest
class RoleServiceTest {
    @Resource(name = "roleServiceImpl")
    private RoleServiceImpl service;

    @Test
    void test(){

    }
}
