package com.example.warehousemanagment.Shadi.Dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.warehousemanagment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewProductDialog extends DialogFragment {

    private static final String TAG = "NewProductDialog";
    Button yes, no;
    private NavController navController;

    public NewProductDialog() {
        // Required empty public constructor
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: dialog");
        final Bundle bundle = new Bundle();
        String code = getArguments().getString("ProductCode");
        bundle.putString("ProductCode",code);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        navController = NavHostFragment.findNavController(this);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle(getString(R.string.add_new_product_dialog))
                // Add action buttons
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // navigate to add new product to list
                        navController.navigate(R.id.action_newProductDialog_to_addNewProduct,bundle);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        navController.navigate(R.id.action_newProductDialog_to_mainFragment);
                    }
                });
        return builder.create();
    }
}
