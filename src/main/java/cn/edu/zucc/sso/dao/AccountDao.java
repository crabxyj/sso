package cn.edu.zucc.sso.dao;

import cn.edu.zucc.sso.pojo.BeanAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author crabxyj
 * @date 2019/12/23 12:04
 */
@Repository("accountDao")
public interface AccountDao extends BaseMapper<BeanAccount> {

}
