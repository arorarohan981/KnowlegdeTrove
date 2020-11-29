package com.sdl.knowlegdetrove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdl.knowlegdetrove.Adapter.HelperAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BuyBooks extends AppCompatActivity {
    private List <bookproduct> fetchdata;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    DatabaseReference databaseReference;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_books);
        recyclerView=findViewById(R.id.recyclerviewsellbooks);
        showAlertDialogButtonClicked();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchdata=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("Product");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    bookproduct data = ds.getValue(bookproduct.class);
                    fetchdata.add(data);
                }
                if (fetchdata.size() > 0) {
                    helperAdapter = new HelperAdapter(BuyBooks.this, fetchdata);
                    recyclerView.setAdapter(helperAdapter);
                } else {
                    Toast.makeText(BuyBooks.this,"No Books For Recycling Right Now !",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BuyBooks.this,Categories.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void showAlertDialogButtonClicked()
    {

        // Create an alert builder
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Alert !");

        // set the custom layout
        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.dialogbox,
                        null);
        builder.setView(customLayout);

        // add a button
        builder
                .setPositiveButton(
                        "Okay",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which)
                            {
                            dialog.dismiss();


                            }
                        });

        // create and show
        // the alert dialog
        AlertDialog dialog
                = builder.create();
        dialog.show();
    }
}