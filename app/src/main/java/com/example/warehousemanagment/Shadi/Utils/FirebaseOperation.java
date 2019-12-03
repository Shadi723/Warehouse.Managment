package com.example.warehousemanagment.Shadi.Utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseOperation {
    private FirebaseDatabase firebaseDatabase;
    private  DatabaseReference mRef;
    public FirebaseOperation() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference();
    }
    public  void updateProductValue(String company,String products, String trademark, String productId,String attribute, String value){
        mRef.child(company)
                .child(products)
                .child(trademark)
                .child(productId)
                .child(attribute)
                .setValue(value);
    }
}
