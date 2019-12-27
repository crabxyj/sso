package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 14:12
 */
public interface PermissionService extends IService<BeanPermission> {
    /**
     * 获取角色权限
     * @param roleIds 角色
     * @return 权限集
     */
    Set<BeanPermission> getByRoleId(Collection<Integer> roleIds);

    /**
     * 分页查询权限
     * @param type 权限类型
     * @param page 页
     * @param pageSize 页大小
     * @return 权限列表
     */
    IPage<BeanPermission> loadPage(String type, int page, int pageSize);

    /**
     * 查询权限
     * @param type 权限类型
     * @return 权限列表
     */
    List<BeanPermission> load(String type);

    /**
     * 添加权限
     * @param permission 权限
     * @return 添加的权限
     * @throws BaseException 当前权限已存在
     */
    BeanPermission add(BeanPermission permission) throws BaseException;

    /**
     * 删除权限
     * @param permissionId 权限编号
     * @throws BaseException 被使用无法删除
     */
    void delete(int permissionId) throws BaseException;

    /**
     * 初始化接口权限列表
     * @throws BaseException 保存异常
     */
    void resetPortPermission() throws BaseException;

//    /**
//     * 获取接口列表
//     * @return 接口列表
//     */
//    List<BeanPermission> loadPortPermission();
}
