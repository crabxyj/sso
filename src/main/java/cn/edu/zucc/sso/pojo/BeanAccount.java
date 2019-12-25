package cn.edu.zucc.sso.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author crabxyj
 * @date 2019/12/23 12:02
 */
@Getter
@Setter
@ApiModel(value = "用户类",description = "用户类信息列表")
@TableName("normal_account")
public class BeanAccount {
    @ApiModelProperty(value = "id",hidden = true)
    private int id;
    @ApiModelProperty(value = "账号",example = "accountName ")
    private String account;
    @ApiModelProperty(value = "密码",example = "123456")
    private String password;
    private String userPhone;

}
