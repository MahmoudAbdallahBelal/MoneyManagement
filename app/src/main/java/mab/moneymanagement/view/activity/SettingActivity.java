package mab.moneymanagement.view.activity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.sql.DataTruncation;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.dialog.DialogBudgetFragment;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class SettingActivity extends AppCompatActivity {


    CheckedTextView tvBudget;
    ImageView imageBudget;
    ImageView imageAlert;
    TextView createBacup;
    TextView shareFile;
    CheckedTextView tv_DailyAlert;
    AlertDialog.Builder builder;
    User user;
    SharedPreference shar;
    TextView delete;
    Spinner daySpinner;
    TextView password;
    String selectedDay;
    String update_user_info_url = URL.PATH + URL.UPDATE_USER_INFO;
    String delete_url = URL.PATH + URL.DELETE_ALL_DATA;
    String password_url = URL.PATH + URL.PASSWORD_PROTECTION;
    String getData = URL.PATH + URL.DATA_URL;

    int flag = 0;
    int budgetFlag;
    String budget;
    User us;
    long quiued;
    DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar mToolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.nav_setting));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        builder = new AlertDialog.Builder(this);

        shar = new SharedPreference();
        user = shar.getUser(getApplicationContext());
        imageBudget = findViewById(R.id.setting_budget_image);
        imageAlert = findViewById(R.id.setting_alert_image);
        shareFile = findViewById(R.id.setting_tv_export_file);
        createBacup = findViewById(R.id.setting_create_backup_database);


        //------------------------------------budget
        tvBudget = findViewById(R.id.setting_check_tv_budget);
        us = shar.getUser(getApplicationContext());
        budget = tvBudget.getText().toString();

        if (us.getBadgetSelected() == true) {
            us = shar.getUser(getApplicationContext());
            budgetFlag = 1;

            imageBudget.setVisibility(View.VISIBLE);
            tvBudget.setText(budget + "      " + us.getBadgetValue());
        }


        tvBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (budgetFlag == 0) {
                    FragmentManager fm = getFragmentManager();
                    DialogBudgetFragment dialogFragment = new DialogBudgetFragment();
                    dialogFragment.show(fm, "");

                    us = shar.getUser(getApplicationContext());
                    imageBudget.setVisibility(View.VISIBLE);
                    tvBudget.setText(budget + "      " + us.getBadgetValue());


                } else {
                    imageBudget.setVisibility(View.INVISIBLE);

                    removeBudget();
                    us = shar.getUser(getApplicationContext());
                    tvBudget.setText(budget);
                    budgetFlag = 0;
                }


            }
        });

        //--------------------------Daily Alert---------------------------

        tv_DailyAlert = findViewById(R.id.setting_check_tv_daily_alert);

        us = shar.getUser(getApplicationContext());
        if (us.isDailyAlert() == true) {
            imageAlert.setVisibility(View.VISIBLE);
            flag = 1;
        }

        tv_DailyAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {
                    removeAlert();
                    flag = 0;
                    imageAlert.setVisibility(View.INVISIBLE);


                } else {
                    setAlert();
                    flag = 1;
                    imageAlert.setVisibility(View.VISIBLE);

                }


            }
        });


        //-------------------------------spinner for day ----------
        daySpinner = findViewById(R.id.setting_spinner_select_day);
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.day, android.R.layout.simple_spinner_item);

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setSelection(user.getBegainDayOfWeek() - 1);


        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = daySpinner.getItemAtPosition(position).toString();
                updateDay();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //-----------------------------password protection-------------------
        password = findViewById(R.id.setting_tv_password_protection);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordProtection();
            }
        });


        //--------------------delete all data in program -----------
        delete = findViewById(R.id.setting_delete_database);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllData();
            }
        });

        //------------------Share file-----------------------------

        //------------------Create backup -----------------------------

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(quiued);
                    Cursor c = downloadManager.query(query);

                    if (c.moveToFirst()) {
                        int coloumIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(coloumIndex)) {

                        }

                    }
                }

            }
        };
        createBacup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                startActivity(i);

            }
        });

        //--------------------------------------------------------------------

    }

    private void getUrl() {

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getData, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");

                            if (message.equals("All Data is Returned")) {
                                String data = response.getString("Url");

                                //---------------download data ---------


                                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(data);
                                DownloadManager.Request request = new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                                quiued = downloadManager.enqueue(request);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            getUrl();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        getUrl();

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getApplicationContext()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(this).addToRequestqueue(jsonObjectRequest);


    }



    private void deleteAllData() {



        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, delete_url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");

                            Toast.makeText(getApplicationContext(), getString(R.string.delete_done), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            deleteAllData();
                            //  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        deleteAllData();
                        // Do something when error occurred
                        // Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getApplicationContext()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(this).addToRequestqueue(jsonObjectRequest);


    }

    void createDialog() {

        builder.setTitle("Daily Alert ");
        builder.setMessage("Do you want alert everu day to remmber write in application");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setAlert();

                imageAlert.setVisibility(View.VISIBLE);

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.create().show();
    }

    private void setAlert() {

        final JSONObject updateObject = new JSONObject();
        try {
            updateObject.put("Email", user.getEmail());
            updateObject.put("FullName", user.getFullName());
            updateObject.put("ConcuranceyId", user.getCurrency() + 1);
            updateObject.put("BadgetValue", user.getBadgetValue());
            updateObject.put("DailyAlert", true);
            updateObject.put("BegainDay", user.getBegainDayOfWeek());
            updateObject.put("BadgetSelected", user.getBadgetSelected());

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
                                shar.removeUser(getApplication());
                                shar.saveUser(getApplication(), user);
                                Toast.makeText(getApplication(), "make alert", Toast.LENGTH_LONG).show();


                            } else {
                                //  Toast.makeText(getApplication(), "Error happen", Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            setAlert();
                            //  Toast.makeText(getApplication(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        setAlert();
                        //  Toast.makeText(getApplicationContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getApplicationContext()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getApplicationContext()).addToRequestqueue(jsonObjectRequest);


    }//22

    private void removeAlert() {

        final JSONObject updateObject = new JSONObject();
        try {
            updateObject.put("Email", user.getEmail());
            updateObject.put("FullName", user.getFullName());
            updateObject.put("ConcuranceyId", user.getCurrency() + 1);
            updateObject.put("BadgetValue", user.getBadgetValue());
            updateObject.put("DailyAlert", true);
            updateObject.put("BegainDay", user.getBegainDayOfWeek());
            updateObject.put("BadgetSelected", user.getBadgetSelected());

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
                                shar.removeUser(getApplication());
                                shar.saveUser(getApplication(), user);
                                Toast.makeText(getApplication(), "remove alert", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(getApplication(), "Error happen", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplication(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred

                        Toast.makeText(getApplicationContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getApplicationContext()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getApplicationContext()).addToRequestqueue(jsonObjectRequest);


    }//22


    private void removeBudget() {

        final JSONObject updateObject = new JSONObject();
        try {

            updateObject.put("Email", user.getEmail());
            updateObject.put("FullName", user.getFullName());
            updateObject.put("ConcuranceyId", user.getCurrency() + 1);
            updateObject.put("BadgetSelected", false);
            updateObject.put("BadgetValue", 0);
            updateObject.put("DailyAlert", user.isDailyAlert());
            updateObject.put("BegainDay", user.getBegainDayOfWeek());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, update_user_info_url, updateObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");
                            //load data on screen after change data
                            if (message.equals("Infromation Changed Successfuly")) {
                                shar.removeUser(getApplication());
                                shar.saveUser(getApplication(), user);
                                Toast.makeText(getApplication(), "remove Budget", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(getApplication(), "Error happen", Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Toast.makeText(getApplicationContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getApplicationContext()));
                return params;
            }
        };
        // Add JsonObjectRequest to the RequestQueue

        MysingleTon.getInstance(getApplicationContext()).addToRequestqueue(jsonObjectRequest);


    }//22


    private void updateDay() {


        final JSONObject updateObject = new JSONObject();
        try {
            updateObject.put("Email", user.getEmail());
            updateObject.put("FullName", user.getFullName());
            updateObject.put("ConcuranceyId", user.getCurrency() + 1);
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
                                shar.removeUser(getApplication());
                                shar.saveUser(getApplication(), user);
                                // Toast.makeText(getApplication(), "update day", Toast.LENGTH_LONG).show();


                            } else {
                                //   Toast.makeText(getApplication(), "Error happen", Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            updateDay();
                            // Toast.makeText(getApplicationContext(), "message" + e.getMessage(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        updateDay();
                        // Do something when error occurred
                        //   Toast.makeText(getApplicationContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", shar.getValue(getApplicationContext()));
                return params;
            }
        };

        // Add JsonObjectRequest to the RequestQueue
        MysingleTon.getInstance(getApplicationContext()).addToRequestqueue(jsonObjectRequest);


    }//22

    void passwordProtection() {

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View login_layout = inflater.inflate(R.layout.create_new_password, null);

        final EditText old = login_layout.findViewById(R.id.new_et_password_old);
        final EditText newPassword = login_layout.findViewById(R.id.new_et_password_new);


        builder.setView(login_layout);

        //SET BUTTON
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Check Validation

                if (newPassword.getText().toString().equals("") && old.getText().toString().equals("")) {

                    dialog.dismiss();
                } else {

                    JSONObject update = new JSONObject();
                    try {
                        update.put("NewPassword", newPassword.getText().toString());
                        update.put("OldPassword", old.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                    // Initialize a new JsonObjectRequest instance
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, password_url, update,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {

                                    //----------HANDEL MESSAGE COME FROM REQUEST -------------------

                                    try {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("RequstDetails"), Toast.LENGTH_LONG).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Do something when error occurred
                                    Toast.makeText(getApplicationContext(), "nnn" + error.getMessage(), Toast.LENGTH_LONG).show();


                                }
                            }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Authorization", shar.getValue(getApplicationContext()));
                            return params;
                        }
                    };

                    // Add JsonObjectRequest to the RequestQueue
                    MysingleTon.getInstance(getApplicationContext()).addToRequestqueue(jsonRequest);


                }

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


}



