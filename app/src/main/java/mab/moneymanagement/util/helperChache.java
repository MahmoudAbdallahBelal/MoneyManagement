package mab.moneymanagement.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by belal on 7/18/18.
 */

public class helperChache {


    public static SharedPreferences mPrefs ;


    public static String Income_Expense_SHARED_PREFERENCES_NAME="IncomeAndExpense";
    public static String  Income_Count_Key ="income";
    public static String Expense_Count_Key ="expense";

    public static  void saveCounter(Context context , int income , int expense)

    {
        mPrefs = context.getSharedPreferences(Income_Expense_SHARED_PREFERENCES_NAME , 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putInt(Income_Count_Key, income);
        prefsEditor.putInt(Expense_Count_Key, expense);
        prefsEditor.commit();

    }

    public static  int retrieveCountIncome(Context context)

    {
        mPrefs = context.getSharedPreferences(Income_Expense_SHARED_PREFERENCES_NAME , 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        int incomeCounter = mPrefs.getInt(Income_Count_Key ,0);

        prefsEditor.commit();

        return incomeCounter;
    }




    public static  int retrieveCountExpense(Context context)

    {
        mPrefs = context.getSharedPreferences(Income_Expense_SHARED_PREFERENCES_NAME , 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        int expenseCounter = mPrefs.getInt(Expense_Count_Key ,0);

        prefsEditor.commit();

        return expenseCounter;
    }

}
