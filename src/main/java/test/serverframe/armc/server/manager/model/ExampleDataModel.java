/********************************************
 * 示例数据模型
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/

package test.serverframe.armc.server.manager.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("示例数据模型")
public class ExampleDataModel {
    @ApiModelProperty("用户名")
    String uname;
    @ApiModelProperty("密码")
    String upwd;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }


}
