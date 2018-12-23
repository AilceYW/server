/********************************************
 * 未知异常枚举值
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/

package test.serverframe.armc.server.manager.enums.exception;

import test.serverframe.armc.server.manager.common.exception.IExceptionEnum;

public enum UnknowExceptionEnum implements IExceptionEnum {
    UNKNOW_ERROR(-1, "未知错误"),;

    private Integer code;
    private String msg;

    UnknowExceptionEnum(Integer code, String msg) {
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
