package cn.edu.zucc.sso.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Set;

/**
 * @author crabxyj
 * @date 2019/12/25 13:02
 */
@Data
@TableName("role")
public class BeanRole {
    @TableId(type = IdType.AUTO)
    private int roleId;
    private String roleName;

    @TableField(exist = false)
    private Set<BeanPermission> permissions;
}
