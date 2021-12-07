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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.models.Produk;
import com.example.uts_pbp.R;
import com.example.uts_pbp.models.ProdukResponse;
import com.example.uts_pbp.recyclerViews.RVProdukAdapter;
import com.example.uts_pbp.databinding.FragmentProdukBinding;
import com.example.uts_pbp.retrofit.api.ApiClient;
import com.example.uts_pbp.retrofit.api.ApiInterface;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProduk extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<Produk> listProduk;
    private FragmentProdukBinding binding;
    private View parentView;
    private SwipeRefreshLayout swiperefresh;
    private PreferencesSettings settings;
    private ApiInterface apiService;
    private RVProdukAdapter myRecyclerViewAdapter;

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

        apiService = ApiClient.getClient().create(ApiInterface.class);

//        getAllProduk();

        settings = (PreferencesSettings) getActivity().getApplication();

        parentView = binding.viewProduk;
        swiperefresh = binding.swiperefresh;
        swiperefresh.setOnRefreshListener(this);

        //cek update tema
        loadSharedPreferences();
        //cek update mode
        loadSharedPreferencesMode();

        binding.rvProduk.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        //panggil fungsi swipe
        onLoadingSwipe ();
    }

    //get list produk from API
    private void getAllProduk() {
        Call<ProdukResponse> call = apiService.getAllProduk();

        swiperefresh.setRefreshing(true);

        call.enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call,
                                   Response<ProdukResponse> response) {
                if (response.isSuccessful()) {
                    myRecyclerViewAdapter = new RVProdukAdapter(listProduk,getActivity());
                    myRecyclerViewAdapter.setProdukList(response.body().getProdukList());
                    binding.setProdukadapter(myRecyclerViewAdapter);
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
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
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
        getAllProduk();
    }

    private void onLoadingSwipe (){
        swiperefresh.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getAllProduk();
                    }
                }
        );
    }
}
