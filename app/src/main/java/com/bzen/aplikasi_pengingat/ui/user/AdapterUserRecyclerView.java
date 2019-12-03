package com.bzen.aplikasi_pengingat.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bzen.aplikasi_pengingat.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterUserRecyclerView extends RecyclerView.Adapter<AdapterUserRecyclerView.ViewHolder> {
    private ArrayList<User> daftarUser;
    private Context context;

    public AdapterUserRecyclerView(ArrayList<User> users, Context ctx){
        this.daftarUser = users;
        this.context = ctx;
    }

    @NonNull
    @Override
    public AdapterUserRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserRecyclerView.ViewHolder holder, final int position) {
        User user = daftarUser.get(position);
        Glide.with(holder.itemView.getContext()).load(user.getFoto()).apply(new RequestOptions().override(55, 55)).into(holder.imgPhoto);

        holder.tvNamaUser.setText(user.getNama());
        holder.tvUserPlat.setText(user.getPlatmobil());

        String jenis = " ";

        switch (user.getJenisUser()){
            case 0:
                jenis = "PENGGUNA";
                break;
            case 1:
                jenis = "ADMIN";
                break;
        }

        holder.tvJenisUser.setText(jenis);

        holder.cv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(DetailUserActivity.getActIntent((Activity) context).putExtra("data", daftarUser.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaUser, tvJenisUser, tvUserPlat;
        CardView cv_user;
        ImageView imgPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaUser = itemView.findViewById(R.id.tvNamaUser);
            tvJenisUser = itemView.findViewById(R.id.tvJenisUser);
            tvUserPlat = itemView.findViewById(R.id.tvUserPlat);
            cv_user = itemView.findViewById(R.id.cv_user);
            imgPhoto = itemView.findViewById(R.id.cir_img_user);
            cv_user = itemView.findViewById(R.id.cv_user);
        }
    }
}
