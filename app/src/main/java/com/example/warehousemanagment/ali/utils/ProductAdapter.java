package com.example.warehousemanagment.ali.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.Shadi.Models.Product;
import com.example.warehousemanagment.Shadi.Utils.UniversalImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ProductAdapter extends BaseAdapter {


    private Context mContext;
    private LayoutInflater inflater;
    private List<ProductNew> productsList;
    private UniversalImageLoader universalImageLoader;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference stockDBReference;

    public ProductAdapter (Context context, List<ProductNew> productsList){

        mContext = context;
        inflater = LayoutInflater.from(mContext);
        universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

        this.productsList = productsList;

        firebaseDatabase = FirebaseDatabase.getInstance();
        stockDBReference = firebaseDatabase.getReference(context.getResources().getString(R.string.company_name)).child("Stock");
    }

    private class ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView productQuantity;
        TextView productPrice;
        ProgressBar progressBar;
    }

    @Override
    public int getCount() {
        return productsList.size();
    }

    @Override
    public Object getItem(int i) {
        return productsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productsList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        ProductNew currentItem = productsList.get(position);

        final ViewHolder viewHolder;

        if(listItem == null){

            viewHolder = new ViewHolder();

            //Note : avoid passing null as parent and use the 3 argument constructor instead
            listItem = inflater.inflate(R.layout.item_product, parent, false);

            //locate the views in item_traffic_sign
            viewHolder.productImage = listItem.findViewById(R.id.imgView_product);
            viewHolder.productName = listItem.findViewById(R.id.txtView_product_name);
            viewHolder.productQuantity = listItem.findViewById(R.id.txtView_product_quantity);
            viewHolder.productPrice = listItem.findViewById(R.id.txtView_product_price);
            viewHolder.progressBar = listItem.findViewById(R.id.progressBar_product);

            listItem.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (currentItem != null){

            UniversalImageLoader.setImage(currentItem.getImageurl(), viewHolder.productImage,viewHolder.progressBar,"");
            viewHolder.productName.setText(currentItem.getName());
            viewHolder.productQuantity.setText(currentItem.getColor());
            viewHolder.productPrice.setText(currentItem.getTrademark());
        }

        return listItem;
    }

    private void getAllProducts(){

        stockDBReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for (DataSnapshot companySnapshot : dataSnapshot.getChildren()){

                    for (DataSnapshot companyProduct : companySnapshot.getChildren()){

                        ProductNew product = companyProduct.getValue(ProductNew.class);
                        productsList.add(product);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
