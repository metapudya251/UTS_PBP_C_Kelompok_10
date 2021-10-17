package com.example.uts_pbp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp.Fragments.FragmentAboutUs;
import com.example.uts_pbp.Fragments.FragmentJadwal;
import com.example.uts_pbp.Fragments.FragmentPendaftaran;
import com.example.uts_pbp.Fragments.FragmentProduk;
import com.example.uts_pbp.Fragments.FragmentSettings;
import com.example.uts_pbp.Fragments.FragmentUser;

public class FragmentActivity extends AppCompatActivity {
    private String menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //ambil menu dari intent
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                menu = null;
            } else {
                menu = extras.getString("menu");
            }
        } else {
            menu = savedInstanceState.getString("menu");
        }

        setTitle(menu);

        if (menu == getString(R.string.menu_user)) {
            changeFragment(new FragmentUser());
        } else if (menu == getString(R.string.menu_produk)) {
            changeFragment(new FragmentProduk());
        } else if (menu == getString(R.string.menu_pendaftaran)) {
            changeFragment(new FragmentPendaftaran());
        } else if (menu == getString(R.string.menu_jadwal)) {
            changeFragment(new FragmentJadwal());
        } else if (menu == getString(R.string.menu_about_us)) {
            changeFragment(new FragmentAboutUs());
        } else if (menu == getString(R.string.menu_settings)) {
            changeFragment(new FragmentSettings());
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.fragment_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_home) {
            Intent i = new Intent(FragmentActivity.this, HomeActivity.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.menu_produk) {
            setTitle(getString(R.string.menu_produk));
            changeFragment (new Fragment());
        } else if (item.getItemId() == R.id.menu_pendaftaraan) {
            setTitle(getString(R.string.menu_pendaftaran));
            changeFragment (new Fragment());
        } else if (item.getItemId() == R.id.menu_jadwal) {
            setTitle(getString(R.string.menu_jadwal));
            changeFragment (new Fragment());
        } else if (item.getItemId() == R.id.menu_about_us) {
            setTitle(getString(R.string.menu_about_us));
            changeFragment (new Fragment());
        } else if (item.getItemId() == R.id.menu_settings) {
            setTitle(getString(R.string.menu_settings));
            changeFragment (new Fragment());
        } else {
            // Jika menu yang dipilih adalah menu Exit, maka tampilkan sebuah dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.exit_confirm).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finishAndRemoveTask();
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFragment (Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment,fragment)
                .commit();
    }
}