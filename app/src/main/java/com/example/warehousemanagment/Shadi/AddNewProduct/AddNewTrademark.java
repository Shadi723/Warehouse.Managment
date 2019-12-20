package com.example.warehousemanagment.Shadi.AddNewProduct;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.warehousemanagment.R;
import com.example.warehousemanagment.Shadi.Models.Product;
import com.example.warehousemanagment.Shadi.Models.Trademark;
import com.example.warehousemanagment.Shadi.Models.Type;
import com.example.warehousemanagment.Shadi.Utils.UniversalImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewTrademark extends Fragment implements View.OnClickListener {


    EditText  trademark_name;
    TextView trademark_id;
    Button save;
    ImageView img_load;
    Spinner types_list;
    NavController navController;
    long count;

    private static final String TAG = "AddNewTrademark";
    //firebase

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;

    public AddNewTrademark() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_new_trademark_fragment_layout,container,false);
        trademark_id = view.findViewById(R.id.trademark_id);
        trademark_name = view.findViewById(R.id.trademark_name);
        types_list = view.findViewById(R.id.types_list);
        save = view.findViewById(R.id.save_product_new);
        navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        save.setOnClickListener(this);
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
        getTypes();
        getTrademarkId();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getTrademarkId(){
        try{
            Query query = FirebaseDatabase.getInstance().getReference().child(getString(R.string.company_name))
                    .child(getString(R.string.field_trademark));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    count = dataSnapshot.getChildrenCount();
                    count++;
                    trademark_id.setText(String.valueOf(count));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }catch (NullPointerException e){
            Log.e(TAG, "onViewCreated: " + e.toString() );
        }
    }
    private void getTypes(){
        Query query1 = FirebaseDatabase.getInstance().getReference().child(getString(R.string.company_name))
                .child(getString(R.string.field_types));
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> allTypes = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String types;
                    Log.d(TAG, "onDataChange: "+ds.getValue(Trademark.class).getType());
                        types = ds.getValue(Type.class).getName();
                        allTypes.add(types);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_simple_layout,allTypes);
                types_list.setAdapter(adapter);
                types_list.setSelection(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v == save) {
            if (trademark_name.getText() != null) {
                Trademark trademark = new Trademark();
                trademark.setId(Integer.parseInt(trademark_id.getText().toString()));
                trademark.setName(trademark_name.getText().toString());
                trademark.setType(types_list.getSelectedItem().toString());
                addTrademark(trademark);
                    navController.navigate(R.id.action_addNewTrademark_to_mainFragment);
            } else {
                showErrorMesssage();
            }
        }
    }

    private void showErrorMesssage() {
        if(TextUtils.isEmpty(trademark_id.getText()))
            trademark_name.setError(getString(R.string.fill_product_id));
    }

    private void addTrademark(Trademark trademark) {
        mRef.child(getString(R.string.company_name))
                .child(getString(R.string.field_trademark))
                .child(String.valueOf(count))
                .setValue(trademark);

    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
}
