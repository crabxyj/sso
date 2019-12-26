package cn.edu.zucc.sso.dao;

import cn.edu.zucc.sso.pojo.BeanUserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author crabxyj
 * @date 2019/12/25 12:54
 */
@Repository("userInfoDao")
public interface UserInfoDao extends BaseMapper<BeanUserInfo> {
}
