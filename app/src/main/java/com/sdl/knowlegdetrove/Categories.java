package com.sdl.knowlegdetrove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Categories extends AppCompatActivity {
    String userphoneno;

    Button gotobuybooks,gotosellbookspage1,gotoebookspage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        gotosellbookspage1 = findViewById(R.id.gotosellbooks1);
        Intent intent = getIntent();
        userphoneno = intent.getStringExtra("phoneno");
        gotosellbookspage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this, Sellbooks1.class);
                intent.putExtra("phoneno", String.valueOf(userphoneno));
                startActivity(intent);
            }
        });
        gotobuybooks = findViewById(R.id.gotobuybooks);
        gotobuybooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this, BuyBooks.class);
                intent.putExtra("phoneno", String.valueOf(userphoneno));
                startActivity(intent);
            }
        });

        gotoebookspage = findViewById(R.id.ebookspage);
        gotoebookspage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this, Ebooks.class);
                intent.putExtra("phoneno", String.valueOf(userphoneno));
                startActivity(intent);
            }
        });

    }



}
