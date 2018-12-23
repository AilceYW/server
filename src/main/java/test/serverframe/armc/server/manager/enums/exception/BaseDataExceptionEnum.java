/********************************************
 * 基础数据管理的异常枚举值
 *
 * @author zwq
 * @create 2018-07-28
 *********************************************/

package test.serverframe.armc.server.manager.enums.exception;

import test.serverframe.armc.server.manager.common.exception.IExceptionEnum;

public enum BaseDataExceptionEnum implements IExceptionEnum {
    DIC_TYPE_NOT_EXIST(-11, "字典分类名称不存在");

    private Integer code;
    private String msg;

    BaseDataExceptionEnum(Integer code, String msg) {
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
