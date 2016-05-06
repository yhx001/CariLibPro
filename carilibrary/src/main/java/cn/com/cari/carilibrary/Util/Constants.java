package cn.com.cari.carilibrary.Util;

import java.util.ArrayList;


public class Constants {


	// 此处填入默认本数据库名称
	public static String DATABASE_NAME = "CariMobile.db";


	// 此处填入当前创建的数据库类版本
	public static int DATABASE_VERSION = 1;

	// 此处填入日志文件的路径
	public static String LogPath = "";

	// 此处填入日志文件的名称
	public static String LogName = "";

	public static String SHARED_PREFERENCES = "BaseInfo";
	
	public static String ch4StoreShareString = "ch4StoreShareString";//CD4相关

	public static String USER_NAME = "";// 登陆用户名


	public static String USER = "";// 实际用户名

	public static String USER_ROLE = "";// 用户角色

	public static String USER_DEPID = "";// 用户部门

	public static String USER_DEPNAME = "";// 用户部门名称

	public static boolean LOGIN_STATE = false;// 登陆状态

	public static String NAMESPACE = "http://tempuri.org/";

	public static String SERVER_IP = "192.168.12.127";// IP:10.27.8.213

	public static String SERVER_IP_PUSH = "192.168.12.188";// IP:10.27.8.213

	public static String SERVER_1 = "gasapp/Gas/Client/PDAService.ashx";// 查询页面

	public static String SERVER_2 = "user/WebService1.asmx";// 人员登陆

	public static String SERVER_url = "http://60.208.27.154:8011/SafetyNet/SafetyWebService2.asmx";

	public static String LOCATION_url = "http://60.208.27.154:8011/TDRJNETWEB/WebServiceJson.asmx";
	// http://60.208.27.154:8011/AppWebService/WebService.asmx
	public static String Server_url_v1 = "http://60.208.27.154:8011/AppWebService/WebService.asmx";
	public static String DEVICE_IMEI = "";

	public static String PHOTOPATH = "/sdcard/Photo/";

	public final static int INTENT_RESULT_OK = 1000;
	public final static int INTENT_RESULT_FAILD = 1001;
	public final static String INTENT_RESULT_INFO = "";
	public final static String INTENT_RESULT_ERROR = "";
	
	public static String BTMac;//最近一次连接的蓝牙地址

	// chose Depart 当前部门
	public final static int CHOSE_DEPART = 1002;

	public static int Now_Line = 0;
	// chose Depart 当前巡检结果
	public static String[] Now_resultStr = null;
	// public static ArrayList<ResultBean> Now_resultList = null;

	public static ArrayList<String> XList; // x point
}
