package mab.moneymanagement.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.adapter.ExpectedAdapter;
import mab.moneymanagement.view.model.ExpectedData;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class ExpectedFragment extends Fragment {
    ExpectedAdapter adapter;
    ListView mList;
    ArrayList<ExpectedData> data = new ArrayList<>();
    String staticUrl = URL.PATH + URL.RESET_CTEGORY;
    SharedPreference shar;


    int month;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_expected, container, false);

        mList = v.findViewById(R.id.expected_list);

        shar = new SharedPreference();

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        month = localDate.getMonthValue();
        data.clear();

        getStatics(month);


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
                            //Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            JSONArray arr = response.getJSONArray("data");

                            data.clear()
                            ;

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.getJSONObject(i);

                                if (jsonObject.getInt("Budget") == 0) {
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


                            }
                            adapter = new ExpectedAdapter(getContext(), data);
                            mList.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                            getStatics(month);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        getStatics(month);


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
