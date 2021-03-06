package com.example.warehousemanagment.Shadi.ScanBarcode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.Shadi.Models.Product;
import com.example.warehousemanagment.Shadi.Models.Trademark;
import com.example.warehousemanagment.Shadi.Models.ProductSettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanBarcodeIn extends Fragment implements ZXingScannerView.ResultHandler
{

    private static final int ZXING_CAMERA_PERMISSION = 1;
    private NavController navController;
    private ZXingScannerView mScannerView;
    private static final String TAG = "AddNewProduct";
    private String [] parts;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_barode_in_fragment_layout,container,false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewGroup contentFrame = view.findViewById(R.id.content_frame);
        //Demo

        getInformation("1-60300-2");

     /*   mScannerView = new ZXingScannerView(getContext());
        contentFrame.addView(mScannerView);*/
    }
/*
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);                //Register  ourselves as handler for scan result
        mScannerView.startCamera();                         //Start camera on resume
    }

    @Override
    public void onStop() {
        super.onStop();
        mScannerView.stopCamera();                          //Stop camera on stop
    }
*/
    @Override
    public void handleResult(Result rawResult) {
        //Do somthing with the result here

        Log.d(TAG, "handleResult: " + rawResult.getText());       //Print scan result
        Log.d(TAG, "handleResult: " + rawResult.getBarcodeFormat().toString()); //Prints the scan format (qrcode, pdf417 etc.)

        if(rawResult.getText() != null){
            getInformation(rawResult.getText());
        }

        /*//If you would like to resume  scanning, call this method
        Toast.makeText(getContext(),"This is code" + rawResult.getText(),Toast.LENGTH_LONG).show();
        mScannerView.resumeCameraPreview(this);*/
    }

    /**
     * // Toggle flash:
     * void setFlash(boolean);
     *
     * // Toogle autofocus:
     * void setAutoFocus(boolean);
     *
     * // Specify interested barcode formats:
     * void setFormats(List<BarcodeFormat> formats);
     *
     * // Specify the cameraId to start with:
     * void startCamera(int cameraId);
     */

    private void getInformation(String barcode_value){
        parts = barcode_value.split("-");
        mRef = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "getInformation: " + parts[0] );
        if(parts[0].equals("1")) {
            Query query = mRef.child(getString(R.string.company_name));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ProductSettings settings = initWidgets(dataSnapshot);
                    if (settings.getProduct().getId() != 0 ) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("product", settings);
                        Log.d(TAG, "onDataChange: navigating to save to database");
                        navController.navigate(R.id.action_scanBarcodeIn_to_saveToFirebaseIn,bundle);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.no_product_in_product_list), Toast.LENGTH_LONG).show();
                        showAddNewDialog(parts[1], parts[0]);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    private Trademark getTrademark(DataSnapshot dataSnapshot) {
        Trademark trademark = new Trademark();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            //user_account_settings node
            // Log.d(TAG, "initWidgets: "+ds.child(parts[1]).getValue(Product.class).getName());
            if (ds.getKey().equals(getString(R.string.field_trademark))) {
                trademark.setId(ds.child(parts[2]).getValue(Trademark.class).getId());
                trademark.setName(ds.child(parts[2]).getValue(Trademark.class).getName());
            }
        }
        return trademark;
    }
    private ProductSettings initWidgets(DataSnapshot dataSnapshot){
        Product product = new Product();
        Trademark trademark = getTrademark(dataSnapshot);
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            //user_account_settings node
               // Log.d(TAG, "initWidgets: "+ds.child(parts[1]).getValue(Product.class).getName());
            if (ds.getKey().equals(getString(R.string.field_products))) {
                for(DataSnapshot single:ds.getChildren()){
                    if(single.getKey().equals(trademark.getName())){
                        Log.d(TAG, "initWidgets: inside" + single.getKey());
                        try {
                            Map<String, Object> objectMap = (Map<String, Object>) single.child(parts[1]).getValue();
                            product.setId(Integer.parseInt(objectMap.get(getString(R.string.field_id)).toString()));
                            product.setName(objectMap.get(getString(R.string.field_name)).toString());
                            product.setColor(objectMap.get(getString(R.string.field_color)).toString());
                            product.setHeight(Float.parseFloat(objectMap.get(getString(R.string.field_height)).toString()));
                            product.setWidth(Float.parseFloat(objectMap.get(getString(R.string.field_width)).toString()));
                            product.setDepth(Float.parseFloat(objectMap.get(getString(R.string.field_depth)).toString()));
                            product.setKg(Float.parseFloat(objectMap.get(getString(R.string.field_kg)).toString()));
                            product.setType(objectMap.get(getString(R.string.field_type)).toString());
                            product.setimgurl(objectMap.get(getString(R.string.field_imgUrl)).toString());
                            product.setUnite(objectMap.get(getString(R.string.field_unite)).toString());
                            product.setTrademark(objectMap.get(getString(R.string.field_trademark)).toString());
                            product.setInner_count(Integer.parseInt(objectMap.get(getString(R.string.field_inner_count)).toString()));
                        }catch (NullPointerException e){
                            Log.e(TAG, "initWidgets: " + e.toString() );
                        }
                    }
                }
            }
        }
        ProductSettings settings = new ProductSettings(product,trademark);
        Log.d(TAG, "initWidgets: " + settings.getTrademark().getName());
        return settings;
    }

    private void showAddNewDialog(String code, String type){
        Log.d(TAG, "showAddNew: hello my type " + type );
        Log.d(TAG, "showAddNew: hello my type " + type.equals("1") );
        if(type.equals("1")) {
            Bundle bundle = new Bundle();
            bundle.putString("ProductCode", code);
            Log.d(TAG, "showAddNew: hello my type " + type );
            navController.navigate(R.id.action_scanBarcodeIn_to_newProductDialog, bundle);
        }
    }
}
