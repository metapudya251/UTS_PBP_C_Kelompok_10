package com.example.uts_pbp.retrofit.api;

import com.example.uts_pbp.models.AuthResponse;
import com.example.uts_pbp.models.Jadwal;
import com.example.uts_pbp.models.JadwalResponse;
import com.example.uts_pbp.models.Pengguna;
import com.example.uts_pbp.models.PenggunaResponse;
import com.example.uts_pbp.models.Petugas;
import com.example.uts_pbp.models.PetugasResponse;
import com.example.uts_pbp.models.Produk;
import com.example.uts_pbp.models.ProdukResponse;
import com.example.uts_pbp.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    //login
    @Headers({"Accept: application/json"})
    @POST("login")
    Call<AuthResponse> getLogin(@Body User user);

    //Register
    @Headers({"Accept: application/json"})
    @POST("register")
    Call<AuthResponse> getRegister(@Body Pengguna pengguna);

    //Jadwal
    @Headers({"Accept: application/json"})
    @GET("jadwal")
    Call<JadwalResponse> getAllJadwal();

    @Headers({"Accept: application/json"})
    @GET("jadwal/{id}")
    Call<JadwalResponse> getJadwalById(@Path("id") long id);

    @Headers({"Accept: application/json"})
    @POST("jadwal")
    Call<JadwalResponse> createJadwal(@Body Jadwal jadwal);

    @Headers({"Accept: application/json"})
    @PUT("jadwal/{id}")
    Call<JadwalResponse> updateJadwal(@Path("id") long id, @Body Jadwal jadwal);

    @Headers({"Accept: application/json"})
    @DELETE("jadwal/{id}")
    Call<JadwalResponse> deleteJadwal(@Path("id") long id);

    //Produk
    @Headers({"Accept: application/json"})
    @GET("produk")
    Call<ProdukResponse> getAllProduk();

    @Headers({"Accept: application/json"})
    @GET("produk/{id}")
    Call<ProdukResponse> getProdukById(@Path("id") long id);

    @Headers({"Accept: application/json"})
    @POST("produk")
    Call<ProdukResponse> createProduk(@Body Produk produk);

    @Headers({"Accept: application/json"})
    @PUT("produk/{id}")
    Call<ProdukResponse> updateProduk(@Path("id") long id, @Body Produk produk);

    @Headers({"Accept: application/json"})
    @DELETE("produk/{id}")
    Call<ProdukResponse> deleteProduk(@Path("id") long id);

    //Petugas
    @Headers({"Accept: application/json"})
    @GET("petugas")
    Call<PetugasResponse> getAllPetugas();

    @Headers({"Accept: application/json"})
    @GET("petugas/{id}")
    Call<PetugasResponse> getPetugasById(@Path("id") long id);

    @Headers({"Accept: application/json"})
    @POST("petugas")
    Call<PetugasResponse> createPetugas(@Body Petugas petugas);

    @Headers({"Accept: application/json"})
    @PUT("petugas/{id}")
    Call<PetugasResponse> updatePetugas(@Path("id") long id, @Body Petugas petugas);

    @Headers({"Accept: application/json"})
    @DELETE("petugas/{id}")
    Call<PetugasResponse> deletePetugas(@Path("id") long id);


    //User
    @Headers({"Accept: application/json"})
    @GET("user")
    Call<PenggunaResponse> getAllUser();

    @Headers({"Accept: application/json"})
    @GET("user/{id}")
    Call<PenggunaResponse> getUserById(@Path("id") long id);

    @Headers({"Accept: application/json"})
    @POST("user")
    Call<PenggunaResponse> createUser(@Body Pengguna user);

    @Headers({"Accept: application/json"})
    @PUT("user/{id}")
    Call<PenggunaResponse> updateUser(@Path("id") long id, @Body Pengguna user);

    @Headers({"Accept: application/json"})
    @DELETE("user/{id}")
    Call<PenggunaResponse> deleteUser(@Path("id") long id);
}
