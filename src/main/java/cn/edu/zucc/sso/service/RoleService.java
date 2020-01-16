package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 14:15
 */
@Transactional(rollbackFor = Exception.class)
public interface RoleService {

    /**
     * 获取用户角色信息
     * @param personId 用户id
     * @return 角色s
     */
    Set<BeanRole> getByPersonId(int personId);

    /**
     * 分页查询
     * @param page 当前页
     * @param pageSize 页大小
     * @return 查询结果
     */
    IPage<BeanRole> loadPage(int page, int pageSize);

    /**
     * 获取所有角色
     * @return 查询结果
     */
    List<BeanRole> load(Collection<Integer> roleIds);


    /**
     * 添加 roleid自增
     * @param roleName  角色名 不可重复
     * @throws BaseException 重复异常
     * @return BeanRole 添加的对象
     */
    BeanRole add(String roleName) throws BaseException;

    /**
     * 删除
     * @param roleId 通过roleId 删除
     * @throws BaseException 被使用异常
     */
    void delete(int roleId) throws BaseException ;
}
