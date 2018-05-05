package mab.moneymanagement.util;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Mahmoud on 07/08/2017.
 */

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        setTypeface(MyTypeFace.getInstance(context).getTypeFace());

    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(MyTypeFace.getInstance(context).getTypeFace());


    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(MyTypeFace.getInstance(context).getTypeFace());

    }

}