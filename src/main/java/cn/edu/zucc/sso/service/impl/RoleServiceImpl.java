package cn.edu.zucc.sso.service.impl;

import cn.edu.zucc.sso.dao.RoleDao;
import cn.edu.zucc.sso.pojo.BeanRole;
import cn.edu.zucc.sso.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
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
    public Set<BeanRole> getByPersonId(int personId){
        if (personId<=0){
            return new HashSet<>();
        }
        return new HashSet<>(roleDao.selectByPersonId(personId));
    }


}
