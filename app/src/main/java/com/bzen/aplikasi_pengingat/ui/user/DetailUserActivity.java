package com.bzen.aplikasi_pengingat.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bzen.aplikasi_pengingat.R;
import com.bzen.aplikasi_pengingat.TambahUserActivity;
import com.bzen.aplikasi_pengingat.ui.mobil.Mobil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailUserActivity extends AppCompatActivity implements View.OnClickListener {
    CircleImageView fotoUser;
    TextInputEditText tiNama, tiEmail, tiPass, tiAlamat;
    EditText etTgl;
    RadioGroup rgJK, rgUser;
    RadioButton rbEditL, rbEditP, rbEditAdmin, rbEditPengguna;
    Button btnTgl, btnUpdate;
    Spinner spStatus;
    SearchableSpinner searchableSpinnerMobil;
    TextView tvPlatMobilFix;
    private FirebaseDatabase database;
    private int mTahun, mBulan, mTanggal;

    public static Intent getActIntent(Activity context) {
        return new Intent(context, DetailUserActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        fotoUser = findViewById(R.id.foto_user);
        tiNama = findViewById(R.id.tiEditUpdateNama);
        tiEmail = findViewById(R.id.tiEditUpdateEmail);
        tiPass = findViewById(R.id.tiEditUpdatePassword);
        tiAlamat = findViewById(R.id.tiEditAlamat);
        etTgl = findViewById(R.id.etEditTglLahir);
        rgJK = findViewById(R.id.rgEditJK);
        rgUser = findViewById(R.id.rgEditUser);
        rbEditL = findViewById(R.id.rbEditL);
        rbEditP = findViewById(R.id.rbEditP);
        rbEditAdmin = findViewById(R.id.rbEditAdmin);
        rbEditPengguna = findViewById(R.id.rbEditPengguna);
        btnTgl = findViewById(R.id.btnEditTglLahir);
        btnUpdate = findViewById(R.id.btnUpdate);
        tvPlatMobilFix = findViewById(R.id.tvPlatFix);

        spStatus = findViewById(R.id.spEditStatusUser);
        searchableSpinnerMobil = findViewById(R.id.spinner_searchEdit);
        searchableSpinnerMobil.setVisibility(View.GONE);

        fotoUser.setEnabled(false);
        tiNama.setEnabled(false);
        tiEmail.setEnabled(false);
        tiPass.setEnabled(false);
        tiAlamat.setEnabled(false);
        rbEditP.setEnabled(false);
        rbEditL.setEnabled(false);
        rbEditAdmin.setEnabled(false);
        rbEditPengguna.setEnabled(false);
        btnTgl.setEnabled(false);
        spStatus.setEnabled(false);

        btnTgl.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();

        final User user = (User) getIntent().getSerializableExtra("data");

        database.getReference("mobil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(user.getPlatmobil());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Mobil mobil = postSnapshot.getValue(Mobil.class);
                    if (mobil.getStsMobil() == 0) {
                        temp.add(mobil.getPlat());
                    }
                }

                ArrayAdapter<String> myAdapter = new ArrayAdapter<>(DetailUserActivity.this, R.layout.support_simple_spinner_dropdown_item, temp);

                searchableSpinnerMobil.setAdapter(myAdapter);
                searchableSpinnerMobil.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(user != null){
            tiNama.setText(user.getNama());
            tiEmail.setText(user.getEmail());
            tiPass.setText(user.getPassword());
            tiAlamat.setText(user.getAlamat());
            etTgl.setText(user.getTglLahir());
            tvPlatMobilFix.setText(user.getPlatmobil());


            searchableSpinnerMobil.setTitle(user.getPlatmobil());

            if(user.getJenisKelamin() == 0){
                rbEditL.setChecked(true);
            }else{
                rbEditP.setChecked(true);
            }

            if(user.getJenisUser() == 0){
                rbEditPengguna.setChecked(true);
            }else {
                rbEditAdmin.setChecked(true);
            }

            if(user.getStsUser() == 0){
                spStatus.setSelection(0);
            }else{
                spStatus.setSelection(1);
            }
        }

    }

    public void gantiFoto(View view) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUpdate:
                if(btnUpdate.getText().equals("UPDATE")){
                    tvPlatMobilFix.setVisibility(View.GONE);
                    fotoUser.setEnabled(true);
                    tiNama.setEnabled(true);
                    tiEmail.setEnabled(true);
                    tiPass.setEnabled(true);
                    tiAlamat.setEnabled(true);
                    rbEditP.setEnabled(true);
                    rbEditL.setEnabled(true);
                    rbEditAdmin.setEnabled(true);
                    rbEditPengguna.setEnabled(true);
                    btnTgl.setEnabled(true);
                    spStatus.setEnabled(true);
                    searchableSpinnerMobil.setEnabled(true);
                    searchableSpinnerMobil.setVisibility(View.VISIBLE);
                    btnUpdate.setText("SELESAI");

                } else {
                    fotoUser.setEnabled(false);
                    tiNama.setEnabled(false);
                    tiEmail.setEnabled(false);
                    tiPass.setEnabled(false);
                    tiAlamat.setEnabled(false);
                    rbEditP.setEnabled(false);
                    rbEditL.setEnabled(false);
                    rbEditAdmin.setEnabled(false);
                    rbEditPengguna.setEnabled(false);
                    btnTgl.setEnabled(false);
                    spStatus.setEnabled(false);
                    searchableSpinnerMobil.setEnabled(false);
                    searchableSpinnerMobil.setVisibility(View.GONE);
                    tvPlatMobilFix.setText(searchableSpinnerMobil.getSelectedItem().toString());
                    tvPlatMobilFix.setVisibility(View.VISIBLE);
                    btnUpdate.setText("UPDATE");

                }
                break;

            case R.id.btnEditTglLahir:
                setTanggal();
                break;
        }
    }

    private void setTanggal() {
        final Calendar c = Calendar.getInstance();
        mTahun = c.get(Calendar.YEAR);
        mBulan = c.get(Calendar.MONTH);
        mTanggal = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DetailUserActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                etTgl.setText(tanggal);
            }
        }, mTahun, mBulan, mTanggal);
        datePickerDialog.show();
    }
}
