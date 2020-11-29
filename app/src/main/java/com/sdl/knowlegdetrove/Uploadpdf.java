package com.sdl.knowlegdetrove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Uploadpdf extends AppCompatActivity {
    String userphoneno;
    EditText ebookname,ebookname1;
    Button uploadebookbtn;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadpdf);
        Intent intent = getIntent();
        userphoneno=intent.getStringExtra("phoneno");
        ebookname=findViewById(R.id.ebooknameonuploadebook);
        uploadebookbtn=findViewById(R.id.uploadbuttononebookuploadpage);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Ebooks");
        ebookname1=findViewById(R.id.ebooknameonuploadebook1);
        ebookname.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectEbook();
            }
        });
    }

    private void selectEbook() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK && data != null && data.getData()!=null){
            uploadebookbtn.setEnabled(true);
            ebookname.setText("Pdf Selected !!!");//data.getDataString().substring(data.getDataString().lastIndexOf("/")+1)
            uploadebookbtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    uploadEbookFileFirebase(data.getData());
                }
            });
            }
    }

    private void uploadEbookFileFirebase(Uri data) {
        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("File is Uploading ...");
        progressDialog.show();
        StorageReference reference = storageReference.child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uritask= taskSnapshot.getStorage().getDownloadUrl();
                while(!uritask.isComplete()) ;
                    Uri uri = uritask.getResult();
                    Ebookproduct ebookproduct = new Ebookproduct(userphoneno, ebookname1.getText().toString(), uri.toString());
                    databaseReference.child(databaseReference.push().getKey()).setValue(ebookproduct);
                    Toast.makeText(Uploadpdf.this, "File Uploaded Succesfully !", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(Uploadpdf.this, Ebooks.class);
                    startActivity(intent);

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>(){

            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploading .."+(int)progress+"%");

            }
        });

    }
}