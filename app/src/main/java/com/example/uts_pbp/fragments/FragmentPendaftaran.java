package com.example.uts_pbp.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp.FragmentActivity;
import com.example.uts_pbp.HomeActivity;
import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.FragmentPendaftaranBinding;
import com.example.uts_pbp.entity.Pendaftaraan;
import com.example.uts_pbp.entity.Produk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentPendaftaran extends Fragment {
    //DEKLARASI OBJEK DAN VARIABEL
    private Pendaftaraan dftr;
    private FragmentPendaftaranBinding binding;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

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

        //INISIALISASI OBJEK DAN VARIABEL
        dftr = new Pendaftaraan();
        binding.setDftr(dftr);

        //kuragu tapi kalau bawah ini dihapus, enggak jalan
        binding.setActivity(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    }

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
                    //dftr.setTanggal_masuk(dateFormatter.format(newDate.getTime()));
                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }
    };

    //MEMBUAT RESPONSE CLICK LISTENER YANG AKAN DIGUNAKAN PADA BUTTON KEMBALI
    public View.OnClickListener btnBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((FragmentActivity)getActivity()).finishAndRemoveTask();
        }
    };

    //MEMBUAT RESPONSE CLICK LISTENER YANG AKAN DIGUNAKAN PADA BUTTON SIMPAN
    public View.OnClickListener btnSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //todos

            //kembali ke FragmentActivity
            ((FragmentActivity)getActivity()).finishAndRemoveTask();
        }
    };
}
