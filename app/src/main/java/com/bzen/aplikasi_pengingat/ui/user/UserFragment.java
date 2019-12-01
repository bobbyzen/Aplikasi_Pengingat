package com.bzen.aplikasi_pengingat.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bzen.aplikasi_pengingat.R;
import com.bzen.aplikasi_pengingat.TambahUserActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

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