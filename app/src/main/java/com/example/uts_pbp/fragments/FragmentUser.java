package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.R;
import com.example.uts_pbp.user.User;
import com.example.uts_pbp.databinding.FragmentUserBinding;
import com.google.android.material.textview.MaterialTextView;

public class FragmentUser extends Fragment {
    private MaterialTextView tvUser,tvUser2,tvPass, tvPass2;
    private View parentView;
    private User profil;
    private UserPreferences userPreferences;
    private PreferencesSettings settings;

    private FragmentUserBinding binding;

    public FragmentUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = (PreferencesSettings) getActivity().getApplication();

        userPreferences = new UserPreferences(this.getActivity());
        profil = userPreferences.getUserLogin();
        binding.setUser(profil);
        binding.setActivity(this);

        tvUser2 = view.findViewById(R.id.tv_user2);
        tvPass2 = view.findViewById(R.id.tv_pass2);
        tvUser = view.findViewById(R.id.tv_user);
        tvPass = view.findViewById(R.id.tv_pass);
        parentView = view.findViewById(R.id.viewUser);

        //cek update tema
        loadSharedPreferences();

        //cek update mode
        loadSharedPreferencesMode();
    }

    private void loadSharedPreferences()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PreferencesSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(PreferencesSettings.CUSTOM_THEME, PreferencesSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
        updateView();
    }

    private void updateView() {
        final int black = ContextCompat.getColor(this.getActivity(), R.color.black);
        final int white = ContextCompat.getColor(this.getActivity(), R.color.white);

        if(settings.getCustomTheme().equals(PreferencesSettings.DARK_THEME))
        {
            tvUser2.setTextColor(white);
            tvPass2.setTextColor(white);
            tvUser.setTextColor(white);
            tvPass.setTextColor(white);
            parentView.setBackgroundColor(black);
        }
        else
        {
            tvUser2.setTextColor(black);
            tvPass2.setTextColor(black);
            tvUser.setTextColor(black);
            tvPass.setTextColor(black);
            parentView.setBackgroundColor(white);
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

    public View.OnClickListener tvPass3 = new View.OnClickListener() {
        int check = 1;
        @Override
        public void onClick(View view) {
            if(check==1){
                tvPass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                check=-1;
            }else{
                tvPass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                check=+1;
            }
        }
    };
}
