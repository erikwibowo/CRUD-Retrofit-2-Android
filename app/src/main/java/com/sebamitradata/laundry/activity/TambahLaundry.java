package com.sebamitradata.laundry.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sebamitradata.laundry.api.Server;
import com.sebamitradata.laundry.databinding.ActivityTambahLaundryBinding;
import com.sebamitradata.laundry.request.LaundryRequest;
import com.sebamitradata.laundry.response.LaundryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahLaundry extends AppCompatActivity {

    private ActivityTambahLaundryBinding binding;
    String nama, alamat, telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahLaundryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = binding.etNama.getText().toString();
                alamat = binding.etAlamat.getText().toString();
                telepon = binding.etTelepon.getText().toString();
                if (nama.trim().equals("")){
                    binding.tilNama.setError("Nama harus diisi");
                }else if(alamat.trim().equals("")){
                    binding.tilAlamat.setError("Alamat harus diisi");
                }else if(telepon.trim().equals("")){
                    binding.tilTelepon.setError("Telepon harus diisi");
                }else{
                    create();
                }
            }
        });
    }

    private void create(){
        LaundryRequest request = Server.connection().create(LaundryRequest.class);
        Call<LaundryResponse> response = request.laundry_create(nama, alamat, telepon);

        response.enqueue(new Callback<LaundryResponse>() {
            @Override
            public void onResponse(Call<LaundryResponse> call, Response<LaundryResponse> response) {
                Toast.makeText(TambahLaundry.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<LaundryResponse> call, Throwable t) {
                Toast.makeText(TambahLaundry.this, "Gagal menghubungi server : "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}