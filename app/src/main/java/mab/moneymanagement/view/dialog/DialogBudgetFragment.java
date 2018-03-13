package mab.moneymanagement.view.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mab.moneymanagement.R;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

import static mab.moneymanagement.view.activity.SettingActivity.isBudget;

/**
 * Created by Gihan on 3/4/2018.
 */

public class DialogBudgetFragment extends DialogFragment {


    EditText et_Value;
    User user;
    SharedPreference shar;


    String user_info_url = "http://gasem1234-001-site1.dtempurl.com/api/GetUserInfo";
    String update_user_info_url = "http://gasem1234-001-site1.dtempurl.com/api/UpdateUserInfo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.budget, container, false);

        shar = new SharedPreference();


        et_Value = v.findViewById(R.id.budget_et_value);
        Button ok = v.findViewById(R.id.budget_btn_ok);
        Button cancel = v.findViewById(R.id.budget_btn_cancel);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isBudget=1;
                dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return v;
    }

    private void makeUpdate() {
        String value = et_Value.getText().toString();
        int v = Integer.parseInt(value);
        if (value.equals("")) {
            Toast.makeText(getActivity(), "Sorrry no value", Toast.LENGTH_SHORT).show();


        } else {

            final JSONObject regsterObject = new JSONObject();
            try {
                regsterObject.put("Email", user.getEmail());
                regsterObject.put("FullName", user.getFullName());
                regsterObject.put("ConcuranceyId", user.getCurrency());
                regsterObject.put("BadgetSelected", true);
                regsterObject.put(" BadgetValue", v);
                regsterObject.put("DailyAlert", false);
                regsterObject.put("Authorization", "bearer");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Initialize a new RequestQueue instance
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, update_user_info_url, regsterObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something when error occurred

                        }
                    });

            // Add JsonObjectRequest to the RequestQueue
            MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

        }

    }

    private void getUserData() {

        final JSONObject data = new JSONObject();
        try {
            //    data.put("Content-Type", "application/json");
            data.put("Authorization", shar.getValue(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();

        }

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, user_info_url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");

                            Toast.makeText(getActivity(), "mmmmm" + message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred

                    }
                });

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }


}
