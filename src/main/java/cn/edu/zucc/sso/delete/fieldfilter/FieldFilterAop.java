package cn.edu.zucc.sso.delete.fieldfilter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author crabxyj
 * @date 2020/1/3 10:35
 */
@Aspect
@Slf4j
@Component
public class FieldFilterAop {

//    @Pointcut("@annotation(cn.edu.zucc.sso.delete.fieldfilter.JsonPropertyFilter)")
//    public void point() {
//
//    }

    /**
     * 自定义aop方式拦截自定义注解方法
     *
     * @param joinPoint 切点
     * @param value     返回值处理
     */

//    @AfterReturning(value = "point()", returning = "value")
//    public void doAfter(JoinPoint joinPoint, Object value) {
//        log.info("------数据过滤格式化 start-----");
////        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
////        Method method = methodSignature.getMethod();
////        JsonPropertyFilter annotation = method.getAnnotation(JsonPropertyFilter.class);
//
//        log.info("------数据过滤格式化 end-------");
//    }




}
