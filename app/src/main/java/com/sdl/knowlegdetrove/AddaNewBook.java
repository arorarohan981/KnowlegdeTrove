package com.sdl.knowlegdetrove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class AddaNewBook extends AppCompatActivity {

    ImageButton imagebtn;

    EditText bookname, bookdesc, bookprice;
    Button addproduct;
    String booknamep,bookdescp,bookpricep,phonenop;
    FirebaseDatabase rootnode;
    DatabaseReference root = rootnode.getInstance().getReference("Product");
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    Uri imageUri;
    String userphoneno;
    TextView alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adda_new_book);
        imagebtn = findViewById(R.id.Uploadimage);
        bookname = findViewById(R.id.bookname);
        bookdesc = findViewById(R.id.bookdesc);
        bookprice = findViewById(R.id.bookprice);
        addproduct = findViewById(R.id.addproduct);
        showAlertDialogButtonClicked();
        Intent intent = getIntent();
        userphoneno=intent.getStringExtra("phoneno");

        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    uploadtofirebase(imageUri);
                } else {
                    Toast.makeText(AddaNewBook.this, "Please Select an Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadtofirebase(Uri uri) {
        StorageReference fileref = reference.child(System.currentTimeMillis() + "." + getFirebaseExtension(uri));
        fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            booknamep=bookname.getText().toString();
                            bookdescp=bookdesc.getText().toString();
                            bookpricep=bookprice.getText().toString();
                            if(booknamecheck(booknamep) && bookdesccheck(bookdescp) && bookpricecheck(bookpricep)) {
                                String productid = root.push().getKey();
                                bookproduct bookproductuploaddata = new bookproduct(uri.toString(), booknamep, bookdescp, bookpricep, userphoneno,productid);
                                root.child(productid).setValue(bookproductuploaddata);
                                Toast.makeText(AddaNewBook.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddaNewBook.this, Categories.class);
                                startActivity(intent);
                            }
                        }
                    });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AddaNewBook.this, "Uploading Failed !! Please Try Again !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean bookpricecheck(String bookpricep) {
        if(bookpricep.isEmpty()){
            Toast.makeText(this, "Book Price cannot be Empty !", Toast.LENGTH_SHORT).show();
            return false;
        }else if(Integer.parseInt(bookpricep)<0){
            Toast.makeText(this, "Book Price cannot be less than 0 !", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean bookdesccheck(String bookdescp) {
        if(bookdescp.isEmpty()){
            Toast.makeText(this, "Book Desc cannot be Empty !", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean booknamecheck(String bookname) {
        if(bookname.isEmpty()){
            Toast.makeText(this, "Book Name cannot be Empty !", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }


    private String getFirebaseExtension(Uri uri) {
        ContentResolver cr= getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            imagebtn.setImageURI(imageUri);
        }
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
                        R.layout.alertdialogonaddabookforsale,
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