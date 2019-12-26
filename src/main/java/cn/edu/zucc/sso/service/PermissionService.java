package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.pojo.BeanPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 14:12
 */
public interface PermissionService extends IService<BeanPermission> {

    Set<BeanPermission> getByRoleId(Collection<Integer> roleIds);

}
