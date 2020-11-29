package com.sdl.knowlegdetrove.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdl.knowlegdetrove.Categories;
import com.sdl.knowlegdetrove.Listofalluseruploadedbooks;
import com.sdl.knowlegdetrove.R;
import com.sdl.knowlegdetrove.bookproduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UseruploadsHelperAdapter extends RecyclerView.Adapter {
    List<bookproduct> fetchdataList;
    Context context;

    public UseruploadsHelperAdapter(List<bookproduct> fetchdataList, Context context) {
        this.fetchdataList = fetchdataList;
        this.context = context;
    }

    public UseruploadsHelperAdapter(List<bookproduct> fetchdataList) {
        this.fetchdataList = fetchdataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.useruploadedproducts,parent,false);
        ViewHolderClassUserUploads viewHolderClassUserUploads = new ViewHolderClassUserUploads(view);
        return viewHolderClassUserUploads;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClassUserUploads viewHolderClassUserUploads = (ViewHolderClassUserUploads)holder;
        bookproduct fetchdata = fetchdataList.get(position);
        viewHolderClassUserUploads.bookname.setText(fetchdata.getBookname());
        viewHolderClassUserUploads.bookdesc.setText(fetchdata.getBookdesc());
        viewHolderClassUserUploads.bookprice.setText(fetchdata.getBookprice());
        Picasso.get().load(fetchdata.getImageUrl()).into(viewHolderClassUserUploads.image);

        viewHolderClassUserUploads.updateproductdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String booknamestr,bookdescstr,bookpricestr;
                String uid;
                booknamestr=viewHolderClassUserUploads.bookname.getText().toString();
                bookdescstr=viewHolderClassUserUploads.bookdesc.getText().toString();
                bookpricestr=viewHolderClassUserUploads.bookprice.getText().toString();
                uid=fetchdata.getParentKey();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Product");

                databaseReference.child(uid).child("bookname").setValue(booknamestr);
                databaseReference.child(uid).child("bookdesc").setValue(bookdescstr);
                databaseReference.child(uid).child("bookprice").setValue(bookpricestr);
                Toast.makeText(context,"Book Details Updated Successfully !!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Categories.class);
                context.startActivity(intent);



            }
        });
        viewHolderClassUserUploads.deleteproduct.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String uid=fetchdata.getParentKey();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Product");
                databaseReference.child(uid).removeValue();
                Toast.makeText(context,"Book Removed Successfully !!!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Categories.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fetchdataList.size();
    }
    public class ViewHolderClassUserUploads extends RecyclerView.ViewHolder{
        EditText bookname,bookdesc,bookprice;

        ImageView image;
        Button updateproductdetails,deleteproduct;
        public ViewHolderClassUserUploads(@NonNull View itemView) {
            super(itemView);
            bookname=itemView.findViewById(R.id.set_book_name_useruploads);
            bookdesc=itemView.findViewById(R.id.set_book_desc_useruploads);
            bookprice=itemView.findViewById(R.id.set_book_price_useruploads);
            image=itemView.findViewById(R.id.set_bookimage_useruploads);
            // phoneno=itemView.findViewById(R.id.get_book_phoneno);
            updateproductdetails=itemView.findViewById(R.id.updateproductdetails_useruploads);
            deleteproduct=itemView.findViewById(R.id.removethisproduct_useruploads);
        }
    }
}
