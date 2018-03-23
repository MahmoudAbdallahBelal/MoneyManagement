package mab.moneymanagement.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.activity.MonthlyStatics;
import mab.moneymanagement.view.adapter.MonthlystaticsAdapter;
import mab.moneymanagement.view.model.MonthStatics;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class MonthlystaticsFragment extends Fragment {

    MonthlystaticsAdapter adapter;
    ListView mList;
    ArrayList<MonthStatics> data = new ArrayList<>();
    String staticUrl = URL.PATH + URL.RESET_CTEGORY;
    SharedPreference shar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_monthlystatics, container, false);
        mList = v.findViewById(R.id.monthly_statics_list);

        shar = new SharedPreference();
        getStatics();

        return v;
    }

    private void getStatics() {


        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, staticUrl, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            // Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            JSONArray arr = response.getJSONArray("data");

                            data.clear();

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.getJSONObject(i);
                                MonthStatics statics = new MonthStatics(
                                        jsonObject.getInt("Money"),
                                        jsonObject.getInt("Budget"),
                                        jsonObject.getInt("Month"),
                                        jsonObject.getInt("Year")
                                );
                                data.add(statics);


                            }

                            adapter = new MonthlystaticsAdapter(getContext(), data);
                            mList.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        getStatics();


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
