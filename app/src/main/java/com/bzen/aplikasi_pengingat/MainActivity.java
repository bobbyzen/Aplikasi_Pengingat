package com.bzen.aplikasi_pengingat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bzen.aplikasi_pengingat.ui.log.LogFragment;
import com.bzen.aplikasi_pengingat.ui.mobil.MobilFragment;
import com.bzen.aplikasi_pengingat.ui.pengingat.PengingatFragment;
import com.bzen.aplikasi_pengingat.ui.user.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch( menuItem.getItemId()){
                    case(R.id.navigation_user):
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new UserFragment()).commit();
                        break;
                    case(R.id.navigation_pengingat):
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new PengingatFragment()).commit();
                        break;
                    case(R.id.navigation_car):
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MobilFragment()).commit();
                        break;
                    case(R.id.navigation_log):
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new LogFragment()).commit();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_about){
            Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.item_setting){
            Toast.makeText(getApplicationContext(), "Setting", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.item_sortby){
            showDialog();
            Toast.makeText(getApplicationContext(), "Sortby", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.item_search){
            Toast.makeText(getApplicationContext(), "Cari", Toast.LENGTH_SHORT).show();
        }
        return  true;
    }

    private void showDialog(){
        final String[] listKategori = getResources().getStringArray(R.array.kategori);

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);

        alt_bld.setTitle("Pilih Kategori");
        alt_bld.setSingleChoiceItems(listKategori, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Anda memilih " +listKategori[which], Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        alt_bld.setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alt_bld.create();
        alert.show();
    }
}
