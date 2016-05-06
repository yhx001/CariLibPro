package cn.com.cari.carilibrary.Util;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static String PHOTO = "";
    public static String LOG = "LOG";
    private String SDPATH;
    // 下载
    public static String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "ch4" + File.separatorChar;

    private int FILESIZE = 4 * 1024;

    public String getSDPATH() {
        return SDPATH;
    }

    public FileUtil() {
        // 得到当前外部存储设备的目录( /SDCARD )
        SDPATH = DOWNLOAD_PATH;
    }

    /**
     * 在SD卡上创建文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     * @return
     */
    public File createSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 创建目录
     *
     * @param dirName
     * @return
     */
    public static File createDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    /**
     * 设置照片,日志等存储位置
     */
    public static String getPath(Context context, String Type) {
        String filepath;
//        if (Build.VERSION.SDK_INT < 18)// 4.4以上好像不能在SD卡创建文件夹
//        {
//            filepath = GetStoragePath(context, true);// 先考虑外部SD卡
//        } else
        filepath = GetStoragePath(context, false);// 使用内部存储卡
//        if (filepath == "") {
//            filepath = GetStoragePath(context, false);// 再考虑是否使用内部存储卡
//            if (filepath == "")
//                filepath = context.getFilesDir() + "";// 如果使用系统内存，则不能使用系统自带相机。。
//        }
        filepath = filepath + "/" + context.getApplicationInfo().packageName + "/";
        File file = new File(filepath + "/" + Type + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath() + "/";
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 获取内部存储卡地址 返回：内部存储卡路径，是可移除的SD卡还是不移除的sd卡，如果没有，则返回空
     *
     * @param context
     * @return
     */
    public static String GetStoragePath(Context context, boolean isRemoveable) {
        List<StorageInfo> list = listAvaliableStorage(context);

        boolean Removeable = isRemoveable;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isRemoveable == Removeable)// 无法卸载的为内部存储器
            {
                return list.get(i).path;
            }

        }
        return "";
    }

    /**
     * 获取到Android设备所有存储器
     *
     * @param context
     * @return
     */
    public static List<StorageInfo> listAvaliableStorage(Context context) {

        ArrayList<StorageInfo> storagges = new ArrayList<StorageInfo>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            getVolumeList.setAccessible(true);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
            if (invokes != null) {
                StorageInfo info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    info = new StorageInfo(path);
                    File file = new File(info.path);
                    if ((file.exists()) && (file.isDirectory()) && (file.canWrite())) {
                        Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
                        String state = null;
                        try {
                            Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                            state = (String) getVolumeState.invoke(storageManager, info.path);
                            info.state = state;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (info.isMounted()) {
                            info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                            storagges.add(info);
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        storagges.trimToSize();

        return storagges;
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) { //如果文件存在
            file.delete();
        }
    }

    /**
     * 复制文件
     *
     * @param file
     */
    public static void copyDBFile(Context context,String DataBaseName) {
        File file = new File("/data/data/"+context.getPackageName()+"/databases/"+DataBaseName);
        System.out.println(file.getAbsolutePath());
        File f = new File(Environment.getExternalStorageDirectory().getPath()+"/"+ file.getName());
        int len = 0;
        try {
            FileInputStream fin = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(f);
            byte buffer[] = new byte[4096];
            while ((len = fin.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fin.close();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("copy database","Success copy database file" );
    }


}
