/********************************************
 * 返回体报文工具类
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/

package test.serverframe.armc.server.manager.common;

import test.serverframe.armc.server.manager.common.exception.IExceptionEnum;
import test.serverframe.armc.server.manager.dto.ResultDto;


public class ResultDtoUtil<T> {

    /**
     * 返回成功，传入返回体具体出參
     *
     * @param object
     * @return
     */
    public static <T> ResultDto<T> success(T data) {
        ResultDto<T> result = new ResultDto<T>();
        result.setStatus(0);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    /**
     * 提供给部分不需要出參的接口
     *
     * @return
     */

    public static <T> ResultDto<T> success() {
        return success(null);
    }

    /**
     * 自定义错误信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static <T> ResultDto<T> error(Integer code, String msg) {
        ResultDto<T> result = new ResultDto<T>();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 返回异常信息，在已知的范围内
     *
     * @param exceptionEnum
     * @return
     */
    public static <T> ResultDto<T> error(IExceptionEnum exceptionEnum) {
        ResultDto<T> result = new ResultDto<T>();
        result.setStatus(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMsg());
        result.setData(null);
        return result;
    }

    /**
     * 返回异常信息，在已知的范围内
     *
     * @param exceptionEnum
     * @return
     */
    public static <T> ResultDto<T> error(IExceptionEnum exceptionEnum, T data) {
        ResultDto<T> result = new ResultDto<T>();
        result.setStatus(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMsg());
        result.setData(data);
        return result;
    }
}
