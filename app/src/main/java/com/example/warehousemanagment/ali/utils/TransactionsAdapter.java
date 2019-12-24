package com.example.warehousemanagment.ali.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.ali.models.Transaction;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private List<Transaction> transactions;

    public TransactionsAdapter(List<Transaction> transactions){
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.transactionDate.setText(transactions.get(position).getTransactionDate());
        holder.transactionCode.setText(transactions.get(position).getTransactionCode());
        holder.transactionDesc.setText(transactions.get(position).getTransactionDesc());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView transactionDate;
        TextView transactionCode;
        TextView transactionDesc;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            transactionDate = itemView.findViewById(R.id.txtView_transaction_date);
            transactionCode = itemView.findViewById(R.id.txtView_transaction_code);
            transactionDesc = itemView.findViewById(R.id.txtView_transaction_desc);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
