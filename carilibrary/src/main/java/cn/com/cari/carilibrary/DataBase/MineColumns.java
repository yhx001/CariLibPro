package cn.com.cari.carilibrary.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

public class MineColumns {
	// 区域表
	// 部门表
	public static final String AUTHORITY = "carimobilepro.provider";

	public static final class Authority implements BaseColumns { // 区域表
		public static final String TABLENAME = "Authority";
		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ AUTHORITY + "/" + TABLENAME);
		public static final String ModuleName = "ModuleName"; 
		public static final String ModuleCode = "ModuleCode"; 
		public static final String IP = "IP"; 
		
	}

	
}
