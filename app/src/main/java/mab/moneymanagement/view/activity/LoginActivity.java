package mab.moneymanagement.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appus.splash.Splash;

import mab.moneymanagement.R;
import mab.moneymanagement.view.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_login);


        Splash.Builder splash = new Splash.Builder(this, getSupportActionBar());


        splash.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        splash.setSplashImage(getResources().getDrawable(R.drawable.main_logo));


        splash.perform();





        //TO PUT FRAGMENT ON ACTIVITY
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.login_activity, new LoginFragment())
                    .commit();
        }




    }
}
