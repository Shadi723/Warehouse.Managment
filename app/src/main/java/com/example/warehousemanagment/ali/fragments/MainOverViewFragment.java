package com.example.warehousemanagment.ali.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.ali.models.Transaction;
import com.example.warehousemanagment.ali.utils.TransactionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainOverViewFragment extends Fragment {

    private FragmentActivity fragmentActivity;

    private ConstraintLayout expandableTransLayout;
    //private CardView expandableTransLayout;
    private ImageButton transButton;
    private CardView cardView;

    private RecyclerView transactionListView;
    private List<Transaction> transactions;
    private TransactionsAdapter transactionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_over_view, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentActivity = getActivity();

        initViews(view);
        //setTestTransList();

        insertFakeTransactions();
        setRecyclerView();
    }

    private void initViews(View view){

        transactionListView = view.findViewById(R.id.recyclerView_transactions);
        transactionListView.setHasFixedSize(true);

        transactions = new ArrayList<>();
        transactionsAdapter = new TransactionsAdapter(transactions);

        expandableTransLayout = view.findViewById(R.id.expandableView);
        cardView = view.findViewById(R.id.cardView);

        transButton = view.findViewById(R.id.arrowBtn);
        transButton.setBackgroundColor(Color.TRANSPARENT);

        //set the initial icon of the arrow either up or down
        if (expandableTransLayout.getVisibility() == View.GONE){

            transButton.setBackgroundResource(R.drawable.round_arrow_drop_up_24);
            expandableTransLayout.setVisibility(View.VISIBLE);
        }
        else {
            transButton.setBackgroundResource(R.drawable.round_arrow_drop_down_24);
            expandableTransLayout.setVisibility(View.GONE);
        }
        transButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                if (expandableTransLayout.getVisibility() == View.GONE){

                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    transButton.setBackgroundResource(R.drawable.round_arrow_drop_up_24);
                    expandableTransLayout.setVisibility(View.VISIBLE);
                }
                else {
                    //TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    transButton.setBackgroundResource(R.drawable.round_arrow_drop_down_24);
                    expandableTransLayout.setVisibility(View.GONE);
                }
            }
        });
    }


    private void setRecyclerView(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(fragmentActivity, RecyclerView.VERTICAL, false);
        transactionListView.setLayoutManager(layoutManager);
        transactionListView.setItemAnimator(new DefaultItemAnimator());
        transactionsAdapter = new TransactionsAdapter(transactions);
        transactionListView.setAdapter(transactionsAdapter);
    }

    private void insertFakeTransactions(){

        for (int i = 0; i < 10; i++){

            Transaction transaction = new Transaction(i, "15/2/2017", "item " + i, "this is the description of the item " + i);

            transactions.add(transaction);
        }
    }
}