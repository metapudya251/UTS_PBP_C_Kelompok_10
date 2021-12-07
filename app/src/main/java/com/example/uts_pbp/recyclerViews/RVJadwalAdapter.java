package com.example.uts_pbp.recyclerViews;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pbp.R;
import com.example.uts_pbp.databinding.RvItemJadwalBinding;
import com.example.uts_pbp.models.Jadwal;
import com.example.uts_pbp.models.JadwalResponse;
import com.example.uts_pbp.models.Produk;
import com.example.uts_pbp.retrofit.api.ApiClient;
import com.example.uts_pbp.retrofit.api.ApiInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RVJadwalAdapter extends RecyclerView.Adapter<RVJadwalAdapter.ViewHolder>{
    private Context context;
    private List<Jadwal> listJadwal;

    private ApiInterface apiService;

    private Dialog dialog;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RvItemJadwalBinding binding;

        public ViewHolder(@NonNull RvItemJadwalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this::onClick);
        }
        public void bind(Jadwal item) {
            binding.setJdwl(item);
            binding.executePendingBindings();
        }

        public void onClick (View view){
            onNoteClick(getAdapterPosition());
        }
    }

    //pemanggil tampilan detail jadwal
    public void onNoteClick (int position){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_jadwal);

        TextView text1 = dialog.findViewById(R.id.tv_detail_tanggal);
        text1.setText(listJadwal.get(position).getTanggal());
        TextView text2 = dialog.findViewById(R.id.tv_detail_pelayanan);
        text2.setText(listJadwal.get(position).getPelayanan());
        TextView text3 = dialog.findViewById(R.id.tv_detail_petugas);
        text3.setText(listJadwal.get(position).getPetugas());
        ImageView image = dialog.findViewById(R.id.image_detail_petugas);
        Jadwal.loadImage(image, listJadwal.get(position).getImgUrl());

        TextView text4 = dialog.findViewById(R.id.button_detail_tutup);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        apiService = ApiClient.getClient().create(ApiInterface.class);

        TextView text5 = dialog.findViewById(R.id.button_detail_delete);
        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteJadwal(listJadwal.get(position).getId());
                listJadwal.remove(position);
                notifyItemRemoved(position);
            }
        });

        dialog.show();
    }

    public RVJadwalAdapter(List<Jadwal> listJadwal, Context context) {
        this.listJadwal = listJadwal;
        this.context = context;
        notifyDataSetChanged();
    }

    public void setJadwalList(ArrayList<Jadwal> listJadwal) {
        this.listJadwal = listJadwal;
    }

    @NonNull
    @Override
    public RVJadwalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemJadwalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_item_jadwal, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Jadwal item = listJadwal.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }


    public void deleteJadwal(long id) {
        Call<JadwalResponse> call = apiService.deleteJadwal(id);

        call.enqueue(new Callback<JadwalResponse>() {
            @Override
            public void onResponse(Call<JadwalResponse> call,
                                   Response<JadwalResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context,
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context,
                                jObjError.getString("message"),Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JadwalResponse> call, Throwable t) {
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
