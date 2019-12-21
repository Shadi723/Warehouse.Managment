package com.example.warehousemanagment.ali.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.warehousemanagment.ali.utils.ProductAdapter;
import com.example.warehousemanagment.R;
import com.example.warehousemanagment.Shadi.Models.Product;
import com.example.warehousemanagment.ali.utils.ProductNew;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProductsFragment extends Fragment {

    private static final String TAG = "ProductsFragment";

    private NavController navController;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference stockDBReference;

    private FragmentActivity fragmentActivity;

    private List<ProductNew> productsList;
    private ListView listView;
    private ProductAdapter productAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        //navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentActivity = getActivity();

        firebaseDatabase = FirebaseDatabase.getInstance();
        stockDBReference = firebaseDatabase.getReference(fragmentActivity.getString(R.string.company_name)).child("Products");
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllProducts2();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        //getAllProducts2();
    }

    private void initViews(View view) {

        listView = view.findViewById(R.id.list_view_products);

        productsList = new ArrayList<>();

        productAdapter = new ProductAdapter(fragmentActivity, productsList);
        listView.setAdapter(productAdapter);
    }

    private void getAllProducts() {

        stockDBReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for (DataSnapshot type : dataSnapshot.getChildren()) {

                    for (DataSnapshot typeProducts : type.getChildren()) {

                        ProductNew product = typeProducts.getValue(ProductNew.class);
                        productsList.add(product);

                    }
                }
                //productAdapter = new ProductAdapter(fragmentActivity, productsList);
                //listView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
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

    private void getAllProducts2(){

        stockDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot type : dataSnapshot.getChildren()) {

                    for (DataSnapshot typeProducts : type.getChildren()) {

                        ProductNew product = typeProducts.getValue(ProductNew.class);
                        productsList.add(product);

                    }
                }
                //productAdapter = new ProductAdapter(fragmentActivity, productsList);
                //listView.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
