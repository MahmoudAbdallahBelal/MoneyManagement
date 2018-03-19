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
import mab.moneymanagement.view.activity.Main2Activity;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

import static android.content.Context.MODE_PRIVATE;


public class PersonalAccountFragment extends Fragment {


    URL url = new URL();
    String user_info_url = URL.PATH + URL.USER_INFO;
    String update_user_info_url = URL.PATH + URL.UPDATE_USER_INFO;

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
        et_name = v.findViewById(R.id.personal_et_name);
        et_email = v.findViewById(R.id.personal_account_et_email);
        update = v.findViewById(R.id.personal_account_update);
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
                makeUpdate();
            }
        });

        return v;
    }


    private void getUserData() {


        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, user_info_url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            et_name.setText(response.getString("FullName"));
                            et_email.setText(response.getString("Email"));
                            currncySpinner.setSelection(response.getInt("ConcuranceyId")-1);

                            ///**----put user data in object
                            user.setFullName(response.getString("FullName"));
                            user.setEmail(response.getString("Email"));
                            user.setCurrency(response.getInt("ConcuranceyId")-1);


                            //  Toast.makeText(getContext(), "mmmmm" + message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if error time out happen  call method again to get all data for user
                        if (error.toString().equals("")){

                        }
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

                    }


                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String v = "bearer jYfZKvuTA7ccsWsirAV9Hh7OLUWRW9UkThNrZ0SDVpo9iunNIx1Ec-1DZ_-cLkVrns7CD6tkenVKryL1ws3hrygNo5EiOlQV0W0V0ya8m8-ofQb7n0q0JDo3ljKSREFGLJJUXDaJSbFbQmSJCjEGLk-EtYJMIiErov-wwo2okyG6ytvOdiRoViiCpn-Hfp4NrMN9b0IOJafMeFWKQgBsuRElAI8R24BRY4rLu_oL0dEVm3KuQ1JjtxWD6YOQ_NA33gxp4Gx2bzgqT782hzxxQKlX-de3rMhna8BIRN7Co30SJqrc7ikyGyWnUARB8spCRjCbN6b4TV5s2FdkeahQwr7Sfmh75B5sjzZP26werzLq5sd3fF9bA3zd7dlZdLPFBtpQ1zz5AKdRw3DBCBFrwDkpdPMY4A9-7pR2gIODOMRxtqKaJb6OPXM2iI04Pe_YNyIWllP5xixBMtEH67Uz2g_idXyqrhnG8BjDcRRdkCU";

                params.put("Authorization", shar.getValue(getContext()));
                return params;
            }
        };


        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getActivity()).addToRequestqueue(jsonObjectRequest);

    }


    private void makeUpdate() {

        loginDaolog.title("Wait .....").content("Save schanges").show();

        String email = et_email.getText().toString();
        String name = et_name.getText().toString();

        if (email.equals("") || name.equals("") || kindCurrency.equals("")) {
            loginDaolog.build().dismiss();
            Toast.makeText(getActivity(), "Please Complete data ... ", Toast.LENGTH_LONG).show();


        } else if (email.equals(user.getEmail()) && name.equals(user.getFullName()) && kindCurrency.equals(user.getCurrency())) {
            loginDaolog.build().dismiss();
            Toast.makeText(getActivity(), "No data changes ", Toast.LENGTH_LONG).show();

        } else {

            final JSONObject updateObject = new JSONObject();
            try {
                updateObject.put("Email", email);
                updateObject.put("FullName", name);
                updateObject.put("ConcuranceyId", getCurrency(kindCurrency) + 1);
                updateObject.put("BadgetSelected", user.getBadgetSelected());
                updateObject.put("BadgetValue", user.getBadgetValue());
                updateObject.put("DailyAlert", user.isDailyAlert());
                updateObject.put("BegainDay", user.getBegainDayOfWeek());

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

                                if (message.equals("Infromation Changed Successfuly")) {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    shar.saveUser(getContext(), user);

                                    loginDaolog.build().dismiss();
                                    loginDaolog.build().hide();
                                }

                                //load data on screen after change data
                                getUserData();
                               // Toast.makeText(getContext(), "message" + message, Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                loginDaolog.build().dismiss();
                                loginDaolog.build().hide();
                                Toast.makeText(getContext(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();
                                loginDaolog.autoDismiss(true);

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Do something when error occurred
                            loginDaolog.build().hide();
                            Toast.makeText(getContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();
                            loginDaolog.autoDismiss(true);


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
            ///return 4 mean return L.E
            return 4;


    }


}
