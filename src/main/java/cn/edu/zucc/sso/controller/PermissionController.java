package cn.edu.zucc.sso.controller;

import cn.edu.zucc.sso.pojo.BeanPermission;
import cn.edu.zucc.sso.resultformat.ResultFormat;
import cn.edu.zucc.sso.service.PermissionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2019/12/27 16:36
 */
@RestController
@ResultFormat
@RequestMapping("/system/port")
public class PermissionController {
    @Resource(name = "permissionServiceImpl")
    private PermissionService service;

    @RequestMapping(value = "/urls",method = {RequestMethod.GET,RequestMethod.POST})
    public IPage<BeanPermission> loadPage(
            @RequestParam String type,
            @RequestParam(required = false,defaultValue = "1") int page,
            @RequestParam(required = false,defaultValue = "10") int pageSize){
        return service.loadPage(type,page,pageSize);
    }

}
