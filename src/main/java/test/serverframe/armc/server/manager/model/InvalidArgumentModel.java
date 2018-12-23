/********************************************
 * 异常参数模型
 *
 * @author zwq
 * @create 2018-07-26
 *********************************************/
package test.serverframe.armc.server.manager.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("异常参数模型")
public class InvalidArgumentModel {
    @ApiModelProperty("异常参数")
    private String param;
    @ApiModelProperty("异常信息")
    private String errMsg;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
