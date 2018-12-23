/********************************************
 * 统一异常处理类
 *
 * @author zwq
 * @create 2018-06-26
 *********************************************/

package test.serverframe.armc.server.manager.common.exception;

import test.serverframe.armc.server.manager.common.ResultDtoUtil;
import test.serverframe.armc.server.manager.dto.ResultDto;
import test.serverframe.armc.server.manager.enums.exception.InvalidArgumentExceptionEnum;
import test.serverframe.armc.server.manager.enums.exception.UnknowExceptionEnum;
import test.serverframe.armc.server.manager.model.InvalidArgumentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class ExceptionHandle<T> {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     *
     * @param e 异常
     * @return
     */
    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDto<T> exceptionGet(Exception e) {
        e.printStackTrace();

        if (e instanceof DescribeException) {
            DescribeException myException = (DescribeException) e;
            return ResultDtoUtil.error(myException.getCode(), myException.getMessage());
        }
        if (e instanceof ConstraintViolationException) {
            List<InvalidArgumentModel> invalidArguments = new ArrayList<>();
            //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
            for (ConstraintViolation<?> error : ((ConstraintViolationException) e).getConstraintViolations()) {
                InvalidArgumentModel invalidArgument = new InvalidArgumentModel();
                invalidArgument.setParam(error.getPropertyPath().toString());
                invalidArgument.setErrMsg(error.getMessage());
                invalidArguments.add(invalidArgument);
            }
            return (ResultDto<T>) ResultDtoUtil.error(InvalidArgumentExceptionEnum.ARG_INVALID_ERROR, invalidArguments);
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgNotValid = (MethodArgumentNotValidException) e;
            List<InvalidArgumentModel> invalidArguments = new ArrayList<>();
            InvalidArgumentModel invalidArgument = new InvalidArgumentModel();
            invalidArgument.setParam(methodArgNotValid.getParameter().getParameterName());
            invalidArgument.setErrMsg(methodArgNotValid.getMessage());
            invalidArguments.add(invalidArgument);

            return (ResultDto<T>) ResultDtoUtil.error(InvalidArgumentExceptionEnum.ARG_INVALID_ERROR, invalidArguments);

        }

        logger.error("【管理端系统异常】{}", e);
        return ResultDtoUtil.error(UnknowExceptionEnum.UNKNOW_ERROR);
    }


}
