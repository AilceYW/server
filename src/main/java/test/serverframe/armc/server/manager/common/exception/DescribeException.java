/********************************************
 * 重新自定义错误信息
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/

package test.serverframe.armc.server.manager.common.exception;

public class DescribeException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer code;

    /**
     * 继承exception，加入错误状态值
     *
     * @param exceptionEnum
     */
    public DescribeException(IExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    /**
     * 自定义错误信息
     *
     * @param message
     * @param code
     */
    public DescribeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
