package mab.moneymanagement.view.activity;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import mab.moneymanagement.R;
import mab.moneymanagement.util.URL;
import mab.moneymanagement.util.helperChache;
import mab.moneymanagement.view.Volley.MysingleTon;
import mab.moneymanagement.view.dialog.DialogAddItemFragment;
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
    String cc;


    String user_info_url = URL.PATH + URL.USER_INFO;
    public static InterfaceItem interfaceItem;


    public static void setListner(InterfaceItem interfaceIt) {
        interfaceItem = interfaceIt;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //-----------check language--------
        try {
            shar = new SharedPreference();
            cc = shar.getLanguage(getApplicationContext());
            if (cc.equals(null)) {
            } else
                setLocate(cc);
        } catch (Exception e) {

        }


        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        setSupportActionBar(toolbar);

//        ActionBar actionBar=getActionBar();
//        actionBar.setTitle();

        shar = new SharedPreference();
        user = new User();


        //--------------------------------------check current user--------------
        String tt = shar.getValue(getApplicationContext());
        shar.saveUser(getApplicationContext(), getUserData());

        if (tt.equals("null null")) {
            shar.removeValue(getApplicationContext());
            Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(logoutIntent);
            finish();
        }

        //--------------------------------------------------------------------------------


        //-------------View Pager ------
        mViewPager = findViewById(R.id.main_tab_pager);
        mSectionBageAdapter = new SectionBageAdapter(getSupportFragmentManager(), getApplicationContext());
        mViewPager.setAdapter(mSectionBageAdapter);

        mTablLayout = findViewById(R.id.main_tabs);
        mTablLayout.setupWithViewPager(mViewPager);

        //-----------------notification -----------
        if (user.isDailyAlert()) {


        }
        //-----------------------------------------





//        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
//        actionC.setTitle("Hide/Show Action above");
//        actionC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//            }
//        });

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        //menuMultipleActions.addButton(actionC);

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        final FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.action_b);


        final TextView txtB = findViewById(R.id.textView_b);
        final TextView txtDepartments = findViewById(R.id.textView_departments);
        txtB.setVisibility(View.INVISIBLE);
        txtDepartments.setVisibility(View.INVISIBLE);



        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {



                    txtB.setVisibility(View.VISIBLE);
                txtDepartments.setVisibility(View.VISIBLE);

            }

            @Override
            public void onMenuCollapsed() {

                txtB.setVisibility(View.INVISIBLE);

                txtDepartments.setVisibility(View.INVISIBLE);
            }
        });

        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent categoryIntent = new Intent(getApplicationContext(), Category.class);
                startActivity(categoryIntent);
            }
        });

        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check before

                if(helperChache.retrieveCountExpense(Main2Activity.this) > 0 && helperChache.retrieveCountIncome(Main2Activity.this) > 0) {
                    FragmentManager fm = getFragmentManager();
                    DialogAddItemFragment dio = DialogAddItemFragment.getDio(Main2Activity.this);

                    dio.show(fm, "");

                }
                else
                {
                    Toast.makeText(Main2Activity.this, getString(R.string.you_must_add_element), Toast.LENGTH_LONG).show();

                }

            }
        });
        // Test that FAMs containing FABs with visibility GONE do not cause crashes






        //========================================================
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                DialogAddItemFragment dio = DialogAddItemFragment.getDio(Main2Activity.this);
                dio.show(fm, "");


            }
        });
*/

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

            Intent personalIntent = new Intent(Main2Activity.this, PersonalAccountActivity.class);
            startActivity(personalIntent);

        } else if (id == R.id.nav_category) {
            Intent categoryIntent = new Intent(getApplicationContext(), Category.class);
            startActivity(categoryIntent);


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
            // shar.removeValue(getApplicationContext());
            shar.removeUser(getApplicationContext());
            shar.save(getApplicationContext(), "null", "null");
            // shar.clearSharedPreference(getApplicationContext());
            // Toast.makeText(getApplicationContext(), shar.getLanguage(getApplicationContext()), Toast.LENGTH_LONG).show();

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

        //-----------check language--------
        try {
            shar = new SharedPreference();
            cc = shar.getLanguage(getApplicationContext());
            if (cc.equals(null)) {
            } else
                setLocate(cc);
        } catch (Exception e) {

        }

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

//                                Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                                startActivity(logoutIntent);
//                                shar.removeValue(getApplicationContext());
//                                finish();

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

    void setLocate(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        shar.saveLanguage(getApplicationContext(), language);

    }

}
