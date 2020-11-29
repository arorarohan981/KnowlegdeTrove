package com.sdl.knowlegdetrove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ebooks extends AppCompatActivity {
Button gotouploadpdfpagebtn,gotolistofallebooks;
String userphoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks);
        gotouploadpdfpagebtn=findViewById(R.id.gotouploadpdfpage);
        gotolistofallebooks=findViewById(R.id.gotolistofuploadpdf);
        Intent intent = getIntent();
        userphoneno=intent.getStringExtra("phoneno");
        gotouploadpdfpagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ebooks.this,Uploadpdf.class);
                intent.putExtra("phoneno", String.valueOf(userphoneno));
                startActivity(intent);
            }
        });
        gotolistofallebooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ebooks.this,AllUploadedebooks.class);
                intent.putExtra("phoneno", String.valueOf(userphoneno));
                startActivity(intent);
            }
        });
    }
}