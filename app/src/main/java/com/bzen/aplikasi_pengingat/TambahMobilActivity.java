package com.bzen.aplikasi_pengingat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.bzen.aplikasi_pengingat.ui.mobil.Mobil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TambahMobilActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnOli, btnPajak, btnSelesai;
    private int tanda;
    private int mTahun, mBulan, mTanggal;
    private EditText etOli, etPajak, etPlat, etMerk, etThnPembuatan;
    private DatabaseReference database;
    private Spinner spWarna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mobil);

        etOli = findViewById(R.id.etBatasOli);
        etPajak = findViewById(R.id.etBatasPajak);
        etPlat = findViewById(R.id.etPlat);
        etMerk = findViewById(R.id.etMerk);
        etThnPembuatan = findViewById(R.id.etTahunPembuatan);
        spWarna = findViewById(R.id.spWarna);

        btnOli = findViewById(R.id.btnBatasOli);
        btnOli.setOnClickListener(this);

        btnPajak = findViewById(R.id.btnBatasPajak);
        btnPajak.setOnClickListener(this);

        btnSelesai = findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(this);

        database = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBatasOli:
                tanda = 0;
                setTanggal();
                break;
            case R.id.btnBatasPajak:
                tanda = 1;
                setTanggal();
                break;
            case R.id.btnSelesai:
                if (!isEmpty(etPlat.getText().toString()) && !isEmpty(etMerk.getText().toString()) && !isEmpty(etThnPembuatan.getText().toString()) && !isEmpty(etOli.getText().toString()) && !isEmpty(etPajak.getText().toString()))
                    submitMobil(new Mobil(etPlat.getText().toString(), etMerk.getText().toString(), etThnPembuatan.getText().toString(), spWarna.getSelectedItem().toString(), 0, etOli.getText().toString(), etPajak.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.btnSelesai), "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etPlat.getWindowToken(), 0);
                break;
        }
    }

    private void submitMobil(Mobil mobil){
        database.child("mobil").push().setValue(mobil).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etPlat.setText("");
                etMerk.setText("");
                etThnPembuatan.setText("");
                spWarna.setSelection(0);
                etOli.setText("");
                etPajak.setText("");
                Snackbar.make(findViewById(R.id.btnSelesai), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    private void setTanggal() {
        final Calendar c = Calendar.getInstance();
        mTahun = c.get(Calendar.YEAR);
        mBulan = c.get(Calendar.MONTH);
        mTanggal = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(TambahMobilActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    public static Intent getActIntent(Activity activity){
        return new Intent(activity, TambahMobilActivity.class);
    }
}
