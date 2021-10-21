package com.example.uts_pbp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.User.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class HomeActivity extends AppCompatActivity {

    private ImageView user,produk,regis,jadwal,about, logout, set;
    private MaterialTextView tvWelcome, tvName, tvProduk, tvRegis, tvjadwal, tvAbout,tvLogout,tvSet;
    private User profil;
    private View parentView;
    private MaterialButton btnLogout;
    private UserPreferences userPreferences;
    private PreferencesSettings settings;

    private String menu; //variable yang dilemparkan ke fragment untuk tampilan awal fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        setTitle("Home");

        //cek user login
        userPreferences = new UserPreferences(HomeActivity.this);
        checkLogin();

        parentView = findViewById(R.id.parentView2);
        tvName = findViewById(R.id.tv_name);
        tvWelcome = findViewById(R.id.tv_welcome);
        tvAbout = findViewById(R.id.tv_about);
        tvjadwal = findViewById(R.id.tv_jadwal);
        tvLogout = findViewById(R.id.tv_logout);
        tvProduk = findViewById(R.id.tv_produk);
        tvRegis = findViewById(R.id.tv_pendaftaran);
        tvSet = findViewById(R.id.tv_setting);

        //cek update tema
        settings = new PreferencesSettings(HomeActivity.this);
        if(settings.getCustomTheme().equals("darkTheme")){
            tvName.setTextColor(getResources().getColor(R.color.white));
            tvWelcome.setTextColor(getResources().getColor(R.color.white));
            tvAbout.setTextColor(getResources().getColor(R.color.white));
            tvjadwal.setTextColor(getResources().getColor(R.color.white));
            tvLogout.setTextColor(getResources().getColor(R.color.white));
            tvProduk.setTextColor(getResources().getColor(R.color.white));
            tvRegis.setTextColor(getResources().getColor(R.color.white));
            tvSet.setTextColor(getResources().getColor(R.color.white));
            parentView.setBackgroundColor(getResources().getColor(R.color.black));
        }

        userPreferences = new UserPreferences(HomeActivity.this);
        profil = userPreferences.getUserLogin();

        tvName.setText(""+profil.getUsername());

        user = findViewById(R.id.imageView2);
        produk = findViewById(R.id.imageView3);
        regis = findViewById(R.id.imageView4);
        jadwal = findViewById(R.id.imageView5);
        about = findViewById(R.id.imageView7);
        logout = findViewById(R.id.imageView8);
        set = findViewById(R.id.imageView9);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu = getString(R.string.menu_user);
                changeActivity();
            }
        });

        produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu = getString(R.string.menu_produk);
                changeActivity();
            }
        });
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu = getString(R.string.menu_pendaftaran);
                changeActivity();
            }
        });
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu = getString(R.string.menu_jadwal);
                changeActivity();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu = getString(R.string.menu_about_us);
                changeActivity();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage(R.string.exit_confirm).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userPreferences.logout();
                        checkLogin();
                    }
                }).show();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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