package cn.edu.zucc.sso.service.impl;

import cn.edu.zucc.sso.dao.AccountDao;
import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanAccount;
import cn.edu.zucc.sso.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author crabxyj
 * @date 2019/12/23 12:49
 */
@Service("accountServiceImpl")
public class AccountServiceImpl extends ServiceImpl<AccountDao, BeanAccount>  implements AccountService {

    @Resource(name = "accountDao")
    private AccountDao dao;

    @Override
    public Optional<BeanAccount> getByAccount(String account){
        QueryWrapper<BeanAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("account",account);
        return  Optional.ofNullable(dao.selectOne(wrapper));
    }

    @Override
    public BeanAccount checkLogin(String account,String pwd)throws BaseException {
        BeanAccount one = getByAccount(account)
                .orElseThrow(() -> new BaseException(401, "账号或密码错误"));
        if (one.getPassword().equals(pwd)){
            throw  new BaseException(401, "账号或密码错误");
        }
        return one;
    }
}
