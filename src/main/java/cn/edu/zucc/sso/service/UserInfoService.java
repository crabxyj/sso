package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.pojo.BeanUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author crabxyj
 * @date 2019/12/25 12:55
 */
public interface UserInfoService extends IService<BeanUserInfo> {
    /**
     * 通过name获取用户对象
     * @param username 用户名
     * @return 用户对象
     */
    BeanUserInfo getByUserName(String username);

    /**
     * 通过name获取用户对象
     * @param username 用户名
     * @param withRoles 包含角色
     * @param withPermissions 包含权限
     * @return 用户对象
     */
    BeanUserInfo getByUserName(String username, boolean withRoles, boolean withPermissions);
}
