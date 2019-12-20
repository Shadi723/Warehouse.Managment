package com.example.warehousemanagment.Shadi.AddNewProduct;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.Shadi.Models.Product;
import com.example.warehousemanagment.Shadi.Models.Trademark;
import com.example.warehousemanagment.Shadi.Utils.UniversalImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewProduct extends Fragment implements View.OnClickListener {


    private EditText product_id, product_name, product_color, product_depth, product_height, product_weight, product_width, package_inside;
    private Button save;
    private ImageView img_load;
    private Spinner trade;
    private NavController navController;
    private boolean img_loaded = false;

    private static final String TAG = "AddNewProduct";
    //firebase

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;

    private FragmentActivity fragmentActivity;

    public AddNewProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentActivity = getActivity();

        View view = inflater.inflate(R.layout.add_new_product_fragment_layout,container,false);
        product_id = view.findViewById(R.id.product_id);
        product_name = view.findViewById(R.id.product_name);
        product_color = view.findViewById(R.id.product_color);
        product_depth = view.findViewById(R.id.product_depth);
        product_height = view.findViewById(R.id.product_height);
        product_width = view.findViewById(R.id.product_width);
        product_weight = view.findViewById(R.id.product_weight);
        package_inside = view.findViewById(R.id.package_inside);
        trade = view.findViewById(R.id.product_trade);
        save = view.findViewById(R.id.save_product_new);
        img_load = view.findViewById(R.id.img_load);
        navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
        save.setOnClickListener(this);
        img_load.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
        getTrademarks();
        initImageLoader();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            String code = getArguments().getString("ProductCode");
            if(code != null) {
                product_id.setText(code);
                product_id.setFocusable(false);
            }
        }catch (NullPointerException e){
            Log.e(TAG, "onViewCreated: " + e.toString() );
        }

    }

    private void getTrademarks(){
        Query query1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.company_name))
                .child(getString(R.string.field_trademark));
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> allTrades = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String trademark;
                    Log.d(TAG, "onDataChange: "+ds.getValue(Trademark.class).getType());
                    if(ds.getValue(Trademark.class).getType().equals("Pvc Profile")){
                        trademark = ds.getValue(Trademark.class).getName();
                        allTrades.add(trademark);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(fragmentActivity,R.layout.spinner_simple_layout,allTrades);
                trade.setAdapter(adapter);
                trade.setSelection(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v == save){
            if(product_id.getText() != null && product_name.getText() != null && product_weight.getText() != null
                    && product_height.getText() != null && product_width.getText() != null &&product_color.getText() != null
                    && package_inside.getText() != null){
                Product product = new Product();
                product.setId(Integer.parseInt(product_id.getText().toString()));
                product.setName(product_name.getText().toString());
                product.setWidth(Float.parseFloat(product_width.getText().toString()));
                product.setHeight(Float.parseFloat(product_height.getText().toString()));
                product.setColor(product_color.getText().toString());
                product.setKg(Float.parseFloat(product_weight.getText().toString()));
                product.setInner_count(Integer.parseInt(package_inside.getText().toString()));
                product.setDepth(Float.parseFloat(product_width.getText().toString()));
                product.setType("1");
                product.setCategory("Pvc Profile");
                if(img_loaded){
                    //save product
                    product.setTrademark(trade.getSelectedItem().toString());
                    product.setImgUrl("http://site.com/image.png");
                    addProduct(product);
                    Toast.makeText(getContext(),getString(R.string.successfully_added),Toast.LENGTH_LONG).show();
                    navController.navigate(R.id.action_addNewProduct_to_goToScanDialog);
                }else{
                    Toast.makeText(getContext(),getString(R.string.load_image_message),Toast.LENGTH_LONG).show();
                }
            }
            else{
                showErrorMessage();
            }
        }
        if(v == img_load){
            img_loaded = true;
            String imgURL = "http://site.com/image.png";
            UniversalImageLoader.setImage(imgURL,img_load,null,"");
        }
    }

    private void showErrorMessage() {
        if(TextUtils.isEmpty(product_id.getText()))
            product_id.setError(getString(R.string.fill_product_id));
        if(TextUtils.isEmpty(product_color.getText()))
            product_color.setError(getString(R.string.fill_product_color));
        if(TextUtils.isEmpty(product_name.getText()))
            product_name.setError(getString(R.string.fill_product_name));
        if(TextUtils.isEmpty(product_height.getText()))
            product_height.setError(getString(R.string.fill_product_height));
        if(TextUtils.isEmpty(product_width.getText()))
            product_width.setError(getString(R.string.fill_product_width));
        if(TextUtils.isEmpty(product_weight.getText()))
            product_weight.setError(getString(R.string.fill_product_weight));
        if(TextUtils.isEmpty(package_inside.getText()))
            package_inside.setError(getString(R.string.fill_product_package_in_num));

    }

    private void addProduct(Product product) {
        mRef.child(getString(R.string.company_name))
                .child(getString(R.string.field_products))
                .child(product.getTrademark())
                .child(String.valueOf(product.getId()))
                .setValue(product);

    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
}
