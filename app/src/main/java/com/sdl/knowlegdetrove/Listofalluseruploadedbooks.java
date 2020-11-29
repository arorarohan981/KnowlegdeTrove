package com.sdl.knowlegdetrove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdl.knowlegdetrove.Adapter.HelperAdapter;
import com.sdl.knowlegdetrove.Adapter.UseruploadsHelperAdapter;

import java.util.ArrayList;
import java.util.List;

public class Listofalluseruploadedbooks extends AppCompatActivity {
    List<bookproduct> fetchdata;
    String userphoneno;
    RecyclerView recyclerView;
    UseruploadsHelperAdapter helperAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofalluseruploadedbooks);
        Context context = Listofalluseruploadedbooks.this;
        recyclerView = findViewById(R.id.recyclerview_useruploads);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        userphoneno = intent.getStringExtra("phoneno");
        fetchdata = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    bookproduct data = ds.getValue(bookproduct.class);

                    if (data.comparePhoneno(userphoneno)) {
                        fetchdata.add(data);
                    }
                }
                if (fetchdata.size() > 0) {
                    helperAdapter = new UseruploadsHelperAdapter(fetchdata,context);
                    recyclerView.setAdapter(helperAdapter);

                } else {

                    Toast.makeText(getApplicationContext(), "You Have Not Added Any Books For Recycling !", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Categories.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}