package com.bzen.aplikasi_pengingat.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bzen.aplikasi_pengingat.R;
import com.bzen.aplikasi_pengingat.TambahUserActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> daftarUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        rvView = view.findViewById(R.id.rv_user);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarUser = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    user.setKey(ds.getKey());

                    daftarUser.add(user);
                }
                adapter = new AdapterUserRecyclerView(daftarUser, getContext());
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" " + databaseError.getMessage());
            }
        });

        FloatingActionButton fabTambahUser = view.findViewById(R.id.fabAddUser);
        fabTambahUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmbh_user = new Intent(getActivity(), TambahUserActivity.class);
                startActivity(tmbh_user);
            }
        });

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

    }
}