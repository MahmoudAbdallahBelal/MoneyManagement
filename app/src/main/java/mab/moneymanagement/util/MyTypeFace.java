package mab.moneymanagement.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Mahmoud on 07/08/2017.
 */

public class MyTypeFace {

    private static MyTypeFace instance;
    private static Typeface typeface;


    public static MyTypeFace getInstance(Context context) {
        synchronized (MyTypeFace.class) {
            if (instance == null) {
                instance = new MyTypeFace();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "DIN_Alternate_Bold.otf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }

}
