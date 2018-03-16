package mab.moneymanagement.view.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
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
    String update_user_info_url = URL.PATH+URL.UPDATE_USER_INFO;


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

        if (et_Value.getText().toString().equals("")) {

        } else {

            String v = et_Value.getText().toString();
            int tt = Integer.parseInt(v);
            final JSONObject updateObject = new JSONObject();
            try {
                updateObject.put("Email", user.getEmail());
                updateObject.put("FullName", user.getFullName());
                updateObject.put("ConcuranceyId", user.getCurrency());
                updateObject.put("BadgetSelected", true);
                updateObject.put("BadgetValue", tt);
                updateObject.put("DailyAlert", user.isDailyAlert());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, update_user_info_url, updateObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something when error occurred
                            Toast.makeText(getActivity(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", shar.getValue(getActivity()));
                    return params;
                }
            };

            // Add JsonObjectRequest to the RequestQueue
            MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

        }
    }


}
