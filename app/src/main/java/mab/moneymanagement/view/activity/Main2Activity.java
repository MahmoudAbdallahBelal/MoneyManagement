package mab.moneymanagement.view.activity;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.dialog.DialogAddItemFragment;
import mab.moneymanagement.view.fragment.DailyFragment;
import mab.moneymanagement.view.interfaces.InterfaceItem;
import mab.moneymanagement.view.model.Item;
import mab.moneymanagement.view.model.User;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, InterfaceItem {

    public static Boolean isLoggin = false;
    ViewPager mViewPager;
    SectionBageAdapter mSectionBageAdapter;
    TabLayout mTablLayout;
    SharedPreference shar;
    User user;


    String user_info_url = URL.PATH + URL.USER_INFO;
    public static InterfaceItem interfaceItem;


    public static void setListner(InterfaceItem interfaceIt) {
        interfaceItem = interfaceIt;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shar = new SharedPreference();
        user = new User();


        //--------------------------------------check current user--------------
        String tt = shar.getValue(getApplicationContext());
        shar.saveUser(getApplicationContext(), getUserData());

        if (tt.equals("null null")) {
            shar.removeValue(getApplicationContext());
            Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        }

        //--------------------------------------------------------------------------------


        //-------------View Pager ------
        mViewPager = findViewById(R.id.main_tab_pager);
        mSectionBageAdapter = new SectionBageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionBageAdapter);

        mTablLayout = findViewById(R.id.main_tabs);
        mTablLayout.setupWithViewPager(mViewPager);

        //----------------------------

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                DialogAddItemFragment dio = DialogAddItemFragment.getDio(Main2Activity.this);
                dio.show(fm, "");


            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chart) {
            Intent chartIntent = new Intent(getApplicationContext(), ChartActivity.class);
            startActivity(chartIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_account) {

            Intent personalIntent = new Intent(getApplicationContext(), PersonalAccountActivity.class);
            startActivity(personalIntent);

        } else if (id == R.id.nav_category) {
            Intent categoryIntent = new Intent(getApplicationContext(), Category.class);
            startActivity(categoryIntent);


        } else if (id == R.id.nav_account) {
            Intent accountIntent = new Intent(getApplicationContext(), AccountActivity.class);
            startActivity(accountIntent);


        } else if (id == R.id.nav_expect) {

            Intent expectIntent = new Intent(getApplicationContext(), ExpectedActivity.class);
            startActivity(expectIntent);

        } else if (id == R.id.nav_monthly_static) {
            Intent monthlyStaticsIntent = new Intent(getApplicationContext(), MonthlyStatics.class);
            startActivity(monthlyStaticsIntent);


        } else if (id == R.id.nav_calender) {
            Intent calenderIntent = new Intent(getApplicationContext(), CalenderActivity.class);
            startActivity(calenderIntent);


        } else if (id == R.id.nav_setting) {
            Intent settingIntent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(settingIntent);


        } else if (id == R.id.nav_choose_date) {
            Intent choosedateIntent = new Intent(getApplicationContext(), ChooseActivity.class);
            startActivity(choosedateIntent);

        } else if (id == R.id.nav_logout) {

            //change status of loggin
            shar.removeValue(getApplicationContext());
            shar.clearSharedPreference(getApplicationContext());
            shar.removeUser(getApplicationContext());
            // Toast.makeText(getApplicationContext(), shar.getValue(getApplicationContext()), Toast.LENGTH_LONG).show();

            Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        user = getUserData();

    }

    private User getUserData() {

        final User user1 = new User();
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, user_info_url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            //----------HANDEL MESSAGE COME FROM REQUEST -------------------
                            String message = response.getString("RequstDetails");

                            if (message.equals("Details User Returned")) {
                                ///**----put user data in object
                                user1.setEmail(response.getString("Email"));
                                user1.setFullName(response.getString("FullName"));
                                user1.setCurrency(response.getInt("ConcuranceyId") - 1);
                                user1.setDailyAlert(response.getBoolean("DailyAlert"));
                                user1.setBadgetSelected(response.getBoolean("BadgetSelected"));
                                user1.setBadgetValue(response.getInt("BadgetValue"));
                                user1.setBegainDayOfWeek(response.getInt("BegainDay"));

                                shar.saveUser(getApplicationContext(), user1);


                            } else {

                                Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(logoutIntent);
                                shar.removeValue(getApplicationContext());
                                finish();

                            }


                            // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getUserData();

                        //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

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

        return user1;
    }


    @Override
    public void onClick(Item item) {
        interfaceItem.onClick(item);

    }
}
