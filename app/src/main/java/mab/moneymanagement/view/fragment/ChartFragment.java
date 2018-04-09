package mab.moneymanagement.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.model.ExpectedData;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class ChartFragment extends Fragment {


    BarChart barrChart;
    ArrayList<ExpectedData> data = new ArrayList<>();
    String staticUrl = URL.PATH + URL.RESET_CTEGORY;

    SharedPreference shar;
    int income;
    int expense;

    int month;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        barrChart = v.findViewById(R.id.bar_chart);

        shar = new SharedPreference();
        Calendar cal = Calendar.getInstance();       // get calendar instance
        Date zeroedDate = cal.getTime();
        month = zeroedDate.getMonth() + 1;


        getStatics(month);
        for (int i = 0; i < data.size(); i++) {
            income += data.get(i).getBudget();
            expense += data.get(i).getMoney();
        }


//--------------------------------------------------------------
        barrChart.setDrawBarShadow(false);
        barrChart.setDrawValueAboveBar(true);
        barrChart.setMaxVisibleValueCount(income + expense);
        barrChart.setPinchZoom(false);
        barrChart.setDrawGridBackground(true);


        ArrayList<BarEntry> barEntry = new ArrayList<>();
        barEntry.add(new BarEntry(1, income));
        barEntry.add(new BarEntry(2, expense));

        BarDataSet barDataSet = new BarDataSet(barEntry, "");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(.9f);
        barrChart.setData(data);


        return v;
    }

    private void getStatics(final int month) {


        // Initialize a new JsonObjectRequest instance
        String vv = staticUrl + "?month=" + month;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, vv, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            JSONArray arr = response.getJSONArray("data");


                            if (arr.length() == 0) {
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

                            }

                            data.clear();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.getJSONObject(i);

                                ExpectedData expectedData = new ExpectedData(
                                        jsonObject.getInt("Id"),
                                        jsonObject.getString("Name"),
                                        jsonObject.getString("Icon"),
                                        jsonObject.getInt("Money"),
                                        jsonObject.getInt("Budget"),
                                        jsonObject.getInt("Month"),
                                        jsonObject.getInt("Year"),
                                        jsonObject.getInt("Budget") - jsonObject.getInt("Money")


                                );
                                data.add(expectedData);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                            //getStatics(month);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        /// getStatics(month);


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
