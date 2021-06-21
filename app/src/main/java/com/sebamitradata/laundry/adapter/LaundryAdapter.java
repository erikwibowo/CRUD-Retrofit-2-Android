package com.sebamitradata.laundry.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sebamitradata.laundry.R;
import com.sebamitradata.laundry.activity.EditLaundry;
import com.sebamitradata.laundry.activity.MainActivity;
import com.sebamitradata.laundry.activity.TambahLaundry;
import com.sebamitradata.laundry.api.Server;
import com.sebamitradata.laundry.model.LaundryModel;
import com.sebamitradata.laundry.request.LaundryRequest;
import com.sebamitradata.laundry.response.LaundryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaundryAdapter extends RecyclerView.Adapter<LaundryAdapter.HolderData>{
    private Context context;
    private List<LaundryModel> models;

    public LaundryAdapter(Context context, List<LaundryModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_laundry, parent, false);
        HolderData holderData = new HolderData(view);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        LaundryModel model = models.get(position);
        holder.tvNama.setText(model.getNama());
        holder.tvAlamat.setText(model.getAlamat());
        holder.tvTelepon.setText(model.getTelepon());

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Pilih operasi yang akan dilakukan");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(model.getId());
                        dialog.dismiss();
                        ((MainActivity) context).show();
                    }
                });
                builder.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context, EditLaundry.class);
                        i.putExtra("id", model.getId());
                        i.putExtra("nama", model.getNama());
                        i.putExtra("alamat", model.getAlamat());
                        i.putExtra("telepon", model.getTelepon());
                        context.startActivity(i);
                    }
                });
                builder.show();

                return false;
            }
        });
    }

    private void delete(String id){
        LaundryRequest request = Server.connection().create(LaundryRequest.class);
        Call<LaundryResponse> response = request.laundry_delete(id);

        response.enqueue(new Callback<LaundryResponse>() {
            @Override
            public void onResponse(Call<LaundryResponse> call, Response<LaundryResponse> response) {
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LaundryResponse> call, Throwable t) {
                Toast.makeText(context, "Gagal menghubungi server : "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvNama, tvAlamat, tvTelepon;
        CardView card;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvTelepon = itemView.findViewById(R.id.tv_telepon);
            card = itemView.findViewById(R.id.card);

        }
    }
}
