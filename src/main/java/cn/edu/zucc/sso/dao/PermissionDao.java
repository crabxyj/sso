package cn.edu.zucc.sso.dao;

import cn.edu.zucc.sso.pojo.BeanPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author crabxyj
 * @date 2019/12/25 14:11
 */
@Repository("permissionDao")
public interface PermissionDao extends BaseMapper<BeanPermission> {

    /**
     * 通过角色获取权限
     * @param ids role id string
     * @return permissions
     */
    @Select("SELECT permission_id,type,name FROM `permission` WHERE permission_id in (select permission_id from role_permission WHERE role_id in (#{ids}) )")
    List<BeanPermission> selectByRoleId(String ids);

    /**
     * 获取当前权限使用角色
     * @param permissionId 角色id
     * @return personIds
     */
    @Select("select role_id from role_permission WHERE permission_id = #{permissionId} ")
    List<Integer> selectRoleIdByPermissionId(int permissionId);

}
