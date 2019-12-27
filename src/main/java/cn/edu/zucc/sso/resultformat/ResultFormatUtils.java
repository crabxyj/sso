package cn.edu.zucc.sso.resultformat;

import cn.edu.zucc.sso.exception.BaseException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author crabxyj
 * @date 2019/12/23 16:08
 */
public class ResultFormatUtils {
    private static final JSONObject SYS_EXCEPTION;

    static {
        SYS_EXCEPTION = new JSONObject();
        SYS_EXCEPTION.put("code", 3001);
        SYS_EXCEPTION.put("msg", "系统繁忙");
    }

    public static JSONObject ressetResult(Object obj) {
        return ressetResult(obj, 0, "success");
    }

    public static JSONObject ressetResult(Object obj, int code, String msg) {
        JSONObject result = new JSONObject();
        if (obj instanceof List<?>) {
            result.put("rs", resetList((List<?>) obj));
        } else if (obj instanceof IPage) {
            IPage iPage = (IPage) obj;
            JSONObject json = new JSONObject();
            json.put("rs", iPage.getRecords());
            json.put("pages", iPage.getPages());
            json.put("pageIndex", iPage.getCurrent());
            json.put("pageSize", iPage.getSize());
            json.put("totalCount", iPage.getTotal());
            result.put("r", json);
        } else if (obj != null) {
            result.put("r", obj);
        }
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static JSONObject baseExceptionResult(BaseException e) {
        JSONObject result = new JSONObject();
        result.put("code", 3000);
        result.put("msg", e.getMessage());
        return result;
    }

    public static JSONObject sysExceptionResult() {
        return SYS_EXCEPTION;
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
