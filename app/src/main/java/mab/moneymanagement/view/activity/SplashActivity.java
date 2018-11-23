package mab.moneymanagement.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.appus.splash.Splash;

import mab.moneymanagement.R;

public class SplashActivity extends AppCompatActivity {

    Intent myIntent;
    ImageView imageViewSplash ;

    Animation zoomIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






/*
        imageViewSplash = findViewById(R.id.imageView_splash);

        // load animations
        zoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);






        imageViewSplash.startAnimation(zoomIn);


        zoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                myIntent = new Intent(SplashActivity.this, Main2Activity.class);

                startActivity(myIntent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        /*
      myIntent = new Intent(this, Main2Activity.class);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(myIntent);
                finish();
            }
        }, 1000);

*/


    }
}
