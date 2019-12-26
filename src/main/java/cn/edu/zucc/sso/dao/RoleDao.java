package cn.edu.zucc.sso.dao;

import cn.edu.zucc.sso.pojo.BeanRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author crabxyj
 * @date 2019/12/25 14:14
 */
@Repository("roleDao")
public interface RoleDao extends BaseMapper<BeanRole> {

    /**
     * 通过角色获取权限
     * @param personId 用户id
     * @return permissions
     */
    @Select("SELECT * FROM role WHERE role_id in (select role_id from user_role WHERE person_id = #{personId} )")
    List<BeanRole> selectByPersonId(int personId);

    /**
     * 获取当前角色使用人员
     * @param roleId 角色id
     * @return personIds
     */
    @Select("select person_id from user_role WHERE role_id = #{roleId} )")
    List<Integer> getPersonIdByRoleId(int roleId);

    /**
     * 根据角色删除
     * 权限-角色关联信息
     */
    @Select("delete from role_permission WHERE role_id = #{roleId} )")
    void deletePermissionByRoleId(int roleId);
}
