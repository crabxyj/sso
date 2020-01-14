package cn.edu.zucc.sso.resultformat;

import cn.edu.zucc.sso.exception.BaseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author crabxyj
 * @date 2019/12/23 16:08
 */
@Slf4j
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

    static JSONObject ressetResult(Object obj, ResultFormat format) {
//        if (format != null && (format.include().length>0||format.exclude().length>0)) {
//            String s = JSONObject.toJSONString(obj, new PropertyFilter(format));
//            obj = JSONObject.parseObject(s, obj.getClass());
//        }
        return ressetResult(obj);
    }

    private static JSONObject ressetResult(Object obj, int code, String msg) {
        JSONObject result = new JSONObject();
        if (obj instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List) obj;
            result.put("rs", handListType(list));
        } else if (obj instanceof IPage) {
            IPage iPage = (IPage) obj;
            JSONObject json = new JSONObject();
            @SuppressWarnings("unchecked")
            List<Object> rs = iPage.getRecords();
            json.put("rs", handListType(rs));
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

    static JSONObject baseExceptionResult(BaseException e) {
        JSONObject result = new JSONObject();
        result.put("code", 3000);
        result.put("msg", e.getMessage());
        return result;
    }

    static JSONObject sysExceptionResult() {
        return SYS_EXCEPTION;
    }

    private static Object dataHandle(Object value) {
        try {
            if (value instanceof List) {
                @SuppressWarnings("unchecked")
                List<Object> list = (List<Object>) value;
                return ResultFormatUtils.handListType(list);
            } else if (value instanceof IPage) {
                @SuppressWarnings("unchecked")
                IPage<Object> iPage = (IPage<Object>) value;
                return ResultFormatUtils.handPage(iPage);
            } else {
                return ResultFormatUtils.handObject(value);
            }
        } catch (Exception e) {
            return value;
        }
    }


    private static List<Object> handListType(List<Object> values) {
        if (values != null) {
            values = values.stream()
                    .map(ResultFormatUtils::dataHandle)
                    .collect(Collectors.toList());
        }
        return values;
    }

    private static IPage<Object> handPage(IPage<Object> page) {
        List<Object> list = ResultFormatUtils.handListType(page.getRecords());
        page.setRecords(list);
        return page;
    }

    /**
     * 去除null值
     */
    private static Object handObject(Object value) {
        return JSON.parseObject(JSON.toJSONString(value));
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
