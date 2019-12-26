package cn.edu.zucc.sso.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 13:02
 */
@Data
@Builder
@TableName("role")
public class BeanRole {
    private int roleId;
    private String roleName;

    @TableField(exist = false)
    private Set<BeanPermission> permissions;
}
