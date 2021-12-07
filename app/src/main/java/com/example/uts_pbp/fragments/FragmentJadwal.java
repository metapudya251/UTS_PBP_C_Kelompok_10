package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.FragmentJadwalBinding;
import com.example.uts_pbp.models.Jadwal;
import com.example.uts_pbp.models.JadwalResponse;
import com.example.uts_pbp.recyclerViews.RVJadwalAdapter;
import com.example.uts_pbp.retrofit.api.ApiClient;
import com.example.uts_pbp.retrofit.api.ApiInterface;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentJadwal extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FragmentJadwalBinding binding;
    private RVJadwalAdapter jadwalAdapter;

    private View parentView;
    private SwipeRefreshLayout swiperefresh;
    private PreferencesSettings settings;

    private ApiInterface apiService;
    private ArrayList<Jadwal> listJadwal;

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

        apiService = ApiClient.getClient().create(ApiInterface.class);

        settings = (PreferencesSettings) getActivity().getApplication();

        jadwalAdapter = new RVJadwalAdapter(listJadwal,getActivity());

        parentView = binding.viewJadwal;
        swiperefresh = binding.swiperefresh;
        swiperefresh.setOnRefreshListener(this);

        //cek update Tema
        loadSharedPreferences();
        //cek update mode
        loadSharedPreferencesMode();

        // Set Layout Manager dari recycler view
        binding.rvJadwal.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        // Set Adapter dari recycler view
//        getAllJadwal();

        //panggil fungsi swipe
        onLoadingSwipe ();
    }

    //get list jadwal from API
    private void getAllJadwal() {
        Call<JadwalResponse> call = apiService.getAllJadwal();

        swiperefresh.setRefreshing(true);

        call.enqueue(new Callback<JadwalResponse>() {
            @Override
            public void onResponse(Call<JadwalResponse> call,
                                   Response<JadwalResponse> response) {
                if (response.isSuccessful()) {
                    listJadwal = new ArrayList<>();
                    UserPreferences userPreferences = new UserPreferences(getContext());
                    for (int i=0; i<response.body().getJadwalList().size();i++) {
                        if (response.body().getJadwalList().get(i).getNama().equals(userPreferences.getUserLogin().getUsername())) {
                            listJadwal.add(response.body().getJadwalList().get(i));
                        }
                    }
                    jadwalAdapter = new RVJadwalAdapter(listJadwal,getActivity());
                    //jadwalAdapter.setJadwalList(response.body().getJadwalList());
                    binding.setJadwaladapter(jadwalAdapter);
                    swiperefresh.setRefreshing(false);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        swiperefresh.setRefreshing(false);
                        Toast.makeText(getContext(),
                                jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        swiperefresh.setRefreshing(false);
                        Toast.makeText(getContext(),
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JadwalResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onRefresh() {
        getAllJadwal();
    }

    private void onLoadingSwipe (){
        swiperefresh.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getAllJadwal();
                    }
                }
        );
    }
}
