package cn.edu.zucc.sso.resultformat;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author crabxyj
 * @date 2019/12/30 21:23
 */
public class PropertyFilter implements PropertyPreFilter {
    private Class<?> clazz;
    private Set<String> includes;
    private Set<String> excludes;

    public PropertyFilter(ResultFormat format){
        this(format.clazz()==Object.class?null:format.clazz(),
                Stream.of(format.include()).collect(Collectors.toSet()),
                Stream.of(format.exclude()).collect(Collectors.toSet()));
    }

    public PropertyFilter(Class<?> clazz, Collection<String> includes,Collection<String> excludes ){
        super();
        this.clazz = clazz;
        this.includes = includes.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        this.excludes = new HashSet<>(excludes);
    }

    @Override
    public boolean apply(JSONSerializer jsonSerializer, Object o, String s) {
        if (o==null){
            return true;
        }
        if (clazz!=null && !clazz.isInstance(o) ){
            return true;
        }
        if (excludes.contains(s)){
            return false;
        }
        return includes.size() == 0 || includes.contains(s);
    }
}
