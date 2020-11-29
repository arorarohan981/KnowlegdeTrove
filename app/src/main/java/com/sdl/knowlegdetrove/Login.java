package com.sdl.knowlegdetrove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class Login extends AppCompatActivity {
TextInputLayout loginphoneno,loginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        Button callsignup = findViewById(R.id.signupbtn);

        callsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
               Intent intent = new Intent(Login.this,Signup.class);
               startActivity(intent);

            }
        });

    }
    private Boolean validatePhoneNo () {
        loginphoneno = findViewById(R.id.Phonenoonlogin);
        String val = loginphoneno.getEditText().getText().toString();


        if (val.isEmpty()) {
            loginphoneno.setError("Field Cannot be Empty");
            return false;
        }else if(val.length()!=10){
            loginphoneno.setError("Phone Number Has to be 10 Digits");
            return false;
        }
        else {
            loginphoneno.setError(null);
            loginphoneno.setErrorEnabled(false);
            return true;

        }
    }
    private Boolean validatePassword () {
        loginPassword = findViewById(R.id.Passwordonlogin);
        String val = loginPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            loginPassword.setError("Field Cannot be Empty");
            return false;
        }  else {
            loginPassword.setError(null);
            loginPassword.setErrorEnabled(false);
            return true;

        }
    }
    public void loginUser(View view){
        if(!validatePhoneNo()| !validatePassword()){
            return;
        }else{
            isUservalid();
        }
    }

    private void isUservalid() {
        loginphoneno = findViewById(R.id.Phonenoonlogin);
        loginPassword = findViewById(R.id.Passwordonlogin);
        String phoneno=loginphoneno.getEditText().getText().toString();
        String password=loginPassword.getEditText().getText().toString();
        DatabaseReference referenece= FirebaseDatabase.getInstance().getReference("users");
        Query checkuser=referenece.orderByChild("phoneno").equalTo(phoneno);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot){
                    if(dataSnapshot.exists()){
                        loginphoneno.setError(null);
                        loginphoneno.setErrorEnabled(false);
                        String passwordfromdb=dataSnapshot.child(phoneno).child("password").getValue(String.class);
                        //From here fetch all values and then use further
                    if(passwordfromdb.equals(password)){
                        loginphoneno.setError(null);
                        loginphoneno.setErrorEnabled(false);
                        Intent intent = new Intent(Login.this,Categories.class);
                        intent.putExtra("phoneno", phoneno);
                        startActivity(intent);
                    }else{
                        loginPassword.setError("Wrong Password !");
                        loginPassword.requestFocus();
                    }
                    }else{
                        loginphoneno.setError("Wrong Phone Number!");
                    }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError){
                    
            }
        });


    }

}