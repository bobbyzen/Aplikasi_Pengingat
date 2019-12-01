package com.bzen.aplikasi_pengingat;

import android.content.Context;
import android.mtp.MtpObjectInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bzen.aplikasi_pengingat.ui.mobil.Mobil;

import java.util.ArrayList;
import java.util.List;

public class PlatAdapter extends ArrayAdapter<Mobil> {
    private Context mContext;
    private int mResource;
    private ArrayList<Mobil> mobilList = new ArrayList<Mobil>();

    public PlatAdapter(@NonNull Context context, int resource, int textId, ArrayList<Mobil> list) {
        super(context, resource,textId);
        mContext = context;
        mResource = resource;
        mobilList = list;
        Mobil mobil = new Mobil();
        mobil.setKey("A");
        mobil.setBtsOli("A");
        mobil.setBtsPajak("A");
        mobil.setMerk("A");
        mobil.setPlat("AAA");
        mobilList.add(mobil);

    }

    public void setListMobil(List<Mobil> list){
        mobilList.addAll(list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        System.out.println("AAA");
        final View view = LayoutInflater.from(mContext).inflate(mResource, null, true);
        convertView = LayoutInflater.from(mContext).inflate(mResource, null, true);
        TextView offTypeTv = (TextView) view.findViewById(R.id.tvPlatMobil);

        Mobil offerData = mobilList.get(position);

        offTypeTv.setText(offerData.getPlat());

        return view;
    }

    @Override
    public int getCount() {
        return mobilList.size();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        Toast.makeText(mContext, mobilList.get(mobilList.size()-1).getPlat(), Toast.LENGTH_SHORT).show();
        System.out.println("AAA");
        final View view = LayoutInflater.from(mContext).inflate(mResource, parent, false);

        TextView offTypeTv = (TextView) view.findViewById(R.id.tvPlatMobil);

        Mobil offerData = mobilList.get(position);

        offTypeTv.setText(offerData.getPlat());

        return view;
    }
}
