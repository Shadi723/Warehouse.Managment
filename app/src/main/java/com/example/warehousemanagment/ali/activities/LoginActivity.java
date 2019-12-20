package com.example.warehousemanagment.ali.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehousemanagment.MainActivity;
import com.example.warehousemanagment.R;
import com.example.warehousemanagment.ali.Dialogs.ResetPasswordDialog;
import com.example.warehousemanagment.ali.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    private long backPressedTime;
    private Toast backToast;

    private EditText emailEditText;
    private EditText passwordEditText;

    private TextView forgotPasswordTextView;

    private Button loginButton;
    private Button buyButton;

    private FirebaseUser currentUser;
    private FirebaseAuth auth = null;
    private FirebaseDatabase database = null;
    private DatabaseReference databaseReference = null;

    private ArrayList<User> allUsers = null;
    private String emailAddress = null;

    private ResetPasswordDialog passwordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        checkUserSignedIn();
    }

    @Override
    public void onBackPressed() {

        //if the time between the first and second click bigger than 2 seconds ignore this method
        if (backPressedTime + 2000 > System.currentTimeMillis()) {

            backToast.cancel(); //ensure that the Toast message will be canceled after closing the app
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(getBaseContext(), getString(R.string.toast_press_back_again_to_exit), Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = auth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    allUsers.add(snap.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        if(view == loginButton){

            if(!confirmedInput()){

                Toast.makeText(this, getString(R.string.toast_check_your_data), Toast.LENGTH_SHORT).show();
                return;
            }
            openNewSession();
        }

        if(view == buyButton){

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        if (view == forgotPasswordTextView){

            passwordDialog = new ResetPasswordDialog(this);
            passwordDialog.show();
        }

    }

    private void initViews(){

        emailEditText = findViewById(R.id.editTxt_username);
        passwordEditText = findViewById(R.id.editTxt_password);

        //forgotPasswordTextView = findViewById(R.id.txtV)
        loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);

        forgotPasswordTextView = findViewById(R.id.txtView_forgot_password);
        forgotPasswordTextView.setOnClickListener(this);

        buyButton = findViewById(R.id.btn_buy);
        buyButton.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("NurIsPvc").child("Users");
        allUsers = new ArrayList<>();
    }

    private void checkUserSignedIn(){


        if(currentUser != null) {

            Intent homeIntent = new Intent(this, MainActivity.class);
            Toast.makeText(this, currentUser.toString(), Toast.LENGTH_SHORT).show();
            finish();
            startActivity(homeIntent);
        }
        else{
            //test purpose
            Toast.makeText(getApplicationContext(), "user not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void openNewSession(){

        String password = passwordEditText.getText().toString();
        auth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(LoginActivity.this, "Signing In Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    finish();
                    startActivity(intent);
                }
                else
                    Toast.makeText(LoginActivity.this, "Something Happened Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail() {

        String emailInput = emailEditText.getText().toString().trim();

        if (emailInput.isEmpty()) {

            emailEditText.setError(getString(R.string.toast_empty_filed_wrong));
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailEditText.setError(getString(R.string.toast_wrong_email_syntax));
            return false;
        }
        else {
            emailAddress = emailEditText.getText().toString();
            emailEditText.setError(null);
            return true;
        }
    }

    private boolean isValidPassword() {

        String passwordInput = passwordEditText.getText().toString();

        if (passwordInput.isEmpty()) {

            passwordEditText.setError(getString(R.string.toast_empty_filed_wrong));
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){

            passwordEditText.setError(getString(R.string.toast_enter_valid_password));
            return false;
        }
        else {
            passwordEditText.setError(null);
            passwordEditText.getText().toString().trim();
            return true;
        }
    }

    public boolean isSignedByUsername() {

        String inputEmail = emailEditText.getText().toString();

        for (User user:allUsers) {

            String name = user.getUserName();

            if(inputEmail.equals(name)) {

                emailAddress = user.getEmail();
                return true;
            }
        }
        return false;
    }

    public boolean confirmedInput() {

        return (isValidEmail() || isSignedByUsername()) && isValidPassword();
    }
}
