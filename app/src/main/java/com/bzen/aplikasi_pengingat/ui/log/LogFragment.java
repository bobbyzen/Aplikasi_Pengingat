package com.bzen.aplikasi_pengingat.ui.log;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bzen.aplikasi_pengingat.R;

public class LogFragment extends Fragment{

    private LogViewModel logViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_log, container, false);
        return root;
    }
}
