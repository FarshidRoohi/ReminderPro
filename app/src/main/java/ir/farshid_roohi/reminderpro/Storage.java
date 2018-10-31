package ir.farshid_roohi.reminderpro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONObject;


/**
 * Created by ImanX.
 * MyZarinPal-Android-V4 | Copyrights 2018 ZarinPal Crop.
 */

public class Storage {

    private SharedPreferences        sp;
    private SharedPreferences.Editor editor;

    public Storage(Context context) {
        this(context, "dev");
    }

    @SuppressLint("CommitPrefEdits")
    public Storage(Context context, String name) {
        this.sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public <T> void put(Class<T> clazz, String key, @NonNull T value) {
        editor.putString(key, value.toString());
        editor.apply();
    }


    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    @Nullable
    public <T> T get(Class<T> clazz, String key) {
        try {
            String resValue = sp.getString(key, null);

            if (resValue == null) {
                return null;
            }

            if (clazz.isAssignableFrom(String.class)) {
                return (T) resValue;
            }

            if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)) {
                return (T) Integer.valueOf(resValue);
            }

            if (clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(boolean.class)) {
                return (T) Boolean.valueOf(resValue);
            }

            if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)) {
                return (T) Long.valueOf(resValue);
            }

            if (clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(float.class)) {
                return (T) Float.valueOf(resValue);
            }

            if (clazz.isAssignableFrom(JSONObject.class)) {
                return (T) new JSONObject(resValue);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return null;
    }


    public synchronized void removeAll() {
        for (String key : sp.getAll().keySet()) {
            remove(key);
        }
    }

}
