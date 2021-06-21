package com.sebamitradata.laundry.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sebamitradata.laundry.api.Server;
import com.sebamitradata.laundry.databinding.ActivityEditLaundryBinding;
import com.sebamitradata.laundry.databinding.ActivityTambahLaundryBinding;
import com.sebamitradata.laundry.request.LaundryRequest;
import com.sebamitradata.laundry.response.LaundryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLaundry extends AppCompatActivity {
    private ActivityEditLaundryBinding binding;
    String id, nama, alamat, telepon;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditLaundryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        binding.etNama.setText(bundle.getString("nama"));
        binding.etAlamat.setText(bundle.getString("alamat"));
        binding.etTelepon.setText(bundle.getString("telepon"));
        id = bundle.getString("id");
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
                    update();
                }
            }
        });
    }

    private void update(){
        LaundryRequest request = Server.connection().create(LaundryRequest.class);
        Call<LaundryResponse> response = request.laundry_update(id, nama, alamat, telepon);

        response.enqueue(new Callback<LaundryResponse>() {
            @Override
            public void onResponse(Call<LaundryResponse> call, Response<LaundryResponse> response) {
                Toast.makeText(EditLaundry.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<LaundryResponse> call, Throwable t) {
                Toast.makeText(EditLaundry.this, "Gagal menghubungi server : "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}