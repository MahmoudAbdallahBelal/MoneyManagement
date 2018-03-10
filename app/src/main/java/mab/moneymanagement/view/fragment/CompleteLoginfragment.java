package mab.moneymanagement.view.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mab.moneymanagement.R;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.activity.Main2Activity;

public class CompleteLoginfragment extends Fragment {
    EditText etName, etSalary, etStartMonth, etEmail, etPassword;
    Button btnNext;
    Spinner currncySpinner;
    Spinner daySpinner;
    String reg_url = "http://gasem1234-001-site1.dtempurl.com/api/Register";
    //http://gasem1234-001-site1.dtempurl.com/api/Register


    String name, email, password;
    double salary;
    int day, startMonth;
    String kindCurrency;
    String selectedDay;


    AlertDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_complete_loginfragment, container, false);

        etName = (EditText) v.findViewById(R.id.regester_et_name);
        etPassword = (EditText) v.findViewById(R.id.regester_et_password);

        etEmail = (EditText) v.findViewById(R.id.regester_et_email);
        etSalary = (EditText) v.findViewById(R.id.regester_et_salary);
        etStartMonth = (EditText) v.findViewById(R.id.complete_login_et_start_month);
        btnNext = (Button) v.findViewById(R.id.complete_login_btn_next);

        builder = new AlertDialog.Builder(getActivity());


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
//                Regester();


                Intent mainIntent = new Intent(getActivity(), Main2Activity.class);
                startActivity(mainIntent);


            }
        });
        return v;
    }

    void Regester() {
        name = etName.getText().toString();
        password = etPassword.getText().toString();
        email = etEmail.getText().toString();
        startMonth = Integer.parseInt(etStartMonth.getText().toString());
        salary = Double.parseDouble(etSalary.getText().toString());
        //day && currency


        if (name.equals("") || password.equals("") || email.equals("") && selectedDay.equals("") || kindCurrency.equals("")) {

            builder.setTitle("Some thing wrong hppen ");
            builder.setMessage("Please fill all filed ");
            displayAlert("input_error");


        } else {

            final JSONObject regsterObject = new JSONObject();
            try {
                regsterObject.put("Email", email);
                regsterObject.put("FullName", name);
                regsterObject.put("ConcuranceyId", 1);
                regsterObject.put("Password", password);
                regsterObject.put("BegainDayOfWeek", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Initialize a new RequestQueue instance
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, reg_url, regsterObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");

                                if (message == "create account success") {
                                    builder.setTitle(" server response ");
                                    builder.setMessage(message);
                                    displayAlert("sucessful");


                                } else {
                                    builder.setTitle(" server response ");
                                    builder.setMessage(message);
                                    displayAlert("Error");

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                builder.setTitle(" Error happen  ");
                                builder.setMessage(e.toString());
                                displayAlert("error");

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


    private void displayAlert(final String code) {

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (code.equals("sucessful")) {

                    Intent mainIntent = new Intent(getActivity(), Main2Activity.class);
                    startActivity(mainIntent);
                    getActivity().finish();

                }

                if (code.equals("input_error")) {

                    etPassword.setText("");
                }


            //    dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}
