package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.pojo.BeanPermission;
import cn.edu.zucc.sso.service.impl.PermissionServiceImpl;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 15:02
 */
@SpringBootTest
public class PermissionServiceTest {
    @Resource(name = "permissionServiceImpl")
    private PermissionServiceImpl permissionService;

    @Test
    void teest(){

    }
}
