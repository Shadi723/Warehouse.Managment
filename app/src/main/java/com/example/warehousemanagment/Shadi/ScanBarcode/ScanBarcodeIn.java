package com.example.warehousemanagment.Shadi.ScanBarcode;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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

import static android.content.Context.VIBRATOR_SERVICE;

public class ScanBarcodeIn extends Fragment implements ZXingScannerView.ResultHandler
{

    private static final int ZXING_CAMERA_PERMISSION = 1;
    private NavController navController;
    private ZXingScannerView mScannerView;
    private static final String TAG = "AddNewProduct";
    ProgressBar progressBar;
    TextView waitMessage;
    private String [] parts;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_barode_in_fragment_layout,container,false);
        navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        progressBar = view.findViewById(R.id.progressBarWait);
        waitMessage = view.findViewById(R.id.pleaseWait);
        progressBar.setVisibility(View.GONE);
        waitMessage.setVisibility(View.GONE);
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewGroup contentFrame = view.findViewById(R.id.content_frame);
        //Demo
        //getInformation("1-60300-1-34.8");

        mScannerView = new ZXingScannerView(getContext());
        contentFrame.addView(mScannerView);
    }

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleResult(Result rawResult) {
        //Do somthing with the result here
        MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.beep);
        Log.d(TAG, "handleResult: " + rawResult.getText());       //Print scan result
        Log.d(TAG, "handleResult: " + rawResult.getBarcodeFormat().toString()); //Prints the scan format (qrcode, pdf417 etc.)

        if(rawResult.getText() != null){
            Vibrator vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else{
                vibrator.vibrate(200);
            }
            mp.start();
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
        progressBar.setVisibility(View.VISIBLE);
        waitMessage.setVisibility(View.VISIBLE);
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
                        Log.d(TAG, "onDataChange: navigating to save to database" + settings.getProduct().getInner_count());
                        navController.navigate(R.id.action_scanBarcodeIn_to_saveToFirebaseIn,bundle);
                        progressBar.setVisibility(View.GONE);
                        waitMessage.setVisibility(View.GONE);
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
                        try {
                            Map<String, Object> objectMap = (Map<String, Object>) single.child(parts[1]).getValue();
                            product.setInner_count(Integer.parseInt(objectMap.get(getString(R.string.field_inner_count)).toString()));
                            product.setId(Integer.parseInt(objectMap.get(getString(R.string.field_id)).toString()));
                            product.setName(objectMap.get(getString(R.string.field_name)).toString());
                            product.setColor(objectMap.get(getString(R.string.field_color)).toString());
                            product.setHeight(Float.parseFloat(objectMap.get(getString(R.string.field_height)).toString()));
                            product.setWidth(Float.parseFloat(objectMap.get(getString(R.string.field_width)).toString()));
                            product.setDepth(Float.parseFloat(parts[3])/product.getInner_count());
                            product.setKg(Float.parseFloat(objectMap.get(getString(R.string.field_kg)).toString()));
                            product.setType(objectMap.get(getString(R.string.field_type)).toString());
                            Log.d(TAG, "initWidgets: image" + objectMap.get(getString(R.string.field_imgUrl)).toString());
                            product.setImgurl(objectMap.get(getString(R.string.field_imgUrl)).toString());
                            product.setUnite(objectMap.get(getString(R.string.field_unite)).toString());
                            product.setTrademark(objectMap.get(getString(R.string.field_trademark)).toString());
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
