package cn.edu.zucc.sso.utils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author crabxyj
 * @date 2019/12/25 15:27
 */
public class BaseUtils {
    public static String toSqlString(Collection<?> objs){
        return objs.stream()
                .map(BaseUtils::toSqlString)
                .collect(Collectors.joining(",")).toString();
    }
    private static String toSqlString(Object obj){
        if(obj instanceof String){
            return String.format("'%s'",obj);
        }else{
            return obj.toString();
        }
    }
}
