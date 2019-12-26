package cn.edu.zucc.sso.controller;

import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanRole;
import cn.edu.zucc.sso.service.RoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2019/12/26 15:31
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    @RequestMapping(value = "/loadPage",method = {RequestMethod.GET,RequestMethod.POST})
    public IPage<BeanRole> loadPage(@RequestParam(required = false,defaultValue = "1") int page,
                                    @RequestParam(required = false,defaultValue = "10") int pageSize){
        return roleService.loadPage(page,pageSize);
    }

    @RequestMapping(value = "/add",method = {RequestMethod.GET,RequestMethod.POST})
    public void add(@RequestParam String roleName) throws BaseException {
        roleService.add(roleName);
    }

    @RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
    public void delete(@RequestParam(required = false,defaultValue = "0") int roleId) throws BaseException {
        roleService.delete(roleId);
    }
}
