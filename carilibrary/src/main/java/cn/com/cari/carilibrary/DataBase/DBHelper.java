package cn.com.cari.carilibrary.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;

import cn.com.cari.carilibrary.Util.AppUtil;
import cn.com.cari.carilibrary.Util.Constants;
import cn.com.cari.carilibrary.Util.MachineHelper;

public class DBHelper extends SQLiteOpenHelper {

    /**
     * 程序的开始，创建数据表
     *
     * @param context
     * @param tables
     */
    public static void createTables(Context context, Class<?>[] tables) {
        DBHelper.ClassList = tables;
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.close();
    }

    // 默认本数据库名称
    private static final String DATABASE_NAME = Constants.DATABASE_NAME;

    // 表集合
    public static Class<?>[] ClassList = new Class[]{};

    /**
     * 数据库版本升级
     */
    private static final int DATABASE_VERSION = Constants.DATABASE_VERSION;

    /**
     * @param context
     */
    public DBHelper(Context context) {
        super(context, AppUtil.packageName(context), null, AppUtil.getVersion(context));
        int versionCode = MachineHelper.getVersionCode(context);
        Constants.DATABASE_VERSION = versionCode;

    }

    /**
     * @param context
     * @param dataName
     */
    public DBHelper(Context context, String dataName) {
        //
        super(context, dataName, null, DATABASE_VERSION);
    }

    /**
     * @param context
     * @param dataName
     * @param version
     */
    public DBHelper(Context context, String dataName, int version) {
        //
        super(context, dataName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i("Sqlite", "Create dataBase**********************");
            if (ClassList!=null&&ClassList.length > 0) {
                for (int i = 0; i < ClassList.length; i++) {
                    String tableName = ClassList[i].getSimpleName();
                    Field[] declaredFields = ClassList[i].getDeclaredFields(); // 加局部变量
                    // 遍历方法集合
                    Log.i("OrmHelper", "=== start traversing getXX methods====");
                    // 如果类里面的变量数目大于0,新建基础表
                    if (declaredFields.length > 0) {
                        db.execSQL("DROP TABLE IF EXISTS " + tableName);
                        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT )");
                        for (int m = 0; m < declaredFields.length; m++) { //针对类里每个属性增加表的各个字段
                            Class<?> cl = declaredFields[m].getType();
                            String TypeName = cl.getSimpleName();
                            if (TypeName.equals("int") || TypeName.equals("double") || TypeName.equals("float")) {
                                db.execSQL("ALTER TABLE " + tableName + " ADD '" + declaredFields[m].getName() + "' INTEGER");
                            } else {
                                db.execSQL("ALTER TABLE " + tableName + " ADD '" + declaredFields[m].getName() + "' VARCHAR");
                            }
                        }
                    }
                    Log.i("OrmHelper", "=== create Table =" + tableName + "===");
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.i("Set_ZeroError", e.getClass().toString());
        }
    }

    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Sqlite", "ALTER dataBase *******************************");
        if (!AppUtil.isDebuge) {
            if (oldVersion < DATABASE_VERSION) {
                try {
//                    onCreate(db);
                    UpdateDB(db);
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("Sql_error", e.getMessage().toString());
                }
            }
        }
    }

    /**
     * 重置数据
     */
    public void SetZero(String TableName) {
        try {
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("Set_ZeroError", e.getClass().toString());
        }
    }

    public static void DeleteDataBase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    /**
     * 快捷建表 表名 Equip
     * 例：DBHelper.CreateTable(CariAndroidOrmPronActivity.this,Equip.class);
     *
     * @param userClass
     */
    public static void createTable(Context context, Class<?> userClass) {
        try {
            DBHelper db = new DBHelper(context);
            SQLiteDatabase database = db.getWritableDatabase();
            String tableName = userClass.getSimpleName();
            Field[] methods = userClass.getDeclaredFields(); // 加载变量属性
            // 遍历方法集合
            Log.i("OrmHelper", "=== start traversing getXX methods====");
            // 如果类里面的变量数目大于0,新建基础表
            if (methods.length > 0) {
                database.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (_id INTEGER PRIMARY KEY AUTOINCREMENT )");
            }
            for (int i = 0; i < methods.length; i++) {
                Class<?> cl = methods[i].getType();
                String TypeName = cl.getSimpleName();
                if (TypeName.equals("int")) {
                    database.execSQL("ALTER TABLE " + tableName + " ADD '" + methods[i].getName() + "' int");
                } else {
                    database.execSQL("ALTER TABLE " + tableName + " ADD '" + methods[i].getName() + "' VARCHAR");
                }
            }
            database.close();
            db.close();
            Log.i("OrmHelper", "=== end ====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 升级数据库
     */
    public void UpdateDB(SQLiteDatabase db) {
        if (ClassList.length > 0) {
            for (int i = 0; i < ClassList.length; i++) {
                String tableName = ClassList[i].getSimpleName();
                Field[] methods = ClassList[i].getDeclaredFields(); // 加载变量属性,加载所有字段
                CompareTableAndCreate(db, tableName, methods);
            }
        }
    }

    /**
     * 对比需要生成的数据库表与设备中当前存在的数据库表的差别，并对有差别的表进行删除和重建
     */
    private static void CompareTableAndCreate(SQLiteDatabase db, String table, Field[] methods) {
        // if(clear)
        // db.execSQL("drop table if exists "+table);
        String sql = "_id INTEGER PRIMARY KEY AUTOINCREMENT";
        for (int i = 0; i < methods.length; i++) {
            Class<?> cl = methods[i].getType();
            String TypeName = cl.getSimpleName();
            if (TypeName.equals("int"))
                sql = sql + "," + methods[i].getName() + " int";
            else
                sql = sql + "," + methods[i].getName() + " VARCHAR";
        }

        // 判断新数据库的字段是否与旧数据库一致，不一致则删除重建
        boolean a = true;
        for (int j = 0; j < methods.length; j++) {
            a = checkColumnExist(db, table, methods[j].getName());
            if (a == false)
                break;
        }
        if (a == false) {
            Log.i("CompareTableAndCreate", table);
            db.execSQL("drop table if exists " + table);
            db.execSQL("create table if not exists " + table + "(" + sql + ")");
        } else
            db.execSQL("create table if not exists " + table + "(" + sql + ")");

    }

    /**
     * 判断需要生成的表中的字段与数据库中已有的表的字段是否一致
     */
    static public boolean checkColumnExist(SQLiteDatabase db, String tableName, String columnName) {
        boolean result = true;
        Cursor cursor = null;
        int index = -1;
        try {
            // 查询一行
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            if (cursor != null) {
                index = cursor.getColumnIndex(columnName);
                if (index == -1)
                    result = false;
            } else
                result = false;
        } catch (Exception e) {
            Log.i("checkColumnExistError", e.getMessage());
            result = false;
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

}
