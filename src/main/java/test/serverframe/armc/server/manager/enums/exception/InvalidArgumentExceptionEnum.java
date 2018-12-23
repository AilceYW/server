/********************************************
 * 请求参数异常枚举值
 *
 * @author zwq
 * @create 2018-07-26
 *********************************************/
package test.serverframe.armc.server.manager.enums.exception;


import test.serverframe.armc.server.manager.common.exception.IExceptionEnum;

public enum InvalidArgumentExceptionEnum implements IExceptionEnum {
    ARG_INVALID_ERROR(-2, "参数校验错误");

    private Integer code;
    private String msg;

    InvalidArgumentExceptionEnum(Integer code, String msg) {
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
