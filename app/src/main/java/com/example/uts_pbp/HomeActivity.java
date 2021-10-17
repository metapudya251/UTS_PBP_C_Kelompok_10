package com.example.uts_pbp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.User.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class HomeActivity extends AppCompatActivity {

    private ImageView user,produk,regis,jadwal,about, data, set;
    private MaterialTextView tvName;
    private User profil;
    private MaterialButton btnLogout;
    private UserPreferences userPreferences;

    private String menu; //variable yang dilemparkan ke fragment untuk tampilan awal fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreferences = new UserPreferences(HomeActivity.this);
        checkLogin();

        setContentView(R.layout.activity_home);
        setTitle("Home");

        tvName = findViewById(R.id.tv_name);
        btnLogout = findViewById(R.id.btnLogout);

        user = findViewById(R.id.imageView2);
        produk = findViewById(R.id.imageView3);
        regis = findViewById(R.id.imageView4);
        jadwal = findViewById(R.id.imageView5);
        about = findViewById(R.id.imageView7);
        data = findViewById(R.id.imageView8);
        set = findViewById(R.id.imageView9);

        profil = userPreferences.getUserLogin();

        tvName.setText(""+profil.getUsername());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferences.logout();
                Toast.makeText(HomeActivity.this, "Selamat Tinggal", Toast.LENGTH_SHORT).show();
                checkLogin();
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                menu = getString(R.string.menu_user);
                changeActivity();
            }
        });

        produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                menu = getString(R.string.menu_produk);
                changeActivity();
            }
        });
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                menu = getString(R.string.menu_pendaftaran);
                changeActivity();
            }
        });
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                menu = getString(R.string.menu_jadwal);
                changeActivity();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                menu = getString(R.string.menu_about_us);
                changeActivity();
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                menu = getString(R.string.menu_settings);
                changeActivity();
            }
        });
    }
    private void checkLogin(){
        if(!userPreferences.checkLogin()){
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
    }

    private void changeActivity () {
        Intent i = new Intent(HomeActivity.this,FragmentActivity.class);
        i.putExtra("menu",menu);
        startActivity(i);
    }
}