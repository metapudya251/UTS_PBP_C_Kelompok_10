package com.example.uts_pbp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
    public static Activity homeActivity;

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

        homeActivity = this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setActivity(this);

        userPreferences = new UserPreferences(this);
        profil = userPreferences.getUserLogin();
        binding.setUser(profil);

        settings = (PreferencesSettings) getApplication();

        setTitle("Home");

        //cek user login
        checkLogin();

        parentView = binding.parentView2;
        tvName = binding.tvName;
        tvWelcome = binding.tvWelcome;
        tvAbout = binding.tvAbout;
        tvjadwal = binding.tvJadwal;
        tvLogout = binding.tvLogout;
        tvProduk = binding.tvProduk;
        tvRegis = binding.tvPendaftaran;
        tvSet = binding.tvSetting;

        //cek update tema
        loadSharedPreferences();
        //cek update mode
        loadSharedPreferencesMode();

    }

    //LOAD PREFERENCENYA INI BUAT NGECEK TAMPILAN AWAL
    private void loadSharedPreferences()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(PreferencesSettings.CUSTOM_THEME, PreferencesSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);

        String mode = sharedPreferences.getString(PreferencesSettings.CUSTOM_MODE, PreferencesSettings.PORTRAIT_MODE);
        settings.setCustomMode(mode);

        updateView();
    }
    private void updateView() {
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        if(settings.getCustomTheme().equals(PreferencesSettings.DARK_THEME))
        {
            tvName.setTextColor(white);
            tvWelcome.setTextColor(white);
            tvAbout.setTextColor(white);
            tvjadwal.setTextColor(white);
            tvLogout.setTextColor(white);
            tvProduk.setTextColor(white);
            tvRegis.setTextColor(white);
            tvSet.setTextColor(white);
            parentView.setBackgroundColor(black);
        }
        else
        {
            tvName.setTextColor(black);
            tvWelcome.setTextColor(black);
            tvAbout.setTextColor(black);
            tvjadwal.setTextColor(black);
            tvLogout.setTextColor(black);
            tvProduk.setTextColor(black);
            tvRegis.setTextColor(black);
            tvSet.setTextColor(black);
            parentView.setBackgroundColor(white);
        }
    }
    private void loadSharedPreferencesMode()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE);
        String mode = sharedPreferences.getString(PreferencesSettings.CUSTOM_MODE, PreferencesSettings.PORTRAIT_MODE);
        settings.setCustomMode(mode);
        updateMode();
    }
    private void updateMode(){
        if (settings.getCustomMode().equals(PreferencesSettings.LANDSCAPE_MODE)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }
}