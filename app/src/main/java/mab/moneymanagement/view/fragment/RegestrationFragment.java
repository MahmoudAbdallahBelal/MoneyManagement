package mab.moneymanagement.view.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.activity.Main2Activity;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class RegestrationFragment extends Fragment {
    EditText etName, etSalary, etStartMonth, etEmail, etPassword;
    Button btnNext;
    Spinner currncySpinner;
    Spinner daySpinner;
    URL url = new URL();

    String reg_url = URL.PATH + URL.REGISTER;
    String name, email, password;
    String kindCurrency;
    String selectedDay;
    String accessTocken;
    String authorization;


    MaterialDialog.Builder loginDaolog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_complete_loginfragment, container, false);

        etName = v.findViewById(R.id.regester_et_name);
        etPassword = v.findViewById(R.id.regester_et_password);
        etEmail = v.findViewById(R.id.regester_et_email);
        btnNext = v.findViewById(R.id.complete_login_btn_next);

        loginDaolog = new MaterialDialog.Builder(getContext());


        //------spinner for currency ----------
        currncySpinner = v.findViewById(R.id.complete_login_spinner_select_currency);
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


        //------spinner for day ----------
        daySpinner = v.findViewById(R.id.complete_login_spinner_select_day);
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.day,
                android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = daySpinner.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Regester();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                }



            }
        });
        return v;
    }

    void Regester() {
        name = etName.getText().toString();
        password = etPassword.getText().toString();
        email = etEmail.getText().toString();
        String deviceToken = FirebaseInstanceId.getInstance().getToken();


        if (name.equals("") || password.equals("") || email.equals("") || selectedDay.equals("") || kindCurrency.equals("")) {
            Toast.makeText(getContext(), "complete all data ", Toast.LENGTH_LONG).show();
        } else {
            //  String device_id= Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);


            loginDaolog.title("Create Account ").content("Please wait untol regester ..").show();
            final JSONObject regsterObject = new JSONObject();
            try {
                regsterObject.put("Email", email);
                regsterObject.put("FullName", name);
                regsterObject.put("ConcuranceyId", getCurrency(kindCurrency) + 1);
                regsterObject.put("Password", password);
                regsterObject.put("BegainDayOfWeek", getDay(selectedDay) + 1);
                regsterObject.put("BadgetSelected", false);
                regsterObject.put(" BadgetValue", 0);
                regsterObject.put("DailyAlert", false);
                regsterObject.put("DeviceId", deviceToken);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, reg_url, regsterObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");
                                accessTocken = response.getString("access_token");
                                authorization = response.getString("token_type");

                                if (message.equals("create account success")) {
                                    loginDaolog.build().dismiss();
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    //save id  in shared prefrence
                                    SharedPreference shar = new SharedPreference();
                                    shar.save(getActivity(), accessTocken, authorization);

                                    Intent mainIntent = new Intent(getActivity(), Main2Activity.class);
                                    startActivity(mainIntent);
                                    getActivity().finish();


                                } else {
                                    loginDaolog.build().hide();
                                    Toast.makeText(getContext(), "error message" + message, Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                loginDaolog.build().hide();
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
        //---------------------------------------


    }


    private int getDay(String day) {
        if (day.equals("Sunday")) {
            return 0;
        } else if (day.equals("Monday")) {
            return 1;
        } else if (day.equals("Tuesday")) {
            return 2;
        } else if (day.equals("Wednesday")) {
            return 3;
        } else if (day.equals("Thurthday")) {
            return 4;
        } else if (day.equals("Friday")) {
            return 5;
        } else
            return 6;
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
