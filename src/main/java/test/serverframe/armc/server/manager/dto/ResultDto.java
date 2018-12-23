/********************************************
 * 统一返回结果类
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/
package test.serverframe.armc.server.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("统一返回体报文数据结构")
public class ResultDto<T> {

    @ApiModelProperty("返回码(0 为成功，其他数值代表失败)")
    private Integer status;
    @ApiModelProperty("错误信息(若status为0时，提示success)")
    private String msg;
    @ApiModelProperty("返回体报文的出参")
    private T data;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData(Object object) {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
