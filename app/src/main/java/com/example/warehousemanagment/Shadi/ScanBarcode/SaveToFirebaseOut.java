package com.example.warehousemanagment.Shadi.ScanBarcode;


import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.Shadi.Models.ProductSettings;
import com.example.warehousemanagment.Shadi.Models.SoldProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaveToFirebaseOut extends Fragment implements View.OnClickListener {

    private String barcode_value;
    private TextView id,name,packgeIn,color, trademark;
    private EditText packageCount;
    private Button save;
    private ImageView imageView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;
    private ProductSettings settings;
    private int total_package;
    private float total_quantity;
    private static final String TAG = "SaveToFirebaseIn";
    private NavController navController;

    private FragmentActivity fragmentActivity;

    public SaveToFirebaseOut() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentActivity = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.save_to_firebase_out_fragment_layout, container, false);
        id = view.findViewById(R.id.product_id);
        name = view.findViewById(R.id.product_name);
        color = view.findViewById(R.id.product_color);
        packgeIn = view.findViewById(R.id.inner_count);
        packageCount = view.findViewById(R.id.package_count);
        save = view.findViewById(R.id.save_product);
        imageView = view.findViewById(R.id.product_img);
        trademark = view.findViewById(R.id.product_trade);
        navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference();
        settings = getArguments().getParcelable("product");
        save.setOnClickListener(this);
        init();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getInformation();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: " + settings.getTrademark().getName());

    }

    //Init views in layout
    void init() {
        id.setText(String.valueOf(settings.getProduct().getId()));
        name.setText(settings.getProduct().getName());
        color.setText(settings.getProduct().getColor());
        trademark.setText(settings.getTrademark().getName());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.save_product){
            if(!packageCount.getText().toString().equals("") && !packgeIn.getText().toString().equals("")){
                final int count = Integer.parseInt(packageCount.getText().toString());
                final float inner_count = Float.parseFloat((packgeIn.getText().toString()));
                Query query2 = FirebaseDatabase.getInstance().getReference(getString(R.string.company_name))
                        .child(getString(R.string.stock))
                        .child(settings.getTrademark().getName())
                        .child(String.valueOf(settings.getProduct().getId()))
                        .child(getString(R.string.field_total_quantity));
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getKey().equals(getString(R.string.field_total_quantity))) {
                            Log.d(TAG, "onDataChange: " + dataSnapshot.getValue() + settings.getProduct().getId());
                            total_quantity =  dataSnapshot.getValue(Integer.class);
                            if(count*inner_count <= total_quantity){
                                total_quantity = total_quantity - inner_count*count;
                                Query query1 = FirebaseDatabase.getInstance().getReference(getString(R.string.company_name))
                                        .child(getString(R.string.stock))
                                        .child(settings.getTrademark().getName())
                                        .child(String.valueOf(settings.getProduct().getId()))
                                        .child(getString(R.string.field_total_count));
                                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getKey() == getString(R.string.field_total_count)) {
                                            total_package =  dataSnapshot.getValue(Integer.class) - count;
                                            mRef.child(getString(R.string.company_name))
                                                    .child(getString(R.string.stock))
                                                    .child(settings.getTrademark().getName())
                                                    .child(String.valueOf(settings.getProduct().getId()))
                                                    .child(getString(R.string.field_total_count))
                                                    .setValue(total_package);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                mRef.child(getString(R.string.company_name))
                                        .child(getString(R.string.stock))
                                        .child(settings.getTrademark().getName())
                                        .child(String.valueOf(settings.getProduct().getId()))
                                        .child(getString(R.string.field_total_quantity))
                                        .setValue(total_quantity);

                                SoldProduct sold = new SoldProduct();
                                sold.setDate(new Date());
                                sold.setUser_name("ahmed");
                                sold.setPackage_count(count);
                                sold.setTotal_quantity(count*inner_count);
                                sold.setTrademaek(settings.getTrademark().getName());
                                mRef = FirebaseDatabase.getInstance().getReference();
                                mRef.child(getString(R.string.company_name))
                                        .child(getString(R.string.sold_product))
                                        .child(String.valueOf(settings.getProduct().getId()))
                                        .push()
                                        .setValue(sold);
                            }
                            else {
                                Toast.makeText(getContext(), fragmentActivity.getString(R.string.requested_quantity_not_exist),Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                navController.navigate(R.id.action_saveToFirebaseOut_to_goToScanDialog);
            }
            else {
                if(packageCount.getText() == null){
                    packageCount.setError(getString(R.string.fill_package_count));
                }
                if(packgeIn.getText() == null){
                    packgeIn.setError(getString(R.string.fill_inner_package));
                }
            }
        }
    }
}
