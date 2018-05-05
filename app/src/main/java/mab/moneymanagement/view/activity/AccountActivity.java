package mab.moneymanagement.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mab.moneymanagement.R;
import mab.moneymanagement.view.fragment.AccountFragment;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //TO PUT FRAGMENT ON ACTIVITY
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.account_activity, new AccountFragment())
                    .commit();
        }


        Toolbar mToolbar = findViewById(R.id.account_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.account_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mToolbar.setClickable(true);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maIntent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(maIntent);
                finish();
            }
        });


    }


}
