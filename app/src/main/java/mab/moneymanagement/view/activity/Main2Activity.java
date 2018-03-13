package mab.moneymanagement.view.activity;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;


import mab.moneymanagement.R;
import mab.moneymanagement.view.dialog.DialogAddItemFragment;
import mab.moneymanagement.view.sharedPrefrence.SharedPreference;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinner;
    ViewPager mViewPager;
    SectionBageAdapter mSectionBageAdapter;
    TabLayout mTablLayout;
    public static Boolean isLoggin = false;
    SharedPreference shar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shar = new SharedPreference();
        String tt=shar.getValue(getApplicationContext());
        if (tt.equals("null null")) {
            Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        }

        //-------------View Pager ------
        mViewPager = (ViewPager) findViewById(R.id.main_tab_pager);
        mSectionBageAdapter = new SectionBageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionBageAdapter);

        mTablLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTablLayout.setupWithViewPager(mViewPager);

        //----------------------------

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  showAddItemDialog();


                FragmentManager fm = getFragmentManager();
                DialogAddItemFragment dialogFragment = new DialogAddItemFragment();
                dialogFragment.show(fm, "");


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            //   Toast.makeText(getApplicationContext(), "111111111111"+accesTocken, Toast.LENGTH_LONG).show();

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
            Toast.makeText(getApplicationContext(),shar.getValue(getApplicationContext()),Toast.LENGTH_LONG).show();

            Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(logoutIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
