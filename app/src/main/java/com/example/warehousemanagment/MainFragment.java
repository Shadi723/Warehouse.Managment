package com.example.warehousemanagment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainFragment extends Fragment implements View.OnClickListener {
    NavController navController;
    Button addNewProduct, sellProduct, productsListBtn;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClass;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addNewProduct = view.findViewById(R.id.ScanBarcodeIn);
        sellProduct = view.findViewById(R.id.ScanBarcodeOut);
        productsListBtn = view.findViewById(R.id.btn_products_list);

        checkPermission();
        addNewProduct.setOnClickListener(this);
        sellProduct.setOnClickListener(this);
        productsListBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == addNewProduct){
            navController.navigate(R.id.action_mainFragment_to_scanBarcodeIn);
        }
        if(v == sellProduct){
            navController.navigate(R.id.action_mainFragment_to_scanBarcodeOut);
        }
        if(v == productsListBtn){
            navController.navigate(R.id.action_mainFragment_to_productsFragment);
        }
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClass != null) {
                        Intent intent = new Intent(getActivity(), mClass);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getActivity(), "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
