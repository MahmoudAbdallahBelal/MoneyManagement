package mab.moneymanagement.view.Volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Gihan on 3/7/2018.
 */

public class MysingleTon {

    public static MysingleTon instance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MysingleTon(Context context){
        mCtx=context;
        requestQueue=getRequestQueue();

    }

    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return requestQueue;

    }

    public static synchronized  MysingleTon getInstance(Context context){
        if (instance==null){
            instance=new MysingleTon(context);

        }

        return instance;

    }

    public<T> void addToRequestqueue(Request<T> request){
        requestQueue.add(request);


    }
}
