package com.example.warehousemanagment.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.shadi.Models.Product;

import java.util.List;

public class ProductsAdapter extends ArrayAdapter<Product> {

    public ProductsAdapter(Context context, List<Product> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        Product currentItem = getItem(position);

        if (listItemView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_product, parent, false);
        }

        /*TextView productNameTxtView = listItemView.findViewById(R.id.txtView_item_product_name);
        TextView productDescTxtView = listItemView.findViewById(R.id.txtView_item_product_desc);
        TextView productQuantityTxtView = listItemView.findViewById(R.id.txtView_item_product_quantity);
        TextView productWeightTxtView = listItemView.findViewById(R.id.txtView_item_product_weight);

        if (currentItem != null){

            productNameTxtView.setText(currentItem.getName());
            productDescTxtView.setText(currentItem.getDescription());
            productQuantityTxtView.setText(currentItem.getQuantity());
            productWeightTxtView.setText(currentItem.getWeight());
        }*/

        return listItemView;
    }
}
