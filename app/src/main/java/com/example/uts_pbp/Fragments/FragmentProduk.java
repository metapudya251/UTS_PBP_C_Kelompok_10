package com.example.uts_pbp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pbp.R;
import com.example.uts_pbp.RecyclerViews.RVProdukAdapter;

public class FragmentProduk extends Fragment {

    public FragmentProduk() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvProduk = view.findViewById(R.id.rv_produk);

        rvProduk.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // Set Layout Manager dari recycler view
        rvProduk.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));

        // Set Adapter dari recycler view.
        rvProduk.setAdapter(new RVProdukAdapter());
    }
}
