package mab.moneymanagement.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;
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
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.dialog.DialogBudgetFragment;
import mab.moneymanagement.view.dialog.DialogLanguageFragment;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class SettingActivity extends AppCompatActivity {


    CheckedTextView tvBudget;
    public static int isBudget = -1;
    CheckedTextView tv_DailyAlert;
    public static int alertFlag = 0;
    AlertDialog.Builder builder;

    User user;
    SharedPreference shar;
    TextView delete;

    URL url=new URL();


    String user_info_url =url.PATH+url.USER_INFO;
    String update_user_info_url =URL.PATH+URL.UPDATE_USER_INFO;
    String delete_url = url.PATH+url.DELETE_ALL_DATA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.nav_setting));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        builder = new AlertDialog.Builder(this);
        shar = new SharedPreference();


        //------------------------------------budget
        tvBudget = findViewById(R.id.setting_check_tv_budget);
        if (isBudget == 1) {
            tvBudget.setCheckMarkDrawable(R.drawable.checked);
        }
        tvBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                DialogBudgetFragment dialogFragment = new DialogBudgetFragment();
                dialogFragment.show(fm, "");

            }
        });

        //--------------------------Daily Alert---------------------------

        tv_DailyAlert = findViewById(R.id.setting_check_tv_daily_alert);
        tv_DailyAlert.setChecked(false);

        tv_DailyAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_DailyAlert.isChecked() == false) {

                    createDialog();

                } else {


                }


            }
        });
        //--------------------
        delete=(TextView)findViewById(R.id.setting_delete_database);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllData();
            }
        });


    }

    private void deleteAllData() {


        final JSONObject data = new JSONObject();
        try {
            //    data.put("Content-Type", "application/json");
            data.put("Authorization", shar.getValue(getApplicationContext()));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, delete_url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");

                            Toast.makeText(getApplicationContext(), "mmmmm" + message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                    }
                });

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getApplicationContext()).addToRequestqueue(jsonObjectRequest);


    }

    void createDialog() {

        builder.setTitle("Daily Alert ");
        builder.setMessage("Do you want alert everu day to remmber write in application");
        builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv_DailyAlert.setChecked(true);
                tv_DailyAlert.setCheckMarkDrawable(R.drawable.checked);


                dialog.dismiss();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.show();
    }


    private void makeUpdate() {
       String value = ""; //et_Value.getText().toString();
        int v = Integer.parseInt(value);
        if (value.equals("")) {
            Toast.makeText(getApplicationContext(), "Sorrry no value", Toast.LENGTH_SHORT).show();


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
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Initialize a new JsonObjectRequest instance
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, update_user_info_url, regsterObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                                String message = response.getString("RequstDetails");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
            MysingleTon.getInstance(getApplicationContext()).addToRequestqueue(jsonObjectRequest);

        }

    }

    private void getUserData() {

        final JSONObject data = new JSONObject();
        try {
            //    data.put("Content-Type", "application/json");
            data.put("Authorization", shar.getValue(getApplicationContext()));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, user_info_url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");

                            Toast.makeText(getApplicationContext(), "mmmmm" + message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

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
        MysingleTon.getInstance(getApplicationContext()).addToRequestqueue(jsonObjectRequest);

    }

}