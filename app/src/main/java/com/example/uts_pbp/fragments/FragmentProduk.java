package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pbp.Dummy.DaftarProduk;
import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.entity.Produk;
import com.example.uts_pbp.R;
import com.example.uts_pbp.recyclerViews.RVProdukAdapter;
import com.example.uts_pbp.databinding.FragmentProdukBinding;

import java.util.ArrayList;

public class FragmentProduk extends Fragment {
    private ArrayList<Produk> listProduk;
    private FragmentProdukBinding binding;
    private View parentView;
    private PreferencesSettings settings;

    public FragmentProduk() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_produk, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listProduk = new DaftarProduk().listProduk;

        settings = (PreferencesSettings) getActivity().getApplication();

        parentView = view.findViewById(R.id.viewProduk);

        //cek update tema
        loadSharedPreferences();
        //cek update mode
        loadSharedPreferencesMode();

        binding.rvProduk.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        RVProdukAdapter myRecyclerViewAdapter = new RVProdukAdapter(listProduk,getActivity());
        binding.setProdukadapter(myRecyclerViewAdapter);
    }

    //LOAD PREFERENCENYA INI BUAT NGECEK TAMPILAN AWAL
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
            parentView.setBackgroundColor(black);
        }
        else
        {
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
}
