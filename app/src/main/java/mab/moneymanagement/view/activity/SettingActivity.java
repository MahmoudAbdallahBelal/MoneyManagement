package mab.moneymanagement.view.activity;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import mab.moneymanagement.R;
import mab.moneymanagement.view.dialog.DialogBudgetFragment;
import mab.moneymanagement.view.dialog.DialogLanguageFragment;

public class SettingActivity extends AppCompatActivity {


    CheckedTextView tvBudget;
    TextView tvLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.nav_setting));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //-----------
        tvBudget = findViewById(R.id.setting_check_tv_budget);
        tvLanguage = findViewById(R.id.setting_tv_select_language);


        //-------------
        tvBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                DialogBudgetFragment dialogFragment = new DialogBudgetFragment();
                dialogFragment.show(fm, "");

            }
        });

        tvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fm = getFragmentManager();
                DialogLanguageFragment dialogFragment = new DialogLanguageFragment();
                dialogFragment.show(fm, "");


            }
        });
    }


    }