package cn.edu.zucc.sso.delete.fieldfilter;

import java.lang.annotation.*;

/**
 * @author crabxyj
 * @date 2020/1/3 10:27
 */
@Documented //增强javadoc
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldFilter {
    /**
     * 默认包含字段
     */
    String[] include() default {};
    /**
     * 排除字段
     */
    String[] exclude() default {};
    /**
     * 模糊处理方式
     */
    String vague() default "";
}
