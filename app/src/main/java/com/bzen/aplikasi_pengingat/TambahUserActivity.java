package com.bzen.aplikasi_pengingat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bzen.aplikasi_pengingat.ui.mobil.AdapterMobilRecyclerView;
import com.bzen.aplikasi_pengingat.ui.mobil.Mobil;
import com.bzen.aplikasi_pengingat.ui.mobil.MobilFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TambahUserActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button btnTglLahir, btnFoto, btnSelesai;
    private int mTahun, mBulan, mTanggal, tandaEmail;
    private EditText etTglLahir, etNama, etEmail, etPassword, etKonfirmasi, etAlamat, etMobil_Plat;
    private RadioGroup rgJK, rgJenisUser;
    private final int kodeKamera = 99, kodeGallery = 100, kodeAmbilFoto = 98    ;
    private FirebaseAuth mauth;
    private FirebaseUser muser;
    private final CharSequence[] items = {"Buka Kamera","Pilih dari Galeri","Batal"};
    private ImageView imageView;
    private String selectedItem;
    private SearchableSpinner spSearch;
    public static String imagePath;
    private String TAG;

    private PlatAdapter platAdapter;

    private DatabaseReference database;
    private ArrayList<Mobil> daftarMobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_user);

        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPass);
        etKonfirmasi = findViewById(R.id.etKonfirm);
        etTglLahir = findViewById(R.id.etTglLahir);
        etAlamat = findViewById(R.id.etAlamat);
        etMobil_Plat = findViewById(R.id.etMobil_Plat);
        rgJK = findViewById(R.id.rgJK);
        rgJenisUser = findViewById(R.id.rgUser);

        btnTglLahir = findViewById(R.id.btnTglLahir);
        btnTglLahir.setOnClickListener(this);

        btnFoto = findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(this);
        spSearch = findViewById(R.id.spinner_search);

        database = FirebaseDatabase.getInstance().getReference();

        final ArrayList<Mobil> listmobil = new ArrayList<>();
        platAdapter = new PlatAdapter(getApplicationContext(), R.layout.item_plat, R.id.tvPlatMobil,listmobil);

        database.child("mobil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listmobil.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Mobil mobil = postSnapshot.getValue(Mobil.class);
                    listmobil.add(mobil);





                    //Toast.makeText(TambahUserActivity.this, mobil.getPlat(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        database.child("mobil").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                daftarMobil = new ArrayList<>();
//                for (DataSnapshot noteDataSnapShot : dataSnapshot.getChildren()){
//                    Mobil mobil = noteDataSnapShot.getValue(Mobil.class);
//                    mobil.setKey(noteDataSnapShot.getKey());
//
//                    daftarMobil.add(mobil);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println(databaseError.getDetails()+" " + databaseError.getMessage());
//            }
//        });
//        platAdapter = new PlatAdapter(getApplicationContext(), R.layout.item_plat, listmobil);
//
//        spSearch = findViewById(R.id.spinner_search);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.warna, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        spSearch.setAdapter(platAdapter);

        Mobil mobil = new Mobil();
        mobil.setKey("A");
        mobil.setBtsOli("A");
        mobil.setBtsPajak("A");
        mobil.setMerk("A");
        mobil.setPlat("AAA");
        listmobil.add(mobil);
        platAdapter.setListMobil(listmobil);
        platAdapter.setNotifyOnChange(true);
        spSearch.setAdapter(platAdapter);
        spSearch.setOnItemSelectedListener(this);

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
                Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.bzen.aplikasi_pengingat.fileprovider",
                        photoFile);
                Toast.makeText(this, "photo tidak null", Toast.LENGTH_SHORT).show();
                takePictureIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, photoURI);
                startActivityForResult(takePictureIntent, kodeAmbilFoto);
//                Uri uri = Uri.fromFile(photoFile);
//                Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnTglLahir:
                setTanggal();
                break;

            case R.id.btnFoto:
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahUserActivity.this);
                builder.setTitle("Tambah Foto");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(items[item].equals("Buka Kamera")){
                            Toast.makeText(TambahUserActivity.this, "Kamera Tampil", Toast.LENGTH_SHORT).show();
                            dispatchTakePictureIntent();

                        }
                        else if(items[item].equals("Pilih dari Galeri")){
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, kodeGallery);
                            imageView.setVisibility(View.VISIBLE);
                        }else{
                            dialog.dismiss();
                        }
                    }
                });
                AlertDialog alert =  builder.create();
                alert.show();
                break;

            case R.id.btnSelesai:
                final String email = etEmail.getText().toString();
                if(!isEmpty(etNama.getText().toString()) && !isEmpty(email) && !isEmpty(etPassword.getText().toString()) && !isEmpty(etKonfirmasi.getText().toString()) && !isEmpty(etTglLahir.getText().toString()) && !isEmpty(etAlamat.getText().toString()) && (imageView.getDrawable() != null) && (etMobil_Plat.getText().equals(""))){

                    mauth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if(task.getResult().getSignInMethods().size() == 0){
                                
                                if(etPassword.getText().toString().equals(etKonfirmasi.getText().toString())){

                                    String pass = etPassword.getText().toString();

                                    mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                              //  onAuthSuccess
                                            }
                                            Toast.makeText(TambahUserActivity.this, mauth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }else{
                                    Toast.makeText(TambahUserActivity.this, "Password dan Konfirmasi Password anda berbeda", Toast.LENGTH_SHORT).show();
                                }
                                
                            }else{
                                Toast.makeText(TambahUserActivity.this, "Email sudah terdaftar !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this, "Data tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void onAuthSuccess(FirebaseUser user){
        String email = user.getEmail();

     //   tambahUser(user.getUid(), )
    }

   // private tambahUser()

    private boolean isEmpty(String s){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == kodeAmbilFoto) {
            System.out.println("data kamera : " + data);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            Glide.with(getApplicationContext()).load(imageBitmap).into(imageView);
            Toast.makeText(this, extras.get("data").toString(), Toast.LENGTH_SHORT).show();
            //setPic();

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File mFileTemp = null;
            String root= getDir("my_sub_dir", Context.MODE_PRIVATE).getAbsolutePath();
            File myDir = new File(root + "/Img");
            if(!myDir.exists()){
                myDir.mkdirs();
            }
            try {
                mFileTemp=File.createTempFile(imageFileName,".jpg",myDir.getAbsoluteFile());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Uri uri = Uri.fromFile(mFileTemp);
            if(uri != null){
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();

        }else if(requestCode == kodeGallery && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
        }
        else{
            Toast.makeText(this, "TIdak masuk kemana mana", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
