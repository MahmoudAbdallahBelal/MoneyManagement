package mab.moneymanagement.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.labo.kaji.fragmentanimations.CubeAnimation;

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
import mab.moneymanagement.view.adapter.ExpectedAdapter;
import mab.moneymanagement.view.model.ExpectedData;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class ExpectedFragment extends Fragment {
    ExpectedAdapter adapter;
    ListView mList;
    ArrayList<ExpectedData> data = new ArrayList<>();
    String staticUrl = URL.PATH + URL.RESET_CTEGORY;
    SharedPreference shar;
    TextView tvMessage;

    int month;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_expected, container, false);

        mList = v.findViewById(R.id.expected_list);
        tvMessage = v.findViewById(R.id.expected_tv_message);

        shar = new SharedPreference();


        Calendar cal = Calendar.getInstance();       // get calendar instance
        Date zeroedDate = cal.getTime();
        month = zeroedDate.getMonth() + 1;

        getStatics(month);


        return v;
    }

    private void getStatics(final int month) {
        String vv = staticUrl + "?month=" + month;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, vv, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            JSONArray arr = response.getJSONArray("data");


                            if (arr.length() == 0) {
                                tvMessage.setVisibility(View.VISIBLE);
                                tvMessage.setText(getString(R.string.no_expected_until_now));

                            }

                            data.clear();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.getJSONObject(i);

                                if (jsonObject.getInt("Budget") != 0) {
                                    ExpectedData expectedData = new ExpectedData(
                                            jsonObject.getInt("Id"),
                                            changeName(jsonObject.getString("Name")),
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

    public String changeName(String name) {
        if (name.equals("Food")) {
            return getString(R.string.food);
        } else if (name.equals("Home")) {
            return getString(R.string.home);
        } else if (name.equals("Personal")) {
            return getString(R.string.personal);
        } else if (name.equals("Salary")) {
            return getString(R.string.salary);
        } else if (name.equals("Saving")) {
            return getString(R.string.saving);
        } else if (name.equals("Shopping")) {
            return getString(R.string.shopping);
        } else if (name.equals("Child")) {
            return getString(R.string.child);
        } else if (name.equals("Car")) {
            return getString(R.string.car);
        } else if (name.equals("Kast")) {
            return getString(R.string.kast);
        } else if (name.equals("Credit")) {
            return getString(R.string.credit);
        } else
            return name;


    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.DOWN, enter, 700);
    }

}
