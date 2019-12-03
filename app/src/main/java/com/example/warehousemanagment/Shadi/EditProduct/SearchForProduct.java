package com.example.warehousemanagment.Shadi.EditProduct;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;

import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.Shadi.Models.Product;
import com.example.warehousemanagment.Shadi.Models.Trademark;
import com.example.warehousemanagment.Shadi.Models.Type;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchForProduct extends Fragment implements  AdapterView.OnItemSelectedListener {

    private static final String TAG = "SearchForProduct";
    ////////////
    Spinner type, trademark;
    EditText product_id;
    Button search;
    ProgressBar progressBar;
    TextView waitMessage;

    ////////////
    String selected_type, selected_trademark, selected_id;
    ////////////
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;
    NavController navController;
    public SearchForProduct() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_for_product_fragment_layout,container,false);
        type = view.findViewById(R.id.select_type);
        trademark = view.findViewById(R.id.select_trademark);
        product_id = view.findViewById(R.id.enter_id);
        search = view.findViewById(R.id.search_product);
        progressBar = view.findViewById(R.id.progressBarWait);
        waitMessage = view.findViewById(R.id.pleaseWait);
        type.setVisibility(View.GONE);
        trademark.setVisibility(View.GONE);
        product_id.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        type.setOnItemSelectedListener(this);
        initTypeWidgets();
        return view;
    }

    private void initTypeWidgets(){
        Query query1 = firebaseDatabase.getReference().child(getString(R.string.company_name));
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> types = new ArrayList<>();
                for(DataSnapshot single: dataSnapshot.getChildren()){
                    if(single.getKey().equals(getString(R.string.field_types))){
                        for(DataSnapshot ds:single.getChildren()){
                            try {
                                types.add(ds.getValue(Type.class).getName());
                            }catch (NullPointerException e){
                                Log.e(TAG, "onDataChange: " + e.toString() );
                            }
                        }
                    }
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),R.layout.spinner_simple_layout,types);
                progressBar.setVisibility(View.GONE);
                waitMessage.setVisibility(View.GONE);
                type.setVisibility(View.VISIBLE);
                trademark.setVisibility(View.VISIBLE);
                product_id.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                type.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchForProduct(final String trademark, final String id) {
        Query query = firebaseDatabase.getReference().child(getString(R.string.company_name))
                .child(getString(R.string.field_products))
                .child(trademark)
                .child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.getKey());
                if (dataSnapshot.getValue() != null) {
                    ////navigate to edit product
                    Toast.makeText(getContext(), getString(R.string.successfully_founded), Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("productId",id);
                    bundle.putString("productTrademark",trademark);
                    navController.navigate(R.id.action_searchForProduct_to_editProduct,bundle);

                } else {
                    Toast.makeText(getContext(), getString(R.string.failed_to_find), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
        selected_type = parent.getSelectedItem().toString();
        Query query1 = firebaseDatabase.getReference().child(getString(R.string.company_name));
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> trademarks = new ArrayList<>();
                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    if (single.getKey().equals(getString(R.string.field_trademark))) {
                        for (DataSnapshot ds : single.getChildren()) {
                            try {
                                if (ds.getValue(Trademark.class).getType().equals(selected_type))
                                    trademarks.add(ds.getValue(Trademark.class).getName());
                            } catch (NullPointerException e) {
                                Log.e(TAG, "onDataChange: " + e.toString());
                            }
                        }
                    }
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.spinner_simple_layout, trademarks);
                trademark.setAdapter(adapter1);
                trademark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selected_trademark = trademark.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if(product_id.getText() != null){
                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selected_id = product_id.getText().toString();
                            Log.d(TAG, "onItemSelected: "  + selected_id + " -- " + selected_trademark);
                            searchForProduct(selected_trademark,selected_id);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
