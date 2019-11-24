package com.example.warehousemanagment.Shadi.Dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.warehousemanagment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoToScanDialog extends DialogFragment {

    private static final String TAG = "NewProductDialog";
    Button yes, no;
    private NavController navController;

    public GoToScanDialog() {
        // Required empty public constructor
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        navController = NavHostFragment.findNavController(this);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle(getString(R.string.navigate_to_scan))
                // Add action buttons
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // navigate to add new product to list
                        navController.navigate(R.id.action_goToScanDialog_to_scanBarcodeIn);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        navController.navigate(R.id.action_goToScanDialog_to_mainFragment);
                    }
                });
        return builder.create();
    }
}
