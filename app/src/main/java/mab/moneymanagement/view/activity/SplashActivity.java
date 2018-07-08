package mab.moneymanagement.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mab.moneymanagement.R;

public class SplashActivity extends AppCompatActivity {

    Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      myIntent = new Intent(this, Main2Activity.class);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(myIntent);
                finish();
            }
        }, 1000);


    }
}
