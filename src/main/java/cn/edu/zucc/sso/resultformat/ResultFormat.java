package cn.edu.zucc.sso.resultformat;

import java.lang.annotation.*;

/**
 * @author crabxyj
 * @date 2019/12/27 10:45
 */
@Documented //增强javadoc
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultFormat {

    /**
     * 默认包含字段
     */
    String include() default "";
    /**
     * 排除字段
     */
    String exclude() default "";

    /**
     * 请求可选字段
     */
    String properties() default "";
}
