package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

public class FragmentSettings extends Fragment {

    private MaterialTextView tvScreen, tvNight, tvDisplay, tvOrientation;
    private SwitchMaterial switchMaterial;
    private View parentView;
    private PreferencesSettings settings;

    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = (PreferencesSettings) getActivity().getApplication();

        switchMaterial = view.findViewById(R.id.switchBtn);
        tvScreen = view.findViewById(R.id.tv_screen);
        parentView = view.findViewById(R.id.parentView);
        tvDisplay = view.findViewById(R.id.tv_display);
        tvNight = view.findViewById(R.id.tv_night);
        tvOrientation = view.findViewById(R.id.tv_orientation);
        tvOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        //cek update tema
        loadSharedPreferences();
        initSwitchListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
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
}
