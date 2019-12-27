package cn.edu.zucc.sso.service.impl;

import cn.edu.zucc.sso.dao.RoleDao;
import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanRole;
import cn.edu.zucc.sso.service.RoleService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 14:15
 */
@Service("roleServiceImpl")
public class RoleServiceImpl extends ServiceImpl<RoleDao, BeanRole> implements RoleService {

    @Resource(name = "roleDao")
    private RoleDao roleDao;

    @Override
    public Set<BeanRole> getByPersonId(int personId) {
        if (personId <= 0) {
            return new HashSet<>();
        }
        return new HashSet<>(roleDao.selectByPersonId(personId));
    }

    @Override
    public IPage<BeanRole> loadPage(int page, int pageSize) {
        Page<BeanRole> page1 = new Page<>(page, pageSize);
        System.out.println(JSON.toJSONString(page1));
        return page(page1);
    }

    @Override
    public BeanRole add(String roleName) throws BaseException {
        QueryWrapper<BeanRole> query = new QueryWrapper<>();
        query.eq("role_name", roleName);
        if (getOne(query) != null) {
            throw new BaseException("当前角色已存在");
        }
        BeanRole role = new BeanRole();
        role.setRoleName(roleName);
        save(role);
        return role;
    }

    @Override
    public void delete(int roleId) throws BaseException {
        List<Integer> persons = roleDao.selectPersonIdByRoleId(roleId);
        if (!persons.isEmpty()) {
            throw new BaseException("当前角色正在被使用,无法删除");
        }
        removeById(roleId);
        roleDao.deletePermissionByRoleId(roleId);
    }
}
