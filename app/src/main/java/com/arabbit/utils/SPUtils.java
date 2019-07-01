package com.arabbit.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.arabbit.application.BaseApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * @author admin
 */
public class SPUtils {

    private final static String SP_NAME = "ARconfig";

    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void setInt(String key, int value) {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }


    /**
     * 保存List 最后搜索的作品信息  最多保存3条
     *
     * @param key
     */
    public static void setLatelyList(String key, List datalist) {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (null == datalist || datalist.size() <= 0) {
            sp.edit().remove(key).commit();
            return;
        }
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(key, strJson).commit();
    }


    /**
     * 获取List
     *
     * @param key
     * @return
     */
    public static <T> List<T> getList(String key) {
        List<T> datalist = new ArrayList<T>();
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String strJson = sp.getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;
    }

    /**
     * 获取ListString  上面方法暂时搞不定
     *
     * @param key
     * @return
     */
    public static <T> String getListString(String key, Class<?> tClass) {
        List<T> datalist = new ArrayList<T>();
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String strJson = sp.getString(key, null);
        if (null == strJson) {
            return strJson;
        }
        Gson gson = new Gson();
//        List<T> data = (List<T>) gson.fromJson(strJson, tClass);
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return strJson;
    }


    /**
     * 保存List
     *
     * @param key
     * @param datalist
     */
    public static <T> void setList(String key, List<T> datalist) {
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (null == datalist || datalist.size() <= 0) {
            sp.edit().remove(key).commit();
            return;
        }
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(key, strJson).commit();
    }


    public <T> List<T> parseString2List(String key, Class clazz) {
        List<T> datalist = new ArrayList<T>();
        SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        String json = sp.getString(key, null);
        if (CommonUtils.isNull(json)) {
            return datalist;
        }
        Type type = new ParameterizedTypeImpl(clazz);
        datalist = new Gson().fromJson(json, type);
        return datalist;
    }




    private class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
