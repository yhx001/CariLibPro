package cn.com.cari.carilibrary.Util;

import android.content.Context;

/**
 * @author zhouzhou
 * @Title: UserStateUtil
 * @Description:判断用户相关
 * @Company: www.cari.com.cn
 * @date 2015/11/25
 */
public class UserUtil {

    /**
     * 判断当前用户是否为登录状态
     *
     * @param context
     * @return true 是登录；false尚未登录
     */
    public static boolean isLogin(Context context) {
        boolean reslut = false;
        if (!((String) SharedPreferencesHelper.getTempValue(context, AppUtil.USER_ID, "-1")).equals("-1")) { //当前用户未登录
            reslut = true;
        }
        return reslut;
    }
}
