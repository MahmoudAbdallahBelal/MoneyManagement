package mab.moneymanagement.view.sharedPrefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import mab.moneymanagement.view.model.User;

import static android.content.Context.MODE_PRIVATE;
import static mab.moneymanagement.view.activity.Main2Activity.isLoggin;

/**
 * Created by Gihan on 3/11/2018.
 */

public class SharedPreference {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String accessTocken = "acess";
    public static final String authentication = "auth";
    SharedPreferences sharedpreferences;

    public static final String PREFS_NAME = "user_id";
    public static final String PREFS_KEY = "AOP_PREFS_String";


    public SharedPreference() {
        super();
    }

    public void save(Context context, String acess, String auth) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(accessTocken, acess); //3
        editor.putString(authentication, auth); //3
        editor.commit(); //4
        editor.apply();

    }

    public String getValue(Context context) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String access = settings.getString(accessTocken, null);
        String auth = settings.getString(authentication, null);

        if (access == "null" && auth == "null") {
            return "";
        } else {

            text = auth + " " + access;
            return text;
        }
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(PREFS_NAME);

        editor.remove(accessTocken);
        editor.remove(authentication);

        editor.commit();
        editor.apply();
    }
}


