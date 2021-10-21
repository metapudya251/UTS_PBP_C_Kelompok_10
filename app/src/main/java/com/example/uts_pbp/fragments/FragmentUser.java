package com.example.uts_pbp.fragments;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.R;
import com.example.uts_pbp.User.User;
import com.google.android.material.textview.MaterialTextView;

public class FragmentUser extends Fragment {
    private MaterialTextView tvUser, tvPass,tvUser2, tvPass2;
    private View parentView;
    private User profil;
    private UserPreferences userPreferences;
    private PreferencesSettings settings;
    private int check = 1;

    public FragmentUser() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = new PreferencesSettings(getActivity());

        userPreferences = new UserPreferences(this.getActivity());

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

        profil = userPreferences.getUserLogin();

        tvUser2.setText(""+profil.getUsername());
        tvPass2.setText(""+profil.getPassword());

        tvPass2.setOnClickListener(new View.OnClickListener() {
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
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

}
