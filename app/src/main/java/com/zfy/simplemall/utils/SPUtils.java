package com.zfy.simplemall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ZFY on 2017/04/21.
 * <p>
 * PreferencesUtils, easy to get or put data
 * <ul>
 * <strong>Preference Name</strong>
 * <li>you can change preference name by {@link #PREFERENCE_NAME}</li>
 * </ul>
 * <ul>
 * <strong>Put Value</strong>
 * <li>put  {@link #put(Context context, String key, Object object)}</li>
 * </ul>
 * <ul>
 * <strong>Get Value</strong>
 * <li>get  {@link #get(Context context, String key, Object defaultObject)}</li>
 * </ul>
 */

public class SPUtils {
    public static final String PREFERENCE_NAME = "share_data";

    /**
     * sharedPreference put methods
     *
     * @param context
     * @param key     key The name of the preference to retrieve
     * @param object  The new value for the preference
     * @author zfy
     * @created at 2017/4/21/021 11:59
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        }
        editor.apply();
    }

    /**
     * @param context
     * @param key           defaultValue Value to return if this preference does not exist
     * @param defaultObject defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     * @author zfy
     * @created at 2017/4/21/021 12:06
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }
}
