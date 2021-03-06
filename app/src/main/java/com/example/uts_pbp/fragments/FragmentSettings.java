package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp.FragmentActivity;
import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.FragmentSettingsBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

public class FragmentSettings extends Fragment {

    private MaterialTextView tvScreen, tvNight, tvDisplay, tvOrientation;
    private SwitchMaterial switchMaterial;
    private View parentView;
    private PreferencesSettings settings;

    private FragmentSettingsBinding binding;

    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = (PreferencesSettings) getActivity().getApplication();

        switchMaterial = binding.switchBtn;
        tvScreen = binding.tvScreen;
        parentView = binding.parentView;
        tvDisplay = binding.tvDisplay;
        tvNight = binding.tvNight;
        tvOrientation = binding.tvOrientation;
        tvOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opsi Orientation dan Perubahannnya
                showAlertDialog();
            }
        });

        //cek update tema
        loadSharedPreferences();
        initSwitchListener();

        //cek update mode
        loadSharedPreferencesMode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings, container, false);
        View view = binding.getRoot();
        return view;
    }

    private void loadSharedPreferences()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(PreferencesSettings.CUSTOM_THEME, PreferencesSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
        updateView();
    }

    private void initSwitchListener()
    {
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
            {
                if(checked)
                    settings.setCustomTheme(PreferencesSettings.DARK_THEME);
                else
                    settings.setCustomTheme(PreferencesSettings.LIGHT_THEME);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(PreferencesSettings.CUSTOM_THEME, settings.getCustomTheme());
                editor.apply();
                updateView();
            }
        });
    }

    private void updateView() {
        final int black = ContextCompat.getColor(this.getActivity(), R.color.black);
        final int white = ContextCompat.getColor(this.getActivity(), R.color.white);

        if(settings.getCustomTheme().equals(PreferencesSettings.DARK_THEME))
        {
            tvScreen.setTextColor(white);
            tvNight.setTextColor(white);
            tvDisplay.setTextColor(white);
            tvOrientation.setTextColor(white);
            parentView.setBackgroundColor(black);
            switchMaterial.setChecked(true);
        }
        else
        {
            tvScreen.setTextColor(black);
            tvNight.setTextColor(black);
            tvDisplay.setTextColor(black);
            tvOrientation.setTextColor(black);
            parentView.setBackgroundColor(white);
            switchMaterial.setChecked(false);
        }
    }
    private void loadSharedPreferencesMode()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE);
        String mode = sharedPreferences.getString(PreferencesSettings.CUSTOM_MODE, PreferencesSettings.PORTRAIT_MODE);
        settings.setCustomMode(mode);
        updateMode();
    }
    private void updateMode(){
        if (settings.getCustomMode().equals(PreferencesSettings.LANDSCAPE_MODE)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Orientation");
        String[] items = {"Portrait", "Landscape"};
        int checkedItem;

        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            checkedItem = 1;
        } else {
            checkedItem = 0;
        }

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        settings.setCustomMode(PreferencesSettings.PORTRAIT_MODE);
                        break;
                    case 1:
                        settings.setCustomMode(PreferencesSettings.LANDSCAPE_MODE);
                        break;
                }

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(PreferencesSettings.CUSTOM_MODE, settings.getCustomMode());
                editor.apply();
                updateMode();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
}
