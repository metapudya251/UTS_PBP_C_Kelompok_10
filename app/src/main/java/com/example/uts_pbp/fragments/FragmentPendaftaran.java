package com.example.uts_pbp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp.Dummy.DaftarPetugas;
import com.example.uts_pbp.Dummy.DaftarProduk;
import com.example.uts_pbp.FragmentActivity;
import com.example.uts_pbp.Preferences.PreferencesSettings;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.FragmentPendaftaranBinding;
import com.example.uts_pbp.entity.Jadwal;
import com.example.uts_pbp.entity.Petugas;
import com.example.uts_pbp.entity.Produk;
import com.example.uts_pbp.room.database.DatabaseJadwal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FragmentPendaftaran extends Fragment {
    //DEKLARASI OBJEK DAN VARIABEL
    private Jadwal jdwl;
    private FragmentPendaftaranBinding binding;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private PreferencesSettings settings;
    private View parentView;

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

        settings = (PreferencesSettings) getActivity().getApplication();

        //INISIALISASI OBJEK DAN VARIABEL
        jdwl = new Jadwal();
        binding.setDftr(jdwl);
        binding.setActivity(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        parentView = view.findViewById(R.id.viewPendaftaran);

        //cek update tema
        loadSharedPreferences();

        //ku enggak paham gimana cara kerjanya tapi ini bekerja -- WKWKWKK gpp seadanya dulu
        //dropdown menu
        //Produk
        int i;
        Produk produk;
        ArrayList<Produk> listProduk = new DaftarProduk().listProduk;
        String [] PRODUK = new String[listProduk.size()];
        for (i=0;i<listProduk.size();i++) {
            produk = listProduk.get(i);
            PRODUK[i] = produk.getNama();
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter(getActivity(),
                android.R.layout.simple_dropdown_item_1line, PRODUK);
        binding.etProduk.setAdapter(adapter1);

        //Pencukur
        Petugas petugas;
        ArrayList<Petugas> listPetugas = new DaftarPetugas().listPetugas;
        String [] PETUGAS = new String[listPetugas.size()];
        for (i=0;i<listPetugas.size();i++) {
            petugas = listPetugas.get(i);
            PETUGAS[i] = petugas.getNama();
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter(getActivity(),
                android.R.layout.simple_dropdown_item_1line, PETUGAS);
        binding.etNamaPencukur.setAdapter(adapter2);
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
            int i;
            Petugas petugas;
            ArrayList<Petugas> listPetugas = new DaftarPetugas().listPetugas;
            for (i=0;i<listPetugas.size();i++) {
                petugas = listPetugas.get(i);
                if (jdwl.getPetugas().equals(petugas.getNama())) {
                    jdwl.setImgUrl(petugas.getImgUrl());
                    break;
                }
            }

            //todos
            addJadwal();

            //kembali ke FragmentActivity
            getActivity().finish();
        }
    };

    private void addJadwal(){
        final String nama = jdwl.getNama();
        final String telp = jdwl.getTelp();
        final String tanggal = jdwl.getTanggal();
        final String pelayanan = jdwl.getPelayanan();
        final String petugas = jdwl.getPetugas();
        final String imgUrl = jdwl.getImgUrl();

        class AddJadwal extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Jadwal jadwal = new Jadwal();
                jadwal.setNama(nama);
                jadwal.setTelp(telp);
                jadwal.setTanggal(tanggal);
                jadwal.setPelayanan(pelayanan);
                jadwal.setPetugas(petugas);
                jadwal.setImgUrl(imgUrl);

                DatabaseJadwal.getInstance(getActivity().getApplicationContext())
                        .getDatabase()
                        .jadwalDao()
                        .insertJadwal(jadwal);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(getActivity(), "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
//                edt_todo.setText("");
//                getTodos();
            }

        }
        AddJadwal addJadwal = new AddJadwal(  );
        addJadwal.execute();
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
            //Maaf aku buat hitam backgroundnya aja, cardviewnya engga :)
            parentView.setBackgroundColor(black);
        }
        else
        {
            parentView.setBackgroundColor(white);
        }
    }
}
