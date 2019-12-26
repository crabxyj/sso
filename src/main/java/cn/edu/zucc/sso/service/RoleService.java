package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.pojo.BeanRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 14:15
 */
public interface RoleService extends IService<BeanRole> {

    /**
     * 获取用户角色信息
     * @param personId 用户id
     * @return 角色s
     */
    Set<BeanRole> getByPersonId(int personId);
}
