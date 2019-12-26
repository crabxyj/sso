package cn.edu.zucc.sso.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author crabxyj
 * @date 2019/12/25 14:08
 */
@Getter
@Setter
@TableName(value = "permission")
public class BeanPermission {
    private int permissonId;

    @NotNull(message = "type 不能为空")
    private String type;
    @NotNull(message = "name 不能为空")
    private String name;

    @Override
    public int hashCode() {
        return permissonId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BeanPermission){
            return type.equals(((BeanPermission)obj).getType())&&name.equals(((BeanPermission)obj).getName());
        }
        return false;
    }
}
