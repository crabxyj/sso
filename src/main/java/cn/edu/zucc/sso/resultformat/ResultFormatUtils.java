package cn.edu.zucc.sso.resultformat;

import cn.edu.zucc.sso.exception.BaseException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author crabxyj
 * @date 2019/12/23 16:08
 */
public class ResultFormatUtils {

    public static JSONObject ressetResult(Object obj,int code,String msg){
        JSONObject result = new JSONObject();
        if (obj instanceof List<?>) {
            result.put("rs",resetList((List<?>) obj));
        } else {
            result.put("r",obj);
        }
        result.put("code",code);
        result.put("msg",msg);
        return result;
    }

    public static JSONObject systemExceptionResult(Exception e){
        JSONObject result = new JSONObject();
        result.put("code",-1);
        result.put("msg",e.getMessage());
        return result;
    }

    public static JSONObject baseExceptionResult(BaseException e){
        JSONObject result = new JSONObject();
        result.put("code",e.getCode());
        result.put("msg",e.getMsg());
        return result;
    }

    private static JSONArray resetList(List<?> list) {
        JSONArray array = new JSONArray();
        array.addAll(list);
        return array;
    }

//    /**
//     * 判断一个对象是否是基本类型或基本类型的封装类型
//     */
//    private static boolean isPrimitive(Object obj) {
//        try {
//            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
