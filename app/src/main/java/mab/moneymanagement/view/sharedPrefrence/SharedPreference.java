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
    public static final String PREFS_EMAIL = "email";
    public static final String PREFS_FULL_NAME = "name";
    public static final String PREFS_BEING_WEEK = "beingDayOfWeek";
    public static final String PREFS_BUDGET_SELECTED = "budgetSelected";
    public static final String PREFS_BUDGET_VALUE = "budgetValue";
    public static final String PREFS_CURRENCY = "currency";
    public static final String PREFS_ALERT = "alert";


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

    public void saveUser(Context context, User user) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        if (user.getBadgetSelected() != null) {

            editor.putString(PREFS_EMAIL, user.getEmail());
            editor.putString(PREFS_FULL_NAME, user.getFullName());
            editor.putInt(PREFS_BEING_WEEK, user.getBegainDayOfWeek());
            editor.putBoolean(PREFS_BUDGET_SELECTED, user.getBadgetSelected());
            editor.putInt(PREFS_BUDGET_VALUE, user.getBadgetValue());
            editor.putInt(PREFS_CURRENCY, user.getCurrency());
            editor.putBoolean(PREFS_ALERT, user.isDailyAlert());

        } else {
            editor.putString(PREFS_EMAIL, user.getEmail());
            editor.putString(PREFS_FULL_NAME, user.getFullName());
            editor.putInt(PREFS_BEING_WEEK, user.getBegainDayOfWeek());
            editor.putBoolean(PREFS_BUDGET_SELECTED, false);
            editor.putInt(PREFS_BUDGET_VALUE, user.getBadgetValue());
            editor.putInt(PREFS_CURRENCY, user.getCurrency());
            editor.putBoolean(PREFS_ALERT, user.isDailyAlert());

        }
        editor.commit(); //4
        editor.apply();

    }

    public User getUser(Context context) {
        SharedPreferences settings;

        User user = new User();

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        user.setEmail(settings.getString(PREFS_EMAIL, null));
        user.setFullName(settings.getString(PREFS_FULL_NAME, null));
        user.setBegainDayOfWeek(settings.getInt(PREFS_BEING_WEEK, 0));
        user.setBadgetSelected(settings.getBoolean(PREFS_BUDGET_SELECTED, false));
        user.setBadgetValue(settings.getInt(PREFS_BUDGET_VALUE, 0));
        user.setDailyAlert(settings.getBoolean(PREFS_ALERT, false));
        user.setCurrency(settings.getInt(PREFS_CURRENCY, 0));


        return user;
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

    public void removeUser(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        editor = settings.edit();


        editor.remove(PREFS_FULL_NAME);
        editor.remove(PREFS_ALERT);
        editor.remove(PREFS_CURRENCY);
        editor.remove(PREFS_BUDGET_VALUE);
        editor.remove(PREFS_BUDGET_SELECTED);
        editor.remove(PREFS_BEING_WEEK);
        editor.remove(PREFS_EMAIL);


        editor.commit();
        editor.apply();
    }

}


