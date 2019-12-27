package cn.edu.zucc.sso.initrun;

import cn.edu.zucc.sso.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2019/12/27 17:09
 */
@Slf4j
@Component
public class StartPermissionService implements CommandLineRunner {
    @Resource(name = "permissionServiceImpl")
    private PermissionService service;

    @Override
    public void run(String... args){
        try{
            service.resetPortPermission();
        }catch (Exception e){
            log.error("接口权限初始化异常");
        }

    }
}
