package com.sebamitradata.laundry.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.sebamitradata.laundry.R;
import com.sebamitradata.laundry.adapter.LaundryAdapter;
import com.sebamitradata.laundry.api.Server;
import com.sebamitradata.laundry.databinding.ActivityMainBinding;
import com.sebamitradata.laundry.model.LaundryModel;
import com.sebamitradata.laundry.request.LaundryRequest;
import com.sebamitradata.laundry.response.LaundryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView.Adapter adapter;
    private List<LaundryModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipe.setRefreshing(true);
                show();
                binding.swipe.setRefreshing(false);
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahLaundry.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        show();
    }

    public void show(){
        binding.swipe.setRefreshing(true);
        LaundryRequest request = Server.connection().create(LaundryRequest.class);
        Call<LaundryResponse> response = request.laundry_show();

        response.enqueue(new Callback<LaundryResponse>() {
            @Override
            public void onResponse(Call<LaundryResponse> call, Response<LaundryResponse> response) {
                binding.swipe.setRefreshing(false);
                Log.v("Response : ", "Response "+response.body().getData().toString());
                models = response.body().getData();
                adapter = new LaundryAdapter(MainActivity.this, models);
                binding.recycle.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LaundryResponse> call, Throwable t) {
                binding.swipe.setRefreshing(false);
                Log.e("Error", "Gagal menghubungi server : "+t.getMessage());
            }
        });
    }
}