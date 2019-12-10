package com.example.warehousemanagment.Shadi.EditProduct;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.Shadi.Models.Product;
import com.example.warehousemanagment.Shadi.Utils.FirebaseOperation;
import com.example.warehousemanagment.Shadi.Utils.UniversalImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProduct extends Fragment implements View.OnClickListener {

    /////
    TextView id, trademark;
    EditText name, color, depth, width, height, weight, inner_count;
    ImageView imageView;
    Button saveUpdate;

    ///////
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;
    
    ////////////
    private static final String TAG = "EditProduct";

    public EditProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_product_fragment_layout, container, false);
        id = view.findViewById(R.id.product_id);
        trademark = view.findViewById(R.id.product_trade);
        name = view.findViewById(R.id.product_name);
        color = view.findViewById(R.id.product_color);
        depth = view.findViewById(R.id.product_depth);
        weight = view.findViewById(R.id.product_weight);
        height = view.findViewById(R.id.product_height);
        width = view.findViewById(R.id.product_width);
        inner_count = view.findViewById(R.id.package_inside);
        imageView = view.findViewById(R.id.img_load);
        saveUpdate = view.findViewById(R.id.save_update_product);
        saveUpdate.setOnClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference();
        initImageLoader();
        initWidgets();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void initWidgets(){
        final String product_id = getArguments().getString("productId");
        final String product_trademark = getArguments().getString("productTrademark");
        Query query = mRef.child(getString(R.string.company_name));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = new Product();
                for(DataSnapshot single:dataSnapshot.getChildren()){
                    if(single.getKey().equals(getContext().getString(R.string.field_products))){
                        for(DataSnapshot ds:single.getChildren()){
                            if(ds.getKey().equals(product_trademark)){
                                try {
                                    Map<String,Object> objectMap = (Map<String, Object>) ds.child(product_id).getValue();
                                    product.setInner_count(Integer.parseInt(objectMap.get(getString(R.string.field_inner_count)).toString()));
                                    product.setId(Integer.parseInt(objectMap.get(getString(R.string.field_id)).toString()));
                                    product.setName(objectMap.get(getString(R.string.field_name)).toString());
                                    product.setColor(objectMap.get(getString(R.string.field_color)).toString());
                                    product.setHeight(Float.parseFloat(objectMap.get(getString(R.string.field_height)).toString()));
                                    product.setWidth(Float.parseFloat(objectMap.get(getString(R.string.field_width)).toString()));
                                    product.setDepth(Float.parseFloat(objectMap.get(getString(R.string.field_depth)).toString()));
                                    product.setKg(Float.parseFloat(objectMap.get(getString(R.string.field_kg)).toString()));
                                    product.setType(objectMap.get(getString(R.string.field_type)).toString());
                                    product.setTrademark(objectMap.get(getString(R.string.field_trademark)).toString());
                                    product.setImgurl(objectMap.get(getString(R.string.field_imgUrl)).toString());
                                    product.setUnite(objectMap.get(getString(R.string.field_unite)).toString());

                                }catch (NullPointerException e){
                                    Log.e(TAG, "initWidgets: " + e.toString() );
                                }
                                Log.d(TAG, "onDataChange: " + product.getId());
                                id.setText(String.valueOf(product.getId()));
                                trademark.setText(product_trademark);
                                name.setText(product.getName());
                                color.setText(product.getColor());
                                depth.setText(String.valueOf(product.getDepth()));
                                width.setText(String.valueOf(product.getWidth()));
                                height.setText(String.valueOf(product.getHeight()));
                                weight.setText(String.valueOf(product.getKg()));
                                inner_count.setText(String.valueOf(product.getInner_count()));
                                String imgURL = "http://site.com/image.png";
                                UniversalImageLoader.setImage(imgURL,imageView,null,"");

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    @Override
    public void onClick(View v) {
        FirebaseOperation operation = new FirebaseOperation();
        String company = getString(R.string.company_name);
        String products = getString(R.string.field_products);
        String productId = id.getText().toString();
        String productTrademark = trademark.getText().toString();
        if(v == saveUpdate){
            if(name.getText() != null ){
                operation.updateProductValue(company,products,productTrademark,productId,getString(R.string.field_name),name.getText().toString());
            }
            if(color.getText() != null ){
                Log.d(TAG, "onClick: " + color.getText());
                operation.updateProductValue(company,products,productTrademark,productId,getString(R.string.field_color),color.getText().toString());
            }
            if(depth.getText() != null){
                operation.updateProductValue(company,products,productTrademark,productId,getString(R.string.field_depth),depth.getText().toString());
            }
            if(width.getText() != null){
                operation.updateProductValue(company,products,productTrademark,productId,getString(R.string.field_width),width.getText().toString());
            }
            if(height.getText() != null){
                operation.updateProductValue(company,products,productTrademark,productId,getString(R.string.field_height),height.getText().toString());
            }
            if(weight.getText() != null){
                operation.updateProductValue(company,products,productTrademark,productId,getString(R.string.field_kg),weight.getText().toString());
            }
            if(inner_count.getText() != null){
                operation.updateProductValue(company,products,productTrademark,productId,getString(R.string.field_inner_count),inner_count.getText().toString());
            }
        }
    }
}
