/********************************************
 * 示例功能的异常枚举值
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/

package test.serverframe.armc.server.manager.enums.exception;

import test.serverframe.armc.server.manager.common.exception.IExceptionEnum;

public enum ExampleExceptionEnum implements IExceptionEnum {
    USER_NOT_FIND(-101, "用户或密码不匹配");

    private Integer code;
    private String msg;

    ExampleExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
