package mab.moneymanagement.view.fragment;

import android.os.Build;
import android.os.Bundle;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class CalenderTapFragment extends Fragment {
    int year, month, day;

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
        shar = new SharedPreference();

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year = localDate.getYear();
        month = localDate.getMonthValue();
        day = localDate.getDayOfMonth();

        int d = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        if (d == day) {
            resetcategory();
        }
        return v;
    }

    private void resetcategory() {

        final JSONObject updateObject = new JSONObject();
        try {
            updateObject.put("month", month);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Initialize a new JsonObjectRequest instance
        String vv = restUrl + "?month=" + month;
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



