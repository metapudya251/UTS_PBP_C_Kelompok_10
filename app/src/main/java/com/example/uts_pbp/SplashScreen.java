package com.example.uts_pbp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uts_pbp.Preferences.UserPreferences;

public class SplashScreen extends AppCompatActivity {

    ImageView imgLogo;
    TextView textView;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //menghilangkan ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        userPreferences = new UserPreferences(SplashScreen.this);

        imgLogo = (ImageView) findViewById(R.id.splash_logo);
        textView = (TextView) findViewById(R.id.textView);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imgLogo.startAnimation(animation);
        textView.startAnimation(animation);

        checkLogin();
    }
    private void checkLogin() {
        if (userPreferences.checkLogin()) {
            startActivity(new Intent(SplashScreen.this, HomeActivity.class));
            finish();
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }, 5000L); //5000 L = 5 detik
        }
    }
}