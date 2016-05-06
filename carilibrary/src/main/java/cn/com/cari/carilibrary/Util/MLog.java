package cn.com.cari.carilibrary.Util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MLog {
	public static String exction;
	public static Context ctx;
	public static FileOutputStream fos;
   
	public MLog() {
		super();
	}
	/**
	 * 功能：记录日志
	 * @param savePathStr 保存日志路径
	 * @param saveFileNameS 保存日志文件名
	 * @param saveDataStr保存日志数据
	 * @param saveTypeStr 保存类型，false为覆盖保存，true为在原来文件后添加保存
	 */
	public static void recordLog(Context ctx,String savePathStr,String saveFileNameS,String saveDataStr,boolean saveTypeStr) {

		try {

			String saveFileName = savePathStr+saveFileNameS;

			String saveData = saveDataStr;
			boolean saveType =saveTypeStr;

			// 准备需要保存的文件
			
			File saveFile = new File(saveFileName+ saveFileNameS+".txt");
			if (!saveType && saveFile.exists()) {
				saveFile.delete();
				saveFile.createNewFile();
				// 保存结果到文件
				fos = new FileOutputStream(saveFile, saveType);
				fos.write(saveData.getBytes());
				fos.close();
				fos=null;
			} else if (saveType && saveFile.exists()) {
				//saveFile.createNewFile();
				fos = new FileOutputStream(saveFile, saveType);
				fos.write(saveData.getBytes());
				fos.close();
				fos=null;
			}else if (saveType && !saveFile.exists()) {
				saveFile.createNewFile();
				fos = new FileOutputStream(saveFile, saveType);
				fos.write(saveData.getBytes());
				fos.close();
				fos=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(fos!=null)
				try {
					fos.close();
					fos=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}


	}


}
