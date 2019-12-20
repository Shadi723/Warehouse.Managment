package com.example.warehousemanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.warehousemanagment.shadi.Models.Product;
import com.example.warehousemanagment.utils.ProductsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productDescEditText;
    private EditText productQuantityEditText;
    private EditText productWeightEditText;

    private Button addProductBtn;

    private ListView productsListView;
    private List<Product> productList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_products);

       // initViews();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ALI-COMPANY").child("Products");
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                productList.clear();

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()){

                    Product product = productSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                productsAdapter = new ProductsAdapter(getApplicationContext(), productList);
                productsListView.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

   /*private void initViews(){

        productNameEditText = findViewById(R.id.editText_product_name);
        productDescEditText = findViewById(R.id.editText_product_desc);
        productQuantityEditText = findViewById(R.id.editText_product_quantity);
        productWeightEditText = findViewById(R.id.editText_product_weight);

        productList = new ArrayList<>();
        productsListView = findViewById(R.id.listView_products);

        addProductBtn = findViewById(R.id.btn_add_product);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });
    }*/

    private void addProduct(){

        String productName = productNameEditText.getText().toString().trim();
        String productDesc = productDescEditText.getText().toString().trim();
        String productWeight = productWeightEditText.getText().toString().trim();
        String productQuantity = productQuantityEditText.getText().toString().trim();

        if (!productName.isEmpty()){

            String id = databaseReference.push().getKey();

            //Product product = new Product(id, productName, productDesc, productQuantity, productWeight);

            // (id != null)
               // databaseReference.child(id).setValue(product);
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter product name first", Toast.LENGTH_SHORT).show();
        }
    }
}
