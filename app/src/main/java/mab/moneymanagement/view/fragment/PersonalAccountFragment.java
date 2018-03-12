package mab.moneymanagement.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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
import mab.moneymanagement.view.activity.Main2Activity;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

import static android.content.Context.MODE_PRIVATE;


public class PersonalAccountFragment extends Fragment {

    String user_info_url = "http://gasem1234-001-site1.dtempurl.com/api/GetUserInfo";
    String update_user_info_url = "http://gasem1234-001-site1.dtempurl.com/api/UpdateUserInfo";

    User user;
    EditText et_name;
    EditText et_email;
    Spinner currncySpinner;
    Button update;
    SharedPreference shar;
    String kindCurrency;

    MaterialDialog.Builder loginDaolog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal_account, container, false);
        et_name = (EditText) v.findViewById(R.id.personal_et_name);
        et_email = (EditText) v.findViewById(R.id.personal_account_et_email);
        update = (Button) v.findViewById(R.id.personal_account_update);
        loginDaolog = new MaterialDialog.Builder(getContext());


        user = new User();

        //------spinner for currency ----------
        currncySpinner = v.findViewById(R.id.personal_account_spinner_select_currency);
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.currency,
                android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currncySpinner.setAdapter(currencyAdapter);
        currncySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kindCurrency = currncySpinner.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        shar = new SharedPreference();
        //get informatio of user  from server
        getUserData();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), shar.getValue(getActivity()), Toast.LENGTH_LONG).show();
                //makeUpdate();
                getUserData();
            }
        });

        return v;
    }


    private void getUserData() {

        final JSONObject data = new JSONObject();
        try {
            //    data.put("Content-Type", "application/json");
            data.put("Authorization", shar.getValue(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

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
                            et_name.setText(response.getString("FullName"));
                            et_email.setText(response.getString("Email"));
                            currncySpinner.setSelection(getCurrency("L.E"));

                            Toast.makeText(getContext(), "mmmmm" + message, Toast.LENGTH_LONG).show();

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

                    }
                });

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }


    private void makeUpdate() {

        loginDaolog.title("Wait .....").content("Save schanges").show();

        String email = et_email.getText().toString();
        String name = et_name.getText().toString();

        if (email.equals("") || name.equals("") || currncySpinner.equals("")) {
            loginDaolog.build().dismiss();
            Toast.makeText(getActivity(), "Please Complete data ... ", Toast.LENGTH_LONG).show();


        } else if (email.equals(user.getEmail()) && name.equals(user.getFullName()) && currncySpinner.equals(user.getCurrency())) {
            loginDaolog.build().dismiss();
            Toast.makeText(getActivity(), "No data changes ", Toast.LENGTH_LONG).show();

        } else {

            final JSONObject regsterObject = new JSONObject();
            try {
                regsterObject.put("Email", email);
                regsterObject.put("FullName", name);
                regsterObject.put("ConcuranceyId", 1);
                regsterObject.put("BadgetSelected", false);
                regsterObject.put(" BadgetValue", 0);
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


                                loginDaolog.build().dismiss();
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                loginDaolog.build().dismiss();
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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

    private int getCurrency(String cur) {

        if (cur.equals("USD")) {
            return 0;
        } else if (cur.equals("EUR")) {
            return 1;
        } else if (cur.equals("UK")) {
            return 2;
        } else if (cur.equals("RSA")) {
            return 3;
        } else
            return 4;


    }
}
