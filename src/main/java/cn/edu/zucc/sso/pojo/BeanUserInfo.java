package cn.edu.zucc.sso.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 12:48
 */
@Data
@TableName("user_info")
public class BeanUserInfo {
    @TableId
    private int personId;
    private String username;
    private String password;

    @TableField(exist = false)
    private Set<BeanRole> roles;
    @TableField(exist = false)
    private Set<BeanPermission> permissions;
}
