package com.example.uts_pbp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.databinding.ActivityHomeBinding;
import com.example.uts_pbp.user.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class HomeActivity extends AppCompatActivity {

    private MaterialTextView tvWelcome, tvName, tvProduk, tvRegis, tvjadwal, tvAbout,tvLogout,tvSet;
    private User profil;
    private View parentView;
    private UserPreferences userPreferences;
    private PreferencesSettings settings;

    ActivityHomeBinding binding;

    private String menu; //variable yang dilemparkan ke fragment untuk tampilan awal fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setActivity(this);

        userPreferences = new UserPreferences(this);
        profil = userPreferences.getUserLogin();
        binding.setUser(profil);

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
        if(!settings.checkDarkMode()){
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
    }
    public View.OnClickListener userMenu = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            menu = getString(R.string.menu_user);
            changeActivity();
        }
    };
    public View.OnClickListener produkMenu = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            menu = getString(R.string.menu_produk);
            changeActivity();
        }
    };
    public View.OnClickListener regisMenu = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            menu = getString(R.string.menu_pendaftaran);
            changeActivity();
        }
    };
    public View.OnClickListener jadwalMenu = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            menu = getString(R.string.menu_jadwal);
            changeActivity();
        }
    };
    public View.OnClickListener aboutMenu = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            menu = getString(R.string.menu_about_us);
            changeActivity();
        }
    };
    public View.OnClickListener settingMenu = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            menu = getString(R.string.menu_settings);
            changeActivity();
        }
    };
    public View.OnClickListener logoutMenu = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            // Jika menu yang dipilih adalah menu Exit, maka tampilkan sebuah dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setMessage(R.string.exit_confirm).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    userPreferences.logout();
                    checkLogin();
                }
            }).show();
        }
    };

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