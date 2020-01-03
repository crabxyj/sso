package cn.edu.zucc.sso.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author crabxyj
 * @date 2019/12/25 14:08
 */
@Data
@Accessors(chain = true)
@TableName(value = "permission")
public class BeanPermission {
    @TableId(type = IdType.AUTO)
    private int permissionId;

    @NotNull(message = "type 不能为空")
    private String type;
    @NotNull(message = "name 不能为空")
    private String name;

    @Override
    public int hashCode() {
        return permissionId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BeanPermission){
            return type.equals(((BeanPermission)obj).getType())&&name.equals(((BeanPermission)obj).getName());
        }
        return false;
    }
}
