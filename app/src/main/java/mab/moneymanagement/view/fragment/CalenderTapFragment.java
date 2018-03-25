package mab.moneymanagement.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class CalenderTapFragment extends Fragment {
    int yearr, monthh, dayy;

    CalendarView calendarView;
    String restUrl = URL.PATH + URL.RESET_CTEGORY;
    SharedPreference shar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calender_tap, container, false);

        calendarView = v.findViewById(R.id.calendar_tap_fragment);
//        shar = new SharedPreference();
//        calendarView.getDate();
//
//
//        int dd = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
//        int mm=Calendar.getInstance().getActualMaximum(Calendar.MONTH);
//        int yy=Calendar.getInstance().getActualMaximum(Calendar.YEAR);
//        if (dd == dayy ||monthh>mm||yearr>yy) {
//            resetcategory();
//        }
        return v;
    }

    private void resetcategory() {

        final JSONObject updateObject = new JSONObject();
        try {
            updateObject.put("month", monthh);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Initialize a new JsonObjectRequest instance
        String vv = restUrl + "?month=" + monthh;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, vv, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            resetcategory();
                            Toast.makeText(getContext(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Toast.makeText(getContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();
                        resetcategory();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getContext()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }

}



