package app.actionnation.actionapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends BaseActivity {
    Button btnForgotSendEmail;
    EditText etForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        btnForgotSendEmail = findViewById(R.id.btnForgetPassword);
        etForgetPassword = findViewById(R.id.editTextEmail_Forgot);


        btnForgotSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etForgetPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(forgotpassword.this, getString(R.string.common_Pls_Enter_Email), Toast.LENGTH_LONG).show();
                    return;
                } else {
                    resetUserPassword(email);
                }

            }
        });


    }

    public void resetUserPassword(String email) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(forgotpassword.this);
        progressDialog.setMessage(getString(R.string.common_verifying));
        progressDialog.show();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), getString(R.string.forgotPwd_email_msg),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.forgotPwd_email_doest_exist), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void RedirectLoginPage(View view) {
        CommonClass cls = new CommonClass();
        cls.redirectToLoginPage(forgotpassword.this);

    }
}