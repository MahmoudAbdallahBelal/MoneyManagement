package mab.moneymanagement.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;


public class PersonalAccountFragment extends Fragment {


    String user_info_url = URL.PATH + URL.USER_INFO;
    String update_user_info_url = URL.PATH + URL.UPDATE_USER_INFO;

    User user;
    EditText et_name;
    EditText et_email;
    Spinner currncySpinner;
    Button update;
    SharedPreference shar;
    String kindCurrency;

    ProgressDialog progressDialog;

    MDToast mdToast;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_personal_account, container, false);


        et_name = view.findViewById(R.id.personal_et_name);
        et_email =view.findViewById(R.id.personal_account_et_email);
        update = view.findViewById(R.id.personal_account_update);
        currncySpinner = view.findViewById(R.id.personal_account_spinner_select_currency);


             user = new User();
            progressDialog = new ProgressDialog(getActivity());


             shar = new SharedPreference();

        //------spinner for currency ----------
        getUserData();



        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.currency, android.R.layout.simple_spinner_item);
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


        //get informatio of user  from server



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et_email.getText().toString();
                String name = et_name.getText().toString();





                if (email.equals("") || name.equals("") || kindCurrency.equals("")) {
                    Toast.makeText(getActivity(), getString(R.string.complete_data), Toast.LENGTH_LONG).show();

                    return;

                } else if (email.equals(user.getEmail()) && name.equals(user.getFullName()) && kindCurrency.equals(user.getCurrency())) {

                    Toast.makeText(getActivity(), getString(R.string.no_changes), Toast.LENGTH_LONG).show();

                    return;
                } else {
                    makeUpdate();
                }
            }
        });


        return view;
    }


    private void getUserData() {



        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();

        // Initialize a new RequestQueue instance
       // RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, user_info_url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        progressDialog.dismiss();
                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                           if(message.equals("Details User Returned")) {

                               et_name.setText(response.getString("FullName"));
                               et_email.setText(response.getString("Email"));
                               currncySpinner.setSelection(response.getInt("ConcuranceyId") - 1);

                               ///**----put user data in object
                               user.setFullName(response.getString("FullName"));
                               user.setEmail(response.getString("Email"));
                               user.setCurrency(response.getInt("ConcuranceyId") - 1);

                           }
                           else{

                               mdToast = MDToast.makeText(getActivity(),getString(R.string.error_load_data) , 8,MDToast.TYPE_ERROR );
                               mdToast.show();
                           }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            getUserData();



                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if error time out happen  call method again to get all data for user
//                                               if (error.toString().equals("")) {
//
//                        }
//                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

                        getUserData();

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


    private void makeUpdate() {


        String email = et_email.getText().toString();
        String name = et_name.getText().toString();

        if (email.equals("") || name.equals("") || kindCurrency.equals("")) {
            Toast.makeText(getActivity(), getString(R.string.complete_data), Toast.LENGTH_LONG).show();


        } else if (email.equals(user.getEmail()) && name.equals(user.getFullName()) && kindCurrency.equals(user.getCurrency())) {
            Toast.makeText(getActivity(), getString(R.string.no_changes), Toast.LENGTH_LONG).show();

        } else {

            final JSONObject updateObject = new JSONObject();

            progressDialog.setMessage(getString(R.string.wait));
            progressDialog.show();
            try {
                if (user.getBadgetSelected() == null) {
                    updateObject.put("Email", email);
                    updateObject.put("FullName", name);
                    updateObject.put("ConcuranceyId", getCurrency(kindCurrency) + 1);
                    updateObject.put("BadgetSelected", false);
                    updateObject.put("BadgetValue", user.getBadgetValue());
                    updateObject.put("DailyAlert", user.isDailyAlert());
                    updateObject.put("BegainDay", user.getBegainDayOfWeek());

                } else {
                    updateObject.put("Email", email);
                    updateObject.put("FullName", name);
                    updateObject.put("ConcuranceyId", getCurrency(kindCurrency) + 1);
                    updateObject.put("BadgetSelected", user.getBadgetSelected());
                    updateObject.put("BadgetValue", user.getBadgetValue());
                    updateObject.put("DailyAlert", user.isDailyAlert());
                    updateObject.put("BegainDay", user.getBegainDayOfWeek());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, update_user_info_url, updateObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                progressDialog.dismiss();

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");

                                if (message.equals("Infromation Changed Successfuly")) {
                                    //Toast.makeText(getContext(), , Toast.LENGTH_LONG).show();
                                    mdToast = MDToast.makeText(getActivity(),getString(R.string.update_done) , 8,MDToast.TYPE_SUCCESS );
                                    mdToast.show();
                                    shar.saveUser(getContext(), user);

                                }

                                //load data on screen after change data
                                getUserData();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                makeUpdate();

                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            makeUpdate();

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
            }
            else
                return  3;
// else if (cur.equals("SAR")) {
//                return 3;
//            } else
//                ///return 4 mean return L.E
//                return 4;



    }



}
