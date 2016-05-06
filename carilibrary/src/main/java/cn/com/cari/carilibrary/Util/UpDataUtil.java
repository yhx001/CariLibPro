package cn.com.cari.carilibrary.Util;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import cn.com.cari.carilibrary.DataBase.DataBaseUtil;

/**
 * @author zhouzhou
 * @Title: UpDataUtil
 * @Description:
 * @Company: www.cari.com.cn
 * @date 2015/12/22
 */
public class UpDataUtil {

    public static Class[] upClasss = new Class[]{};

    public static Object getDataList(Context context, Class cls) {
        HashMap<String,Object> item = new HashMap<>();
        String tempId = (String) SharedPreferencesHelper.getLongValue(context, cls.getSimpleName(), "-1");
        item.put(cls.getSimpleName(), DataBaseUtil.getData(context, cls, " where _id >" + tempId));
        return item;
    }

    /**
     * 获取更新数据集
     *
     * @param context
     * @return
     */
    public static ArrayList<Object> getUpDataList(Context context) {
        return getUpDataList(context, upClasss);
    }

    /**
     * 获取更新数据集
     *
     * @param context
     * @param cls
     * @return
     */
    public static ArrayList<Object> getUpDataList(Context context, Class[] cls) {
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < cls.length; i++) {
            objects.add(getDataList(context, cls[i]));
        }
        return objects;
    }


    public static void upTemp(Context context, Class cls) {
        int tmp = 0;
        //获取当前数据表最大 _id
        tmp = DataBaseUtil.getData(context, cls).size();
        //获取最大 _id
        String Sql ="SELECT max(_id) FROM "+cls.getSimpleName();
        String Id ="-1";
        ArrayList<HashMap<String,String>> datalist= DataBaseUtil.getValues(context, Sql);
        if(datalist.size()>0) {
             Id = datalist.get(0).get("max(_id)");
        }
        SharedPreferencesHelper.setLongValue(context, cls.getSimpleName(), Id);
    }

    /**
     * 更新数据标志位
     *
     * @param context
     */
    public static void upTemps(Context context) {
        Class[] cls =upClasss;
        for (int i = 0; i < cls.length; i++) {
            upTemp(context, cls[i]);
        }
    }
}
