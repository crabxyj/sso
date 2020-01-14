package cn.edu.zucc.sso.resultformat;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * @author crabxyj
 * @date 2019/12/27 11:14
 * 返回结果处理类
 */
@Slf4j
@RestControllerAdvice
public class ResultHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = methodParameter.getMethod();
        if (method==null){
            return false;
        }
        // 只处理含有ResultFormat注解的方法 或 类
        ResultFormat resultFormat = method.getDeclaringClass().getAnnotation(ResultFormat.class);
        if (resultFormat!=null){
            return true;
        }
        resultFormat = method.getAnnotation(ResultFormat.class);
        return resultFormat != null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //修改返回值
        Method method = methodParameter.getMethod();
        JSONObject json;
        if (method!=null){
            json = ResultFormatUtils.ressetResult(o,method.getAnnotation(ResultFormat.class));
        }else{
            json = ResultFormatUtils.ressetResult(o);
        }
        log.info(json.toJSONString());

        return json;
    }
}
