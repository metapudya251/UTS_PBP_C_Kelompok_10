package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.FragmentPendaftaranBinding;
import com.example.uts_pbp.models.Jadwal;
import com.example.uts_pbp.models.JadwalResponse;
import com.example.uts_pbp.models.Petugas;
import com.example.uts_pbp.models.PetugasResponse;
import com.example.uts_pbp.models.Produk;
import com.example.uts_pbp.models.ProdukResponse;
import com.example.uts_pbp.retrofit.api.ApiClient;
import com.example.uts_pbp.retrofit.api.ApiInterface;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPendaftaran extends Fragment {
    //DEKLARASI OBJEK DAN VARIABEL
    private Jadwal jdwl;
    private FragmentPendaftaranBinding binding;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private PreferencesSettings settings;
    private View parentView;

    private ApiInterface apiServiceProduk, apiServicePetugas, apiServiceJadwal;
    private ArrayList<Petugas> listPetugas;

    public FragmentPendaftaran() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_pendaftaran, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiServiceProduk = ApiClient.getClient().create(ApiInterface.class);
        apiServicePetugas = ApiClient.getClient().create(ApiInterface.class);
        apiServiceJadwal = ApiClient.getClient().create(ApiInterface.class);

        settings = (PreferencesSettings) getActivity().getApplication();

        //INISIALISASI OBJEK DAN VARIABEL
        jdwl = new Jadwal();
        binding.setDftr(jdwl);
        binding.setActivity(this);

        UserPreferences userPreferences = new UserPreferences(getContext());

        jdwl.setNama(userPreferences.getUserLogin().getUsername());
        binding.etNama.setEnabled(false);

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

        parentView = binding.viewPendaftaran;

        //cek update tema
        loadSharedPreferences();
        //cek update mode
        loadSharedPreferencesMode();

        //ku enggak paham gimana cara kerjanya tapi ini bekerja -- WKWKWKK gpp seadanya dulu
        //dropdown menu
        //Produk
        getAllProdukName();

        //Pencukur
        getAllPetugasName();
    }

    public View.OnClickListener btnProduk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //kosong

        }
    };

    public View.OnClickListener btnNamaPencukur = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    //MEMBUAT RESPONSE CLICK LISTENER YANG AKAN DIGUNAKAN PADA LAYOUT TANGGAL MASUK PEGAWAI
    public View.OnClickListener btnTanggal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar newCalendar = Calendar.getInstance();
            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    //data tanggal
                    jdwl.setTanggal(dateFormatter.format(newDate.getTime()));
                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }
    };

    //MEMBUAT RESPONSE CLICK LISTENER YANG AKAN DIGUNAKAN PADA BUTTON KEMBALI
    public View.OnClickListener btnBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().finish();
        }
    };

    //MEMBUAT RESPONSE CLICK LISTENER YANG AKAN DIGUNAKAN PADA BUTTON SIMPAN
    public View.OnClickListener btnSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            jdwl.setNama(binding.etNama.getText().toString());
            jdwl.setTelp(binding.etNomorTelp.getText().toString());
            jdwl.setPelayanan(binding.etProduk.getText().toString());
            jdwl.setPetugas(binding.etNamaPencukur.getText().toString());
            //get imgUrl
            for (int i=0;i<listPetugas.size();i++) {
                if (jdwl.getPetugas().equals(listPetugas.get(i).getNama())) {
                    jdwl.setImgUrl(listPetugas.get(i).getImgUrl());
                    break;
                }
            }

            //todos
            addJadwal();
        }
    };

    private void addJadwal(){
        Call<JadwalResponse> call = apiServiceJadwal.createJadwal(jdwl);

        call.enqueue(new Callback<JadwalResponse>() {
            @Override
            public void onResponse(Call<JadwalResponse> call, Response<JadwalResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    //kembali ke FragmentActivity
                    getActivity().finish();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(),
                                jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(),
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JadwalResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //get list produk name from API
    private void getAllProdukName() {
        Call<ProdukResponse> call = apiServiceProduk.getAllProduk();

        call.enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call,
                                   Response<ProdukResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Produk> listProduk = response.body().getProdukList();
                    String [] PRODUK = new String[listProduk.size()];
                    for (int i=0;i<listProduk.size();i++) {
                        PRODUK[i] = listProduk.get(i).getNama();
                    }

                    ArrayAdapter<String> adapter1 = new ArrayAdapter(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, PRODUK);
                    binding.etProduk.setAdapter(adapter1);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(),
                                jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
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

    //get list name petugas from API
    private void getAllPetugasName() {
        Call<PetugasResponse> call = apiServicePetugas.getAllPetugas();

        call.enqueue(new Callback<PetugasResponse>() {
            @Override
            public void onResponse(Call<PetugasResponse> call,
                                   Response<PetugasResponse> response) {
                if (response.isSuccessful()) {
                    listPetugas = response.body().getPetugasList();
                    String [] PETUGAS = new String[listPetugas.size()];
                    for (int i=0;i<listPetugas.size();i++) {
                        PETUGAS[i] = listPetugas.get(i).getNama();
                    }

                    ArrayAdapter<String> adapter2 = new ArrayAdapter(getActivity(),
                            android.R.layout.simple_dropdown_item_1line, PETUGAS);
                    binding.etNamaPencukur.setAdapter(adapter2);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(),
                                jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(),
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PetugasResponse> call, Throwable t) {
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
            //Maaf aku buat hitam backgroundnya aja, cardviewnya engga :) --- Np
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
