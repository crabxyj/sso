package cn.edu.zucc.sso.service.impl;

import cn.edu.zucc.sso.dao.UserInfoDao;
import cn.edu.zucc.sso.pojo.BeanRole;
import cn.edu.zucc.sso.pojo.BeanUserInfo;
import cn.edu.zucc.sso.service.PermissionService;
import cn.edu.zucc.sso.service.RoleService;
import cn.edu.zucc.sso.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author crabxyj
 * @date 2019/12/25 12:56
 */
@Service("userInfoServiceImpl")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, BeanUserInfo> implements UserInfoService {
    @Resource(name = "roleServiceImpl")
    private RoleService roleService;

    @Resource(name = "permissionServiceImpl")
    private PermissionService permissionService;

    @Override
    public BeanUserInfo getByUserName(String username) {
        QueryWrapper<BeanUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return getOne(queryWrapper);
    }

    @Override
    public BeanUserInfo getByUserName(String username,boolean withRoles,boolean withPermissions) {
        BeanUserInfo one = getByUserName(username);
        if (one==null){
            return one;
        }
        if (withRoles||withPermissions){
            //获取角色
            Set<BeanRole> roles = roleService.getByPersonId(one.getPersonId());
            one.setRoles(roles);
            if (withPermissions){
                //获取权限
                Set<Integer> roleIds = roles.stream()
                        .map(BeanRole::getRoleId)
                        .collect(Collectors.toSet());
                one.setPermissions(permissionService.getByRoleId(roleIds));
            }
        }
        return one;
    }
}
