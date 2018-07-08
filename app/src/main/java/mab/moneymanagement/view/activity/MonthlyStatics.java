package mab.moneymanagement.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import mab.moneymanagement.R;
import mab.moneymanagement.view.fragment.MonthlystaticsFragment;

public class MonthlyStatics extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_statics);

        //TO PUT FRAGMENT ON ACTIVITY
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.monthly_statics_activity, new MonthlystaticsFragment())
                    .commit();
        }


        Toolbar mToolbar = findViewById(R.id.monthly_statics_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.nav_monthly_statics));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
