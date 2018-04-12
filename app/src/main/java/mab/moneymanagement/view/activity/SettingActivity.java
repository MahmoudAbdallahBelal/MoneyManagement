package mab.moneymanagement.view.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.dialog.DialogBudgetFragment;
import mab.moneymanagement.view.interfaces.InterfaceBudget;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class SettingActivity extends AppCompatActivity implements InterfaceBudget {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar mToolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.nav_setting));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        builder = new AlertDialog.Builder(this);

        //-set Listner
        DialogBudgetFragment.getDio(this);

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
                    DialogBudgetFragment dio = DialogBudgetFragment.getDio(SettingActivity.this);
                    dio.show(fm, "");

                    dio.getDialog();
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


        createBacup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //     getUrl();
                downloadData("localhost:49399/Files/46c1b090-25aa-452e-ae20-9077c8a23079.txt");


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
                                Toast.makeText(getApplication(), "gggg", Toast.LENGTH_LONG).show();
                                String data = response.getString("Url");
                                downloadData(data);
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

    void downloadData(String url) {
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);


    }

    @Override
    public void onClick(User user) {


        imageBudget.setVisibility(View.VISIBLE);
        tvBudget.setText(budget + "      " + user.getBadgetValue());
        budgetFlag = 1;
        if (user.getBadgetSelected() == false) {
            imageBudget.setVisibility(View.INVISIBLE);

            removeBudget();
            us = shar.getUser(getApplicationContext());
            tvBudget.setText(budget);
            budgetFlag = 0;

        }
    }

    class DownloadTask extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setTitle(getString(R.string.download_progress));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {

            String path = params[0];
            int fileLength = 0;
            try {
                java.net.URL url = new java.net.URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                fileLength = urlConnection.getContentLength();
                File newFolder = new File("DataMangement/AllData");

                if (!newFolder.exists()) {
                    newFolder.mkdir();
                }
                File inputFile = new File(newFolder, "download_data.txt");
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;

                OutputStream outputStream = new FileOutputStream(inputFile);
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    outputStream.write(data, 0, count);
                    int progress = total * 100 / fileLength;
                    publishProgress(progress);

                }
                inputStream.close();
                outputStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return getString(R.string.complete_download);
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

            progressDialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.hide();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}



