package cn.edu.zucc.sso.service.impl;

import cn.edu.zucc.sso.dao.PermissionDao;
import cn.edu.zucc.sso.pojo.BeanPermission;
import cn.edu.zucc.sso.service.PermissionService;
import cn.edu.zucc.sso.utils.BaseUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author crabxyj
 * @date 2019/12/25 14:13
 */
@Service("permissionServiceImpl")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, BeanPermission> implements PermissionService{

    @Resource(name = "permissionDao")
    private PermissionDao dao;

    @Override
    public Set<BeanPermission> selectByRoleId(Collection<Integer> roleIds){
        Set<Integer> collect = roleIds.stream()
                .filter(id -> id > 0)
                .collect(Collectors.toSet());

        if (collect.isEmpty()){
            return new HashSet<>();
        }

        String ids = BaseUtils.toSqlString(collect);
        List<BeanPermission> permissions = dao.selectByRoleId(ids);
        return new HashSet<>(permissions);
    }

}
