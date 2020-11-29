package com.sdl.knowlegdetrove.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdl.knowlegdetrove.Listofalluseruploadedbooks;
import com.sdl.knowlegdetrove.R;
import com.sdl.knowlegdetrove.bookproduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HelperAdapter  extends RecyclerView.Adapter{
    List<bookproduct> fetchdataList;
    Context context;
    HelperAdapter helperAdapter;
    DatabaseReference databaseReference;


    public HelperAdapter(Context mcontext,List<bookproduct> fetchdataList) {
        this.fetchdataList = fetchdataList;
        context=mcontext;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookproductlayout,parent,false);
        ViewHolderClass viewHolderClass =new ViewHolderClass(view);
        databaseReference= FirebaseDatabase.getInstance().getReference("Product");

        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass=(ViewHolderClass)holder;

        bookproduct fetchdata = fetchdataList.get(position);
        viewHolderClass.bookname.setText(fetchdata.getBookname());
        viewHolderClass.bookdesc.setText(fetchdata.getBookdesc());
        viewHolderClass.bookprice.setText(fetchdata.getBookprice());
        //phoneno left
        Picasso.get().load(fetchdata.getImageUrl()).into(viewHolderClass.image);
        //Picasso.get().load(model.getImageUrl()).into(holder.bookimage);
        String uid=fetchdata.getParentKey();
        viewHolderClass.whatsapphoneno.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Whatsapp Code and String number
                String phoneno=fetchdata.getPhoneno();
                phoneno="+91"+phoneno;
                String text="Hello ! I would like to purchase "+fetchdata.getBookname()+" Book which is been uploaded for sale on Knowledge Trove App for Price (₹): "+fetchdata.getBookprice()+" .Description of the Book is : "+fetchdata.getBookdesc();
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneno + "&text=" + text));
                    context.startActivity(intent);
                    databaseReference.child(uid).removeValue();

                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "It seems that Whatsapp is not installed in this Phone. ", Toast.LENGTH_LONG).show();

                }




            }
        });
    }

    @Override
    public int getItemCount() {
        return fetchdataList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView bookname,bookdesc,bookprice;

        ImageView image,whatsapphoneno;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            bookname=itemView.findViewById(R.id.get_book_name_sellingpage);
            bookdesc=itemView.findViewById(R.id.get_book_desc_sellingpage);
            bookprice=itemView.findViewById(R.id.get_book_price_sellingpage);
            image=itemView.findViewById(R.id.get_bookimage_sellingpage);
           // phoneno=itemView.findViewById(R.id.get_book_phoneno);
            whatsapphoneno=itemView.findViewById(R.id.get_book_phoneno);
        }
    }
}
