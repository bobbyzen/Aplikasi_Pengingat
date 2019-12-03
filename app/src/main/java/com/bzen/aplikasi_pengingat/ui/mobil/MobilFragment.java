package com.bzen.aplikasi_pengingat.ui.mobil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bzen.aplikasi_pengingat.R;
import com.bzen.aplikasi_pengingat.TambahMobilActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MobilFragment extends Fragment {
    private DatabaseReference database;
    private RecyclerView rvView;
    private AdapterMobilRecyclerView adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Mobil> daftarMobil;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobil, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvView = view.findViewById(R.id.rv_mobil);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("mobil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarMobil = new ArrayList<>();
                for (DataSnapshot noteDataSnapShot : dataSnapshot.getChildren()){
                    Mobil mobil = noteDataSnapShot.getValue(Mobil.class);
                    mobil.setKey(noteDataSnapShot.getKey());

                    daftarMobil.add(mobil);
                }
                adapter = new AdapterMobilRecyclerView(daftarMobil, getContext());
                rvView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" " + databaseError.getMessage());
            }
        });

        FloatingActionButton fabTambahMobil = view.findViewById(R.id.fabAddMobil);
        fabTambahMobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Tambah Mobil", Toast.LENGTH_SHORT).show();
                Intent tmbh_mobil = new Intent(getContext(), TambahMobilActivity.class);
                startActivity(tmbh_mobil);
            }
        });
    }
}