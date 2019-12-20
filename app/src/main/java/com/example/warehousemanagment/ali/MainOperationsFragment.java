package com.example.warehousemanagment.ali;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.warehousemanagment.R;

import java.security.Principal;

public class MainOperationsFragment extends Fragment implements View.OnClickListener {

    private CardView addProductCardView;
    private CardView scanProductInCardView;
    private CardView scanProductOutCardView;
    private CardView addTrademarkCardView;
    private CardView editProductCardView;

    private NavController navController;

    private FragmentActivity fragmentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_operations, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
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

        fragmentActivity = getActivity();
        initViews(view);
        setViewListener();

    }

    @Override
    public void onClick(View view) {

        if (view == addProductCardView) {

            navController.navigate(R.id.action_mainFragment_to_addNewProduct);
        }

        if (view == scanProductInCardView) {

            navController.navigate(R.id.action_mainFragment_to_scanBarcodeIn);
        }

        if (view == scanProductOutCardView) {

            navController.navigate(R.id.action_mainFragment_to_scanBarcodeOut);
        }

        if (view == addTrademarkCardView) {

            navController.navigate(R.id.action_mainFragment_to_addNewTrademark);
        }

        if (view == editProductCardView) {

            navController.navigate(R.id.action_mainFragment_to_searchForProduct);
        }
    }

    private void initViews(View view) {

        navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);

        addProductCardView = view.findViewById(R.id.cardView_add_product);
        scanProductInCardView = view.findViewById(R.id.cardView_scan_product_in);
        scanProductOutCardView = view.findViewById(R.id.cardView_scan_product_out);
        addTrademarkCardView = view.findViewById(R.id.cardView_add_new_trademark);
        editProductCardView = view.findViewById(R.id.cardView_edit_product);
    }

    private void setViewListener() {

        addTrademarkCardView.setOnClickListener(this);
        scanProductInCardView.setOnClickListener(this);
        scanProductOutCardView.setOnClickListener(this);
        addTrademarkCardView.setOnClickListener(this);
        editProductCardView.setOnClickListener(this);
    }


}
