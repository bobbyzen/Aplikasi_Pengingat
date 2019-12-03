package com.bzen.aplikasi_pengingat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bzen.aplikasi_pengingat.ui.mobil.Mobil;
import com.bzen.aplikasi_pengingat.ui.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TambahUserActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button btnTglLahir, btnFoto, btnSelesai;
    private int mTahun, mBulan, mTanggal, tandaEmail, rbUser, rbJK;
    private EditText etTglLahir, etMobil_Plat;
    private RadioGroup rgJK, rgJenisUser;
    private RadioButton radioButtonJK, radioButtonUs;
    private final int kodeGallery = 100;
    private FirebaseAuth mauth;
    private final CharSequence[] items = {"Buka Kamera", "Pilih dari Galeri", "Batal"};
    private ImageView imageView;
    private String selectedItem;
    private SearchableSpinner spSearch;
    private TextInputEditText tiNama, tiEmail, tiPass, tiKonfirm, tiAlamat;
    private TextView tvPlat;
    private int REQUEST_PERMISSION = 121;
    private String currentPhotoPath = "";
    private int REQUEST_IMAGE_CAPTURE = 1;
    private Uri photoURI;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_user);

        rgJK = findViewById(R.id.rgJK);
        rgJenisUser = findViewById(R.id.rgUser);

        tiNama = findViewById(R.id.tiNama);
        tiEmail = findViewById(R.id.tiEmail);
        tiPass = findViewById(R.id.tiPassword);
        tiKonfirm = findViewById(R.id.tiKonfirm);
        etTglLahir = findViewById(R.id.etTglLahir);
        tiAlamat = findViewById(R.id.tiAlamat);
        etMobil_Plat = findViewById(R.id.etMobil_Plat);
        rgJK = findViewById(R.id.rgJK);
        rgJenisUser = findViewById(R.id.rgUser);

        tvPlat = findViewById(R.id.textView8);

        btnTglLahir = findViewById(R.id.btnTglLahir);
        btnTglLahir.setOnClickListener(this);

        btnFoto = findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(this);
        spSearch = findViewById(R.id.spinner_search);

        database = FirebaseDatabase.getInstance();
        database.getReference("mobil").addValueEventListener(new ValueEventListener() {
                @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add("PILIH MOBIL");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Mobil mobil = postSnapshot.getValue(Mobil.class);
                    if (mobil.getStsMobil() == 0) {
                        temp.add(mobil.getPlat());
                    }
                }

                ArrayAdapter<String> myAdapter = new ArrayAdapter<>(TambahUserActivity.this, R.layout.support_simple_spinner_dropdown_item, temp);
                spSearch.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rgJenisUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbAdmin){
                    spSearch.setVisibility(View.GONE);
                    tvPlat.setVisibility(View.GONE);
                    rbUser = 1;
                }else{
                    rbUser = 0;
                    spSearch.setVisibility(View.VISIBLE);
                    tvPlat.setVisibility(View.VISIBLE);
                }
            }
        });

        imageView = findViewById(R.id.imgView);
        imageView.setVisibility(View.GONE);

        btnSelesai = findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(this);

        mauth = FirebaseAuth.getInstance();

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItem = parent.getItemAtPosition(position).toString();
        etMobil_Plat.setText(selectedItem);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnTglLahir:
                setTanggal();
                break;

            case R.id.btnFoto:
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahUserActivity.this);
                builder.setTitle("Tambah Foto");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Buka Kamera")) {
                            if (ContextCompat.checkSelfPermission(
                                    TambahUserActivity.this,
                                    Manifest.permission.CAMERA
                            )
                                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                                    TambahUserActivity.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                                    != PackageManager.PERMISSION_GRANTED
                            ) {
                                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                        TambahUserActivity.this,
                                        Manifest.permission.CAMERA
                                ) || !ActivityCompat.shouldShowRequestPermissionRationale(
                                        TambahUserActivity.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                                ) {
                                    ActivityCompat.requestPermissions(
                                            TambahUserActivity.this,
                                            new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            REQUEST_PERMISSION
                                    );
                                }
                            } else {
                                dispatchTakePictureIntent();
                            }

                        } else if (items[item].equals("Pilih dari Galeri")) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, kodeGallery);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.btnSelesai:
                final String email = tiEmail.getText().toString();
                int penandaUser = 0;
                if(rbUser == 1){
                    penandaUser = 1;
                }else if((rbUser == 0) && (spSearch.getSelectedItem().toString().equals("PILIH MOBIL"))){
                    penandaUser = 0;
                }else if((rbUser == 0) && (spSearch.getSelectedItem().toString() != null)){
                    penandaUser = 2;
                }

                if (!isEmpty(tiNama.getText().toString()) && !isEmpty(email) && !isEmpty(tiPass.getText().toString()) && !isEmpty(tiKonfirm.getText().toString()) && !isEmpty(etTglLahir.getText().toString()) && !isEmpty(tiAlamat.getText().toString()) && (imageView.getDrawable() != null) && penandaUser != 0) {

                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        mauth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.getResult().getSignInMethods().size() == 0) {

                                    if (tiPass.getText().toString().equals(tiKonfirm.getText().toString())) {

                                        String pass = tiPass.getText().toString();

                                        mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull final Task<AuthResult> task) {

                                                int jenisKelamin = rgJK.getCheckedRadioButtonId();
                                                int jenisUser = rgJenisUser.getCheckedRadioButtonId();
                                                radioButtonJK = findViewById(jenisKelamin);
                                                radioButtonUs = findViewById(jenisUser);
                                                if(radioButtonJK.getText().equals("Laki - laki")){
                                                    rbJK = 0;
                                                }else{
                                                    rbJK = 1;
                                                }

                                                final StorageReference storageRef = FirebaseStorage
                                                        .getInstance()
                                                        .getReference().child("users/"+System.currentTimeMillis());

                                                if(rbUser == 0){
                                                    storageRef.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    submitUser(new User(tiNama.getText().toString(), tiEmail.getText().toString(), tiPass.getText().toString(), uri.toString(), spSearch.getSelectedItem().toString(), rbUser, rbJK, 1, etTglLahir.getText().toString(), tiAlamat.getText().toString()));

                                                                    Snackbar.make(btnSelesai, "Upload data berhasil", Snackbar.LENGTH_LONG).show();
                                                                    spSearch.setVisibility(View.VISIBLE);
                                                                    tvPlat.setVisibility(View.VISIBLE);
                                                                }
                                                            });
                                                        }
                                                    });
                                                }else if(rbUser == 1){
                                                    storageRef.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    submitUser(new User(tiNama.getText().toString(), tiEmail.getText().toString(), tiPass.getText().toString(), uri.toString(), "-", rbUser, rbJK, 1, etTglLahir.getText().toString(), tiAlamat.getText().toString()));

                                                                    Snackbar.make(btnSelesai, "Upload data berhasil", Snackbar.LENGTH_LONG).show();
                                                                    spSearch.setVisibility(View.VISIBLE);
                                                                    tvPlat.setVisibility(View.VISIBLE);
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                                if (task.isSuccessful()) {
                                                }

                                                task.addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                });
                                            }
                                        });

                                    } else {
                                        Toast.makeText(TambahUserActivity.this, "Password dan Konfirmasi Password anda berbeda", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(TambahUserActivity.this, "Email sudah terdaftar !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this, "Email tidak valid !", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Data tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void submitUser(User user){
        database.getReference().child("mobil").orderByChild("plat").equalTo(user.getPlatmobil()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String fixmobilkey = dataSnapshot1.getKey();

                    database.getReference().child("mobil").child(fixmobilkey).child("stsMobil").setValue(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        database.getReference("user").push().setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//               finish();
            }
        });
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File x = File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
        );
        currentPhotoPath = x.getAbsolutePath();
        return x;
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private void setTanggal() {
        final Calendar c = Calendar.getInstance();
        mTahun = c.get(Calendar.YEAR);
        mBulan = c.get(Calendar.MONTH);
        mTanggal = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(TambahUserActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                etTglLahir.setText(tanggal);
            }
        }, mTahun, mBulan, mTanggal);
        datePickerDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION){
            if ((grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                dispatchTakePictureIntent();
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),
                        FileProvider.getUriForFile(
                                this,
                                "com.example.android.fileprovider",
                                new File(currentPhotoPath)
                        )
                );
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == kodeGallery && resultCode == RESULT_OK){
            photoURI = data.getData();
            imageView.setImageURI(photoURI);
            imageView.setVisibility(View.VISIBLE);
        }
    }


}
