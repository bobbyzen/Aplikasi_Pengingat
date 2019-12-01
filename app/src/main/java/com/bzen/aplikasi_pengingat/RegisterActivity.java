package com.bzen.aplikasi_pengingat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mauth;
    private FirebaseUser muser;
    private EditText edtEmail, edtPass;
    private Button btnRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);

        mauth = FirebaseAuth.getInstance();

        //showProgressDialog();
        final String email = edtEmail.getText().toString();
        final String password = edtPass.getText().toString();
        btnRegis = findViewById(R.id.btnRegister);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mauth.signInWithEmailAndPassword("bobby@gmail.com","bobby123").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        Toast.makeText(RegisterActivity.this, mauth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
////                    }
////                });
                mauth.createUserWithEmailAndPassword("aaaa@gmail.com","alex123").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, mauth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }
}
