package com.TMAProject.EzzSteel.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.TMAProject.EzzSteel.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Picasso.with(this).load(R.drawable.splash_img_1).fit()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(((ImageView) findViewById(R.id.splash_img)));
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                System.gc();
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
