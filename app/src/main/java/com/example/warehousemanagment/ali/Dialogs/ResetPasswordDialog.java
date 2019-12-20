package com.example.warehousemanagment.ali.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.warehousemanagment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordDialog {

    private TextView dialogMessageTxtView;
    private EditText emailEditText;
    private Button sendEmailBtn;

    private Dialog mDialog;

    private Context context;

    private FirebaseAuth auth;

    public ResetPasswordDialog(Context context) {

        this.context = context;
        mDialog = new Dialog(this.context);

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Window window = mDialog.getWindow();

        if (window != null) {

            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        mDialog.setContentView(R.layout.dialog_reset_password);

        dialogMessageTxtView = mDialog.findViewById(R.id.txtView_dialog_message);
        emailEditText = mDialog.findViewById(R.id.editTxt_email_resend);
        sendEmailBtn = mDialog.findViewById(R.id.btn_send_reset_email);

        setOnPositiveButtonClicked();
    }

    public void show(){

        if (mDialog != null){

            mDialog.show();
        }
    }

    public void dismiss() {

        if (mDialog != null) {

            mDialog.dismiss();
            mDialog = null;
        }
    }

    private boolean isValidEmail() {

        String emailInput = emailEditText.getText().toString().trim();

        if (emailInput.isEmpty()) {

            emailEditText.setError(context.getString(R.string.toast_empty_filed_wrong));
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {

            emailEditText.setError(context.getString(R.string.toast_wrong_email_syntax));
            return false;
        }
        else {
            emailEditText.setError(null);
            return true;
        }
    }

    private void showSuccessEmailSentMessage(){

        sendEmailBtn.setEnabled(false);
        emailEditText.setVisibility(View.INVISIBLE);
        dialogMessageTxtView.setVisibility(View.VISIBLE);
    }

    private void setOnPositiveButtonClicked() {

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidEmail()) {

                    auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(emailEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                //mDialog.dismiss();
                                showSuccessEmailSentMessage();
                                Toast.makeText(context, context.getString(R.string.toast_reset_email_success), Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
