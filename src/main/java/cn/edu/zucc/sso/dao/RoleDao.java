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

}
