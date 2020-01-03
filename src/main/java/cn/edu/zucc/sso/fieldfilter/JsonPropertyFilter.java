package cn.edu.zucc.sso.fieldfilter;

import java.lang.annotation.*;

/**
 * @author crabxyj
 * @date 2019/12/30 21:33
 */
@Documented //增强javadoc
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonPropertyFilter {
    Class<?> clazz() default Object.class;
    /**
     * 默认包含字段
     */
    String[] include() default {};
    /**
     * 排除字段
     */
    String[] exclude() default {};

    /**
     * 请求可选字段
     */
    String[] properties() default {};
}
