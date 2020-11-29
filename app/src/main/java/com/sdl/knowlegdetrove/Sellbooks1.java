package com.sdl.knowlegdetrove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sellbooks1 extends AppCompatActivity {
        Button add_a_new_product,list_of_all_products;
    String userphoneno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellbooks1);
        add_a_new_product = findViewById(R.id.addanewbookbtn);
        list_of_all_products=findViewById(R.id.listofallbooks);
        Intent intent = getIntent();
        userphoneno=intent.getStringExtra("phoneno");

        add_a_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Sellbooks1.this,AddaNewBook.class);
                intent.putExtra("phoneno", String.valueOf(userphoneno));
                startActivity(intent);
            }
        });
        list_of_all_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Sellbooks1.this,Listofalluseruploadedbooks.class);
                intent.putExtra("phoneno", String.valueOf(userphoneno));
                startActivity(intent);
            }
        });
    }

}