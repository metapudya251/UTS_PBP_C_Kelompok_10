package com.example.uts_pbp.fragments;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        settings = new PreferencesSettings(getActivity());

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
        if(settings.getCustomTheme().equals("darkTheme")){
            tvUser2.setTextColor(getResources().getColor(R.color.white));
            tvPass2.setTextColor(getResources().getColor(R.color.white));
            tvUser.setTextColor(getResources().getColor(R.color.white));
            tvPass.setTextColor(getResources().getColor(R.color.white));
            parentView.setBackgroundColor(getResources().getColor(R.color.black));
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
