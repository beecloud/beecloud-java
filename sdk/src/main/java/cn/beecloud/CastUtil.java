package cn.beecloud;

import java.util.List;
import java.util.Map;


/**
 * Created by BeeCloud on 2015/12/8.
 */
public class CastUtil {

    public static Integer object2Integer(Object object) {
        if (object instanceof Integer)
            return (Integer) object;
        return null;
    }

    public static Boolean object2Boolean(Object object) {
        if (object instanceof Boolean)
            return (Boolean) object;
        return null;
    }

    public static Long object2Long(Object object) {
        if (object instanceof Long)
            return (Long) object;
        return null;
    }

    public static Map<String, Object> object2MapStringObject(Object object) {
        Map<String, Object> ret = null;
        if (object instanceof Map) {
            try {
                ret = (Map<String, Object>) object;
            } catch (ClassCastException cce) {
                ret = null;
                System.out.println(cce.getMessage());
            }
        }
        return ret;
    }

    public static Map<String, String> object2MapStringString(Object object) {
        Map<String, String> ret = null;
        if (object instanceof Map) {
            try {
                ret = (Map<String, String>) object;
            } catch (ClassCastException cce) {
                ret = null;
                System.out.println(cce.getMessage());
            }
        }
        return ret;
    }

    public static List<Map<String, Object>> object2ListMap(Object object) {
        List<Map<String, Object>> ret = null;
        if (object instanceof List) {
            try {
                ret = (List<Map<String, Object>>) object;
            } catch (ClassCastException cce) {
                ret = null;
                System.out.println(cce.getMessage());
            }
        }
        return ret;
    }
}
