package cn.edu.zucc.sso.controller;

import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanPermission;
import cn.edu.zucc.sso.resultformat.ResultFormat;
import cn.edu.zucc.sso.service.PermissionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author crabxyj
 * @date 2019/12/27 16:36
 */
@RestController
@ResultFormat
@RequestMapping("/api/permission")
public class PermissionController {
    @Resource(name = "permissionServiceImpl")
    private PermissionService service;

    @RequestMapping(value = "/loadPage",method = {RequestMethod.GET,RequestMethod.POST})
    public IPage<BeanPermission> loadPage(
            @RequestParam String type,
            @RequestParam(required = false,defaultValue = "1") int page,
            @RequestParam(required = false,defaultValue = "10") int pageSize){
        return service.loadPage(type,page,pageSize);
    }

    @RequestMapping(value = "/load",method = {RequestMethod.GET,RequestMethod.POST})
    public List<BeanPermission> load(){
        return service.load(null,true);
    }

    @RequestMapping(value = "/add",method = {RequestMethod.GET,RequestMethod.POST})
    public BeanPermission add(@RequestParam String name,
                              @RequestParam String type) throws BaseException {
        BeanPermission permission = new BeanPermission()
                .setName(name).setType(type);
        return service.add(permission);
    }

    @RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public void delete(@RequestParam int permissionId) throws BaseException {
        service.delete(permissionId);
    }

}
