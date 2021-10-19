package com.example.uts_pbp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pbp.Dummy.DaftarProduk;
import com.example.uts_pbp.entity.Produk;
import com.example.uts_pbp.R;
import com.example.uts_pbp.recyclerViews.RVProdukAdapter;
import com.example.uts_pbp.databinding.FragmentProdukBinding;

import java.util.ArrayList;

public class FragmentProduk extends Fragment {
    private ArrayList<Produk> listProduk;
    private FragmentProdukBinding binding;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvProduk.setLayoutManager(layoutManager);

        binding.rvProduk.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        RVProdukAdapter myRecyclerViewAdapter = new RVProdukAdapter(listProduk,getActivity());
        binding.setProdukadapter(myRecyclerViewAdapter);
    }
}
