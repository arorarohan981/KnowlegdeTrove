package com.sdl.knowlegdetrove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllUploadedebooks extends AppCompatActivity {
ListView listView;
DatabaseReference databaseReference;
List<Ebookproduct> uploadedebooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_uploadedebooks);
        listView = findViewById(R.id.allebookslistview);
        uploadedebooks=new ArrayList<>();

        viewAllebooks();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ebookproduct ebookproduct=uploadedebooks.get(position);

                Intent intent = new Intent();
                intent.setData(Uri.parse(ebookproduct.getEbookurl()));
                startActivity(intent);
            }
        });
    }

    private void viewAllebooks() {
        databaseReference=FirebaseDatabase.getInstance().getReference("Ebooks");
        databaseReference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot:snapshot.getChildren()){
                    Ebookproduct ebookproduct=postSnapshot.getValue(Ebookproduct.class);
                    uploadedebooks.add(ebookproduct);
                }
                if(!uploadedebooks.isEmpty()) {
                    String[] uploadname = new String[uploadedebooks.size()];
                    for (int i = 0; i < uploadname.length; i++) {
                        uploadname[i] = uploadedebooks.get(i).getEbookname();

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listviewadapter, uploadname);
                    listView.setAdapter(adapter);
                }else{
                    Toast.makeText(AllUploadedebooks.this, "No E-Books Avaialable Right Now",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AllUploadedebooks.this,Categories.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}