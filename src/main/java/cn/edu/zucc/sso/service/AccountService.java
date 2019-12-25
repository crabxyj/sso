package cn.edu.zucc.sso.service;

import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

/**
 * @author crabxyj
 * @date 2019/12/23 12:48
 */
public interface AccountService extends IService<BeanAccount> {

    BeanAccount checkLogin(String account, String pwd)throws BaseException;

    Optional<BeanAccount> getByAccount(String account)throws BaseException;

}
