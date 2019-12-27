package cn.edu.zucc.sso.resultformat;

import cn.edu.zucc.sso.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author crabxyj
 * @date 2019/12/27 11:51
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 业务异常处理方法
      */
    @ExceptionHandler(value = BaseException.class)
    public Object baseException(HttpServletRequest request, BaseException e, HandlerMethod handlerMethod) {
        log.error("业务处理不成功，错误信息为：	{}", e.getMessage());
        return ResultFormatUtils.baseExceptionResult(e);
    }

    /**
     * 服务器异常处理方法
     */
    @ExceptionHandler(value = Exception.class)
    public Object exception(HttpServletRequest request, Exception e, HandlerMethod handlerMethod) {
        log.error("服务器发生异常，异常信息为", e);
        return ResultFormatUtils.sysExceptionResult();
    }
}
