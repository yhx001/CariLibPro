package cn.com.cari.carilibrary.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
	/**
	 * 存储节点值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void SavNode(Context context, String key, String value) {
		try {
			SharedPreferences sp = context.getSharedPreferences(Constants.SHARED_PREFERENCES, 0);
			sp.edit().putString(key, value).commit();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 设置ID戳
	 * 
	 * @param context
	 * @param value
	 */
	public static void SetMaxIDStmp(Context context, String value) {
		SavNode(context, "IDSTMP", value);
	}
	/**
	 * 修改权限
	 * 
	 * @param f
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void changeMode(File f) throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec("chmod 777 " + f.getAbsolutePath());
		int status = p.waitFor();
		if (status == 0) {
			System.out.println("sucess");
		} else {
			System.out.println("fail");
		}
	}

	// //获取部门名称
	// public static String GetDepName(Context context,String Depid)
	// {
	// String DepName="";
	// try {
	// List<String> Dep = getColumnData(context,
	// MineColumns.Department.CONTENT_URI,
	// MineColumns.Department.DepartmentName,
	// MineColumns.Department.DepartID+"=?", new String[]{Depid}, null);
	// if (Dep.size() > 0) {
	// DepName = Dep.get(Dep.size() - 1);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return DepName;
	// }

	public static List<String> getColumnData(Context ctx, Uri uri, String column, String selection, String[] selectionArgs, String SortOrder) {
		List<String> columnsStringList = new ArrayList<String>();

		Cursor cursor = ctx.getContentResolver().query(uri, null, selection, selectionArgs, SortOrder);

		int columnID = cursor.getColumnIndex(column);// 取得字段的ID值
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

			columnsStringList.add(cursor.getString(columnID));

		}
		if (cursor != null) {
			cursor.close();
		}
		return columnsStringList;
	}


	/**
	 * 获取服务器地址
	 */
	@SuppressWarnings("static-access")
	public static String GetSerURL(Context context) {
		String url = "http://10.27.8.213";
		SharedPreferences sp = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
		if (!sp.contains("SERVER_IP")) {
			Editor editor = sp.edit();
			editor.putString("SERVER_IP", url);
			editor.commit();
		}
		url = sp.getString("SERVER_IP", url);
		return url;
	}

	/**
	 * 获取服务器http
	 * 
	 * @param context
	 * @return
	 */
	public static String GetHttpUrl(Context context) {
		String url = "http://10.27.8.213";
		String tmp = GetSerURL(context);
		if (tmp.contains("http"))
			url = GetSerURL(context) + "/";
		else
			url = "http://" + GetSerURL(context) + "/";
		return url;
	}

	/**
	 * 获取服务器地址
	 * 
	 * @param context
	 * @return
	 */
	public static String GetURL(Context context, String server) {
		String url = "http://10.27.8.213/jxwl/WebServiceForAndroid.asmx";
		url = GetHttpUrl(context) + server;
		return url;
	}

	/**
	 * 获取服务器网页地址
	 * 
	 * @param context
	 * @return
	 */
	public static String GetWebURL(Context context) {
		// String url = "http://192.168.12.251/jxwl/WebForm1.aspx";
		String url = "http://10.27.8.213/jxwl/WebForm1.aspx";
		url = GetHttpUrl(context) + "jxwl/WebForm1.aspx";
		return url;

	}


	
}
