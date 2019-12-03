package com.bzen.aplikasi_pengingat.ui.mobil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bzen.aplikasi_pengingat.R;
import com.bzen.aplikasi_pengingat.ui.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterMobilRecyclerView extends RecyclerView.Adapter<AdapterMobilRecyclerView.ViewHolder> {
    private FirebaseDatabase database;
    private ArrayList<Mobil> daftarMobil;
    private Context context;
    ArrayList<User> daftarUser;

    public AdapterMobilRecyclerView(ArrayList<Mobil> mobils, Context ctx) {
        daftarMobil = mobils;
        context = ctx;
        this.database = database;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaUser;
        TextView tvPlat;
        CardView cvMobil;

        ViewHolder(View v) {
            super(v);
            tvPlat = v.findViewById(R.id.tvPlat);
            tvNamaUser = v.findViewById(R.id.tvNamaSupir);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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
