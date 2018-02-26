package mab.moneymanagement.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import mab.moneymanagement.R;
import mab.moneymanagement.view.fragment.AccountFragment;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        //TO PUT FRAGMENT ON ACTIVITY
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.credit_activity, new AccountFragment())
                    .commit();
        }


        Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.credit_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
