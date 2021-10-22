package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.FragmentJadwalBinding;
import com.example.uts_pbp.entity.Jadwal;
import com.example.uts_pbp.recyclerViews.RVJadwalAdapter;
import com.example.uts_pbp.room.database.DatabaseJadwal;

import java.util.List;

public class FragmentJadwal extends Fragment {
    private FragmentJadwalBinding binding;
    private RVJadwalAdapter jadwalAdapter;

    private View parentView;
    private PreferencesSettings settings;

    public FragmentJadwal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_jadwal, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = (PreferencesSettings) getActivity().getApplication();

        parentView = view.findViewById(R.id.viewJadwal);
        //cek update Tema
        loadSharedPreferences();

        // Set Layout Manager dari recycler view
        binding.rvJadwal.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        // Set Adapter dari recycler view
        getJadwals();
    }

    private void getJadwals() {
        class GetJadwals extends AsyncTask<Void, Void, List<Jadwal>> {

            @Override
            protected List<Jadwal> doInBackground(Void... voids) {
                List<Jadwal> jadwalList = DatabaseJadwal.getInstance(getActivity().getApplicationContext())
                        .getDatabase()
                        .jadwalDao()
                        .getAll();
                return jadwalList;
            }

            @Override
            protected void onPostExecute(List<Jadwal> jadwals) {
                super.onPostExecute(jadwals);
                jadwalAdapter = new RVJadwalAdapter(jadwals, getActivity());
                binding.setJadwaladapter(jadwalAdapter);
            }
        }

        GetJadwals getJadwals = new GetJadwals();
        getJadwals.execute();
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
}
