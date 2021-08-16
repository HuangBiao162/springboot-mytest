package normal.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * ObjectUtils
 *
 * @author hetao
 * @date 2018-09-10
 */
public class ObjectUtils extends cn.hutool.core.util.ObjectUtil {
    /**
     * 字符串数据处理
     */
    public static String valueAsStr(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

    /**
     * 整型数据处理
     */
    public static Integer valueAsInt(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            if ("NaN".equals(value)) {
                return null;
            }
            return Integer.valueOf((String) value);
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? 1 : 0;
        } else {
            return null;
        }
    }

    /**
     * 无用对象置为空
     */
    public static void setEmpty(Object value) {
        if (ObjectUtils.isNotNull(value)) {
            value = null;
        }
    }

    /**
     * @Author haoxiang_guo
     * @Description 使用Gson判断两个字符串是否完全一致
     * @Date 17:44 2019/11/2
     * @Param [json, json]
     * @return Boolean
     **/
    public static Boolean isJsonEqual(String json1, String json2) {
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(json1);
        JsonParser parser1 = new JsonParser();
        JsonObject obj1 = (JsonObject) parser1.parse(json2);
        return obj.equals(obj1);
    }
}
