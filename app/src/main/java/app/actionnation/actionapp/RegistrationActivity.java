package app.actionnation.actionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword, editTextPasswordAgain;
    private Button buttonSignup, buttonLogin;
    private ProgressDialog progressDialog;
    //CallbackManager  callbackManager;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegistrationActivity.this, RedirectFromMain.class));
            finish();
        }

/*        //FB login
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                      //  RedirectToNext("fb");
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });*/

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.et_Email_Registration);
        editTextPassword = (EditText) findViewById(R.id.et_Password_Registration);
        editTextPasswordAgain = (EditText) findViewById(R.id.et_PasswordConfirm_Registration);


        buttonSignup = (Button) findViewById(R.id.buttonSignup_reg);


        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
    }


    private void registerUser() {
        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordAgain = editTextPasswordAgain.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, getString(R.string.Registration_Enter_Email), Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.Registration_Enter_Password), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwordAgain)) {
            Toast.makeText(this, getString(R.string.Registration_ReEnter_Password), Toast.LENGTH_LONG).show();
            return;
        }


        if (password.equals(passwordAgain)) {

            //if the email and password are not empty
            //displaying a progress dialog

            progressDialog.setMessage(getString(R.string.Registration_Registering_Message));
            progressDialog.show();

            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                //display some message here
                                Toast.makeText(RegistrationActivity.this, getString(R.string.Registration_Succesfully_Registered), Toast.LENGTH_LONG).show();
                                CommonClass cls = new CommonClass();
                                cls.redirectToLoginPage(RegistrationActivity.this);
                            } else {
                                try {
                                    throw task.getException();
                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthWeakPasswordException weakPassword) {
                                    Toast.makeText(RegistrationActivity.this, getString(R.string.Registration_WeakPassword), Toast.LENGTH_LONG).show();
                                    // TODO: take your actions!
                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                    Toast.makeText(RegistrationActivity.this, getString(R.string.Registration_Email_Wrong), Toast.LENGTH_LONG).show();

                                    // TODO: Take your action
                                } catch (FirebaseAuthUserCollisionException existEmail) {
                                    Toast.makeText(RegistrationActivity.this, getString(R.string.Registration_Email_Exist), Toast.LENGTH_LONG).show();

                                    // TODO: Take your action
                                } catch (Exception e) {
                                    Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            }
                            progressDialog.dismiss();
                        }
                    });

        } else {
            Toast.makeText(RegistrationActivity.this, getString(R.string.Registration_Pwd_Mismatch), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.buttonSignup_reg) {
            registerUser();
        }

        //calling register method on click
    }

    private void login() {

        String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        //authenticate user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //  progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                // editTextPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(RegistrationActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(RegistrationActivity.this, CertificateGenerator.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

}


