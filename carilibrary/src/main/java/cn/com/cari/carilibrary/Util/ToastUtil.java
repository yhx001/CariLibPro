package cn.com.cari.carilibrary.Util;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.com.cari.carilibrary.R;

/**
 * @author zhouzhou
 * @Title: ToastUtil
 * @Description:
 * @Company: www.cari.com.cn
 * @date 2016/1/7
 */
public class ToastUtil {
    public static Toast makeText(Context context, String str, int lengthLong) {
        Toast toast = Toast.makeText(context,
                str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
//        imageCodeProject.setImageResource(android.R.drawable.ic_menu_info_details);
        imageCodeProject.setImageResource(R.mipmap.alert);
        toastView.addView(imageCodeProject, 0);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return toast;
    }
}
