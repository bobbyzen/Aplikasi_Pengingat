package com.bzen.aplikasi_pengingat.ui.mobil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bzen.aplikasi_pengingat.R;

import java.util.ArrayList;

public class AdapterMobilRecyclerView extends RecyclerView.Adapter<AdapterMobilRecyclerView.ViewHolder> {

    private ArrayList<Mobil> daftarMobil;
    private Context context;

    public AdapterMobilRecyclerView(ArrayList<Mobil> mobils, Context ctx){
        daftarMobil = mobils;
        context = ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlat;
        CardView cvMobil;

        ViewHolder(View v) {
            super(v);
            tvPlat = v.findViewById(R.id.tvPlat);
            cvMobil = v.findViewById(R.id.cv_mobil);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mobil, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String plat = daftarMobil.get(position).getPlat();
        holder.cvMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(DetailMobilActivity.getActIntent((Activity) context).putExtra("data", daftarMobil.get(position)));
            }
        });
        holder.cvMobil.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Delete dan update data
                 */
                return true;
            }
        });
        holder.tvPlat.setText(plat);
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarMobil.size();
    }
}
