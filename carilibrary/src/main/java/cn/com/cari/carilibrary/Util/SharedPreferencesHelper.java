/**
 *
 */
package cn.com.cari.carilibrary.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author zhouzhou
 * @Title: SharedPreferencesCommon
 * @Description:
 * @Company: www.cari.com.cn
 * @date 2015-9-24 上午11:08:14
 */
public class SharedPreferencesHelper {

    private static String TEMP_STORAGE = "TEMP_STORAGE";//临时存储
    private static String LONG_STORAGE = "LONG_STORAGE";//长期存储

    /**
     * 长期存储
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setLongValue(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(LONG_STORAGE, Context.MODE_PRIVATE);
        Editor ed = sp.edit();
        if (value.getClass().equals(String.class)) {
            ed.putString(key, (String) value);
        } else if (value.getClass().equals(boolean.class)) {
            ed.putBoolean(key, (Boolean) value);
        } else if (value.getClass().equals(Integer.class)) {
            ed.putInt(key,Integer.parseInt(value.toString()));
        }
        ed.commit();
    }

    /**
     * 设置临时存储
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setTempValue(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(TEMP_STORAGE, Context.MODE_PRIVATE);
        Editor ed = sp.edit();
        if (value.getClass().equals(String.class)) {
            ed.putString(key, (String) value);
        } else if (value.getClass().equals(boolean.class)) {
            ed.putBoolean(key, (Boolean) value);
        } else if (value.getClass().equals(Integer.class)) {
            ed.putInt(key,Integer.parseInt(value.toString()));
        }
        ed.commit();
    }

    /**
     * 获取临时表中的值
     *
     * @param context
     * @param key
     * @param DefultValue
     * @return
     */
    public static Object getTempValue(Context context, String key, Object DefultValue) {
        SharedPreferences sp = context.getSharedPreferences(TEMP_STORAGE, Context.MODE_PRIVATE);
        if (DefultValue.getClass().equals(String.class)) {
            return sp.getString(key, (String) DefultValue);
        } else if (DefultValue.getClass().equals(Boolean.class)) {
            return sp.getBoolean(key, (boolean) DefultValue);
        } else if (DefultValue.getClass().equals(Integer.class)) {
            return sp.getInt(key,Integer.parseInt(DefultValue.toString()));
        } else {
            return null;
        }
    }

    public static Object getLongValue(Context context, String key, Object DefultValue) {
        SharedPreferences sp = context.getSharedPreferences(LONG_STORAGE, Context.MODE_PRIVATE);
        if (DefultValue.getClass().equals(String.class)) {
            return sp.getString(key, (String) DefultValue);
        } else if (DefultValue.getClass().equals(boolean.class)) {
            return sp.getBoolean(key, (boolean) DefultValue);
        } else if (DefultValue.getClass().equals(Integer.class)) {
            return sp.getInt(key, Integer.parseInt(DefultValue.toString()));
        } else {
            return null;
        }
    }

    /**
     * 清空临时存储
     *
     * @param context
     */
    public static void clearTempValue(Context context) {
        SharedPreferences sp = context.getSharedPreferences(TEMP_STORAGE, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


}
