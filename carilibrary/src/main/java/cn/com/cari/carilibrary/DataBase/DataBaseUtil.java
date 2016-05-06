
package cn.com.cari.carilibrary.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import cn.com.cari.carilibrary.Util.GsonUtil;
import cn.com.cari.carilibrary.Util.ReflectionHelper;

/**
 * @author zhouzhou
 * @Title: SqliteHelper
 * @Description:
 * @Company: www.cari.com.cn
 * @date 2015-9-24 下午5:03:18
 */
public class DataBaseUtil {


    /**
     * 清空数据表
     *
     * @param context
     * @param cls
     */
    public static void clearTable(Context context, Class cls) {

        DBHelper dbhelper = new DBHelper(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase(); // 上传id大于多少的....
        try {
            db.execSQL("delete from " + cls.getSimpleName());
            Log.i(context.getClass().getSimpleName(), "Success delete from" + cls.getSimpleName());
        } catch (Exception ex) {

        } finally {
            db.close();
            dbhelper.close();
        }
    }


    /**
     * 获取指定Sql数据
     *
     * @param context
     * @param cls
     * @param Sql
     * @return
     */
    public static ArrayList<Object> getBaseData(Context context, Class cls, String Sql) {
        ArrayList<Object> resultList = new ArrayList<>();
        try {
            DBHelper dbhelper =new DBHelper(context);
            SQLiteDatabase db = dbhelper.getReadableDatabase(); // 上传id大于多少的....
            Cursor cursor = db.rawQuery(Sql, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Constructor ct = cls.getConstructor(null);
                Object obj = ct.newInstance(null);
                for (int k = 0; k < cursor.getColumnCount(); k++) {
                    String dataName = cursor.getColumnName(k);
                    Field classDeclaredName = cls.getDeclaredField(dataName);
                    String methodName ="set"+dataName.substring(0,1).toUpperCase()+dataName.substring(1);
                    if (classDeclaredName != null && !cursor.isNull(k)) {
                        int type = cursor.getType(k);
                        switch (type) {
                            case Cursor.FIELD_TYPE_STRING:
                                ReflectionHelper.getMethod(methodName, obj, new Object[]{cursor.getString(k)});
                                break;
                            case Cursor.FIELD_TYPE_BLOB:
                                ReflectionHelper.getMethod(methodName, obj, new Object[]{cursor.getBlob(k)});
                                break;
                            case Cursor.FIELD_TYPE_FLOAT:
                                ReflectionHelper.getMethod(methodName, obj, new Object[]{cursor.getFloat(k)});
                                break;
                            case Cursor.FIELD_TYPE_INTEGER:
                                ReflectionHelper.getMethod(methodName, obj, new Object[]{(double) cursor.getInt(k)});
                                break;
                            case Cursor.FIELD_TYPE_NULL:
                                break;
                            default:
                                break;
                        }
                    }
                }
                resultList.add(obj);
            }
            if (cursor != new Object()) {
                cursor.close();
            }
            db.close();
            dbhelper.close();
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("SQLite ERROR", e.getMessage().toString());
        }
        return resultList;
    }


    /**
     * 反射机制，从数据库到数据表
     *
     * @param context
     * @param cls
     * @param sqlStr  查询条件
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Object> getData(Context context, Class cls, String sqlStr) {
        String selectStr = cls.getDeclaredFields()[0].getName();
        for (int i = 1; i < cls.getDeclaredFields().length; i++) {
            selectStr = selectStr + "," + cls.getDeclaredFields()[i].getName();
        }
        String selectSql = "SELECT " + selectStr + " FROM " + cls.getSimpleName() + " " + sqlStr;
        return getBaseData(context, cls, selectSql);
    }

    /**
     * 反射机制，从数据库到数据表
     *
     * @param context
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Object> getData(Context context, Class cls) {
        String selectStr = cls.getDeclaredFields()[0].getName();
        for (int i = 1; i < cls.getDeclaredFields().length; i++) {
            selectStr = selectStr + "," + cls.getDeclaredFields()[i].getName();
        }
        String selectSql = "SELECT " + selectStr + " FROM " + cls.getSimpleName();
        return getBaseData(context, cls, selectSql);
    }

    /**
     * 向数据库中插入数据ArrayList
     *
     * @param context
     * @param cls
     * @param JsonStr
     */

    public static void saveArrayListData(Context context, Class<?> cls, String JsonStr) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase dataBase = db.getWritableDatabase();
        try {
            JSONArray jsonArray = new JSONArray(JsonStr);
            dataBase.beginTransaction(); // 手动设置开始事务
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemdata = (JSONObject) jsonArray.get(i);
                ContentValues cv = new ContentValues();
                Field[] fields = cls.getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    String getDeclaredFieldName = fields[j].getName().toString();
                    if (!itemdata.isNull(getDeclaredFieldName)) {
                        cv.put(getDeclaredFieldName, itemdata.getString(getDeclaredFieldName).trim());
                    }
                }
                dataBase.insert(cls.getSimpleName(), null, cv);
            }
            dataBase.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
            Log.i("SQLite", "Success insert info into SqliteDatabase " + cls.getSimpleName());

        } catch (Exception e) {
            Log.e("SQLite ERROR " + cls.getSimpleName(), e.getMessage().toString());
        } finally {
            dataBase.endTransaction(); // 处理完成
            dataBase.close();
            db.close();
        }
    }

    /**
     * 向数据库中插入数据JSONArray
     *
     * @param context
     * @param cls
     * @param jsonArray 插入实例集合
     */
    public static void saveArrayListData(Context context, Class<?> cls, JSONArray jsonArray) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase dataBase = db.getWritableDatabase();
        try {
            dataBase.beginTransaction(); // 手动设置开始事务
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemdata = (JSONObject) jsonArray.get(i);
                ContentValues cv = new ContentValues();
                Field[] fields = cls.getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    String getDeclaredFieldName = fields[j].getName().toString();
                    if (!itemdata.isNull(getDeclaredFieldName)) {
                        cv.put(getDeclaredFieldName, itemdata.getString(getDeclaredFieldName).trim());
                    }
                }
                dataBase.insert(cls.getSimpleName(), null, cv);
            }
            dataBase.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
            Log.i("SQLite", "Success insert info into SqliteDatabase " + cls.getSimpleName());

        } catch (Exception e) {
            Log.e("SQLite ERROR " + cls.getSimpleName(), e.getMessage().toString());
        } finally {
            dataBase.endTransaction(); // 处理完成
            dataBase.close();
            db.close();
        }
    }

    /**
     * 向数据库中插入数据ArrayList
     *
     * @param context
     * @param cls
     * @param obj     插入实例集合
     */
    public static void saveArrayListData(Context context, Class<?> cls, Object obj) {
        String JsonStr = GsonUtil.toJson(obj);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(JsonStr);
            saveArrayListData(context, cls, jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向数据库中插入单个数据
     *
     * @param context
     * @param cls
     * @param obj
     */
    public static void saveObjectData(Context context, Class<?> cls, Object obj) {
        String JsonStr = GsonUtil.toJson(obj);
        DBHelper db = new DBHelper(context);
        SQLiteDatabase dataBase = db.getWritableDatabase();
        try {
            JSONObject jsonObject = new JSONObject(JsonStr);
            ContentValues cv = new ContentValues();
            Field[] fields = cls.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                String getDeclaredFieldName = fields[j].getName().toString();
                if (!jsonObject.isNull(getDeclaredFieldName)) {
                    cv.put(getDeclaredFieldName, jsonObject.getString(getDeclaredFieldName).trim());
                }
            }
            dataBase.insert(cls.getSimpleName(), null, cv);
            Log.i("SQLite", "Success insert info into SqliteDatabase " + cls.getSimpleName());

        } catch (Exception e) {
            Log.e("SQLite ERROR " + cls.getSimpleName(), e.getMessage().toString());
        } finally {
            dataBase.close();
            db.close();
        }
    }

    /**
     * 执行具有返回的Sql语句
     *
     * @param cxt
     * @param sqlStr
     * @return
     */
    public static ArrayList<HashMap<String, String>> getValues(Context cxt, String sqlStr) {
        ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
        DBHelper db = new DBHelper(cxt);
        SQLiteDatabase dataBase = db.getWritableDatabase();
        try {
            Cursor cursor = dataBase.rawQuery(sqlStr, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                HashMap<String, String> item = new HashMap<>();
                for (int k = 0; k < cursor.getColumnCount(); k++) {
                    if (!cursor.isNull(k)) {
                        item.put(cursor.getColumnName(k), cursor.getString(k));
                    }
                }
                if (item.size() > 0) {
                    resultList.add(item);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            dataBase.close();
            db.close();
        }
        return resultList;
    }

    /**
     * 更新数据表中数据
     *
     * @param context
     * @param table
     * @param changedColum
     * @param value
     * @param whereargs
     */
    public static void upTableItem(Context context, Class table, String changedColum, String value, String whereargs) {
        //updata Table set XX= XX where XX=XX
        DBHelper db = new DBHelper(context);
        SQLiteDatabase dataBase = db.getWritableDatabase();
        try {
            dataBase.execSQL("UPDATE " + table.getSimpleName() + " SET " + changedColum + " = '" + value + "'" + whereargs);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            dataBase.close();
            db.close();
        }
    }

    /**
     * 向数据库中插入单个数据
     *
     * @param context
     * @param cls
     * @param obj
     */
    public static void upObject(Context context, Class<?> cls, Object obj) {
        String JsonStr = GsonUtil.toJson(obj);
        DBHelper db = new DBHelper(context);
        SQLiteDatabase dataBase = db.getWritableDatabase();
        try {
            JSONObject jsonObject = new JSONObject(JsonStr);
            String whereItem = obj.getClass().getDeclaredFields()[1].getName();
            ContentValues cv = new ContentValues();
            Field[] fields = cls.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                String getDeclaredFieldName = fields[j].getName().toString();
                if (!jsonObject.isNull(getDeclaredFieldName)) {
                    cv.put(getDeclaredFieldName, jsonObject.getString(getDeclaredFieldName));
                }
            }
            dataBase.update(cls.getSimpleName(), cv, whereItem + "=?", new String[]{jsonObject.getString(whereItem)});
            Log.i("SQLite", "Success updata SqliteDatabase " + cls.getSimpleName());
        } catch (Exception e) {
            Log.e("SQLite ERROR " + cls.getSimpleName(), e.getMessage().toString());
        } finally {
            dataBase.close();
            db.close();
        }
    }

    /**
     * execSql
     * @param context
     * @param Str
     */
    public static void execSql(Context context,String Str){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(Str);
        sqLiteDatabase.close();
        dbHelper.close();
    }
}
