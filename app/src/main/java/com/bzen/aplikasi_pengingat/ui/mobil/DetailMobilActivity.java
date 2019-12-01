package com.bzen.aplikasi_pengingat.ui.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.bzen.aplikasi_pengingat.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailMobilActivity extends AppCompatActivity {
    private EditText etPlat, etMerk, etThnPembuatan, etOli, etPajak;
    private Button btnOli, btnPajak, btnUpdate, btnSelesai;
    private Spinner spWarna, spStsMobil;
    private int tanda;
    private int mTahun, mBulan, mTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mobil);

        etPlat = findViewById(R.id.etPlat);
        etMerk = findViewById(R.id.etMerk);
        etThnPembuatan = findViewById(R.id.etTahunPembuatan);
        etOli = findViewById(R.id.etBatasOli);
        etPajak = findViewById(R.id.etBatasPajak);

        btnOli = findViewById(R.id.btnBatasOli);
        btnPajak = findViewById(R.id.btnBatasPajak);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnSelesai = findViewById(R.id.btnSelesai);

        btnSelesai.setVisibility(View.GONE);

        etPlat.setEnabled(false);
        etMerk.setEnabled(false);
        etThnPembuatan.setEnabled(false);
        btnOli.setEnabled(false);
        btnPajak.setEnabled(false);

        spWarna = findViewById(R.id.spWarna);
        spWarna.setEnabled(false);
        spStsMobil = findViewById(R.id.spStsMobil);
        spStsMobil.setEnabled(false);

        final Mobil mobil = (Mobil) getIntent().getSerializableExtra("data");
        if(mobil != null){
            etPlat.setText(mobil.getPlat());
            etMerk.setText(mobil.getMerk());
            etThnPembuatan.setText(mobil.getThnPembuatan());

           if(mobil.getWrn().equals("HIJAU")){
                spWarna.setSelection(0);
           } else if(mobil.getWrn().equals("KUNING")){
               spWarna.setSelection(1);
           } else if(mobil.getWrn().equals("PUTIH KOMBINASI")){
               spWarna.setSelection(2);
           } else if(mobil.getWrn().equals("MERAH")){
               spWarna.setSelection(3);
           }

           if(mobil.getStsMobil() == 0){
               spStsMobil.setSelection(0);
           }else{
               spStsMobil.setSelection(1);
           }

           etOli.setText(mobil.getBtsOli());
           etPajak.setText(mobil.getBtsPajak());

           btnUpdate.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   etPlat.setEnabled(true);
                   etMerk.setEnabled(true);
                   etThnPembuatan.setEnabled(true);
                   btnOli.setEnabled(true);
                   btnPajak.setEnabled(true);

                   spWarna.setEnabled(true);

                   btnUpdate.setEnabled(false);
                   btnSelesai.setVisibility(View.VISIBLE);
               }
           });

           btnOli.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   tanda = 0;
                   setTanggal();
               }
           });

           btnPajak.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   tanda = 1;
                   setTanggal();
               }
           });

           btnSelesai.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
           });
        }
        else{
            btnSelesai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, DetailMobilActivity.class);
    }

    private void setTanggal() {
        final Calendar c = Calendar.getInstance();
        mTahun = c.get(Calendar.YEAR);
        mBulan = c.get(Calendar.MONTH);
        mTanggal = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DetailMobilActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                mTahun = year;
                mBulan = month;
                mTanggal = day;

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, mTahun);
                calendar.set(Calendar.MONTH, mBulan);
                calendar.set(Calendar.DAY_OF_MONTH, mTanggal);
                Date date = calendar.getTime();
                SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
                String tanggal = dt.format(date);

                if(tanda == 0){
                    etOli.setText(tanggal);
                }
                else if(tanda == 1){
                    etPajak.setText(tanggal);
                }
            }
        }, mTahun, mBulan, mTanggal);
        datePickerDialog.show();
    }
}
