package cn.edu.zucc.sso.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author crabxyj
 * @date 2019/12/26 9:37
 */
public class PortPermissionController {

    private final WebApplicationContext applicationContext;

    public PortPermissionController(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @RequestMapping("/urls")
    public List<String> getAllUrls(){
        RequestMappingHandlerMapping mapping = applicationContext.getBean("requestMappingHandlerMapping",RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> methods = mapping.getHandlerMethods();
        List<String> urls = new LinkedList<>();
        for(Map.Entry<RequestMappingInfo,HandlerMethod> m : methods.entrySet()){
            HandlerMethod method = m.getValue();
            //类名
            String className = method.getMethod().getDeclaringClass().getName();
            if (!className.startsWith("cn.edu.zucc.sso")){
                continue;
            }
            PatternsRequestCondition condition = m.getKey().getPatternsCondition();
            urls.addAll(condition.getPatterns());
        }
        return urls;
    }


    @RequestMapping("/urlDetails")
    public JSONArray getAllUrlDetail(){

        RequestMappingHandlerMapping mapping = applicationContext.getBean("requestMappingHandlerMapping",RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> methods = mapping.getHandlerMethods();
        JSONArray ja = new JSONArray();
        for(Map.Entry<RequestMappingInfo,HandlerMethod> m : methods.entrySet()){
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition condition = info.getPatternsCondition();
            //类名
            String className = method.getMethod().getDeclaringClass().getName();
            if (!className.startsWith("cn.edu.zucc.sso")){
                continue;
            }
            String methodName = method.getMethod().getName();

            JSONObject json = new JSONObject();
            json.put("urls",condition.getPatterns());
            json.put("className",className);
            json.put("methodName",methodName);
            ja.add(json);
        }
        return ja;
    }

}
