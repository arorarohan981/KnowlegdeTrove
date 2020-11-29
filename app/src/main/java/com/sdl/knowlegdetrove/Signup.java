package com.sdl.knowlegdetrove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    TextInputLayout regName,regUsername,regEmail,regPhoneno,regPassword;
    Button regBtn;
    FirebaseDatabase rootnode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        regBtn = findViewById(R.id.createacc);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        Button backtosignup = findViewById(R.id.backtosignup);
        backtosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });





    }
    private Boolean validateName () {
        regName = findViewById(R.id.Fullname);
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field Cannot be Empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;

        }
    }
    private Boolean validateUsername () {
        regUsername = findViewById(R.id.Username);
        String val = regUsername.getEditText().getText().toString();
        String nowhitespace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Field Cannot be Empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too Long");
            return true;

        } else if (!val.matches(nowhitespace)) {
            regUsername.setError("White Spaces are Not Allowed");
            return false;

        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;

        }
    }
    private Boolean validateemail () {
        regEmail = findViewById(R.id.email);
        String val = regEmail.getEditText().getText().toString();
        String emailpattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

        if (val.isEmpty()) {
            regEmail.setError("Field Cannot be Empty");
            return false;
        } else if (!val.matches(emailpattern)) {
            regEmail.setError("Invalid Email Address ");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;

        }
    }
    private Boolean validatePhoneNo () {
        regPhoneno = findViewById(R.id.Phoneno);
        String val = regPhoneno.getEditText().getText().toString();


        if (val.isEmpty()) {
            regPhoneno.setError("Field Cannot be Empty");
            return false;
        }else if(val.length()!=10){
            regPhoneno.setError("Phone Number Has to be 10 Digits");
            return false;
        }
        else {
            regPhoneno.setError(null);
            regEmail.setErrorEnabled(false);
            return true;

        }
    }
    private Boolean validatePassword () {
        regPassword = findViewById(R.id.PasswordonSignup);
        String val = regPassword.getEditText().getText().toString();
        String passwordverification = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        if (val.isEmpty()) {
            regPassword.setError("Field Cannot be Empty");
            return false;
        } else if (!val.matches(passwordverification)) {
            regPassword.setError("Password Too Weak");
            return false;
        } else {
            regPassword.setError(null);

            return true;

        }
    }
    public void registerUser (View view){
        if (!validateName() | !validateUsername() | !validateemail() | !validatePhoneNo() | !validatePassword()) {
            return;
        }

        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneno = regPhoneno.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        rootnode=FirebaseDatabase.getInstance();
        reference=rootnode.getReference("users");
        UserhelperClass userhelperClass = new UserhelperClass(name, email, username, password, phoneno);
        reference.child(phoneno).setValue(userhelperClass);
        Toast.makeText(this, "Your Registration has been Successfull !!Please Login and Then Continue ! Thank you !", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Signup.this,Login.class);
        startActivity(intent);
    }



}