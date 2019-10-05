package com.example.warehousemanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void buttonAction(View view) {

        Intent intent;

        if (view.getId() == R.id.btn_add_record){

            intent = new Intent(this, ProductsActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_delete_record){
            return;
        }
    }
}
