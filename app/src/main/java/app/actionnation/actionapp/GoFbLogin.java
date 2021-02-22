package app.actionnation.actionapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class GoFbLogin extends BaseActivity
        implements View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    public FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private Button btnAdmin;
    private ProgressDialog progressDialog;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_fb_login);


        //  mDetailTextView = findViewById(R.id.detail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        // Button listeners
        SignInButton btnSignIn = findViewById(R.id.signInButton);
        btnSignIn.setSize(SignInButton.SIZE_WIDE);
        btnSignIn.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FirebaseApp.initializeApp(GoFbLogin.this);
        // [START initialize_au
        // th]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        //Normal way of getting data
        //initializing views
        Intent mIntent = getIntent();
        String previousActivity = mIntent.getStringExtra(getString(R.string.common_key_activity_name));


        if (mAuth.getCurrentUser() != null) {
            if (previousActivity != null) {
                if (previousActivity.equals(getString(R.string.common_key_fp))) {
                    if (mAuth.getCurrentUser() != null) {
                    }
                }
            } else {
                RedirectToNext(getString(R.string.common_firebase));
            }
        }
        btnRegister = (Button) findViewById(R.id.btn_register);

        progressDialog = new ProgressDialog(this);

        btnAdmin = findViewById(R.id.btnAdmin);
        //attaching listener to button
        btnAdmin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Log.d(TAG, "signInWithCredential:success1");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), getString(R.string.go_fb_login_autheticationfailed), Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            RedirectToNext(getString(R.string.common_google));
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signInButton) {
            signIn();
        } else if (i == R.id.btn_register) {
            Intent intent = new Intent(GoFbLogin.this, RegistrationActivity.class);
            startActivity(intent);
        } else if (i == R.id.btnAdmin) {
            login();
        }
    }


    private void RedirectToNext(String str) {
       // Intent homepage = new Intent(GoFbLogin.this, RedirectFromMain.class);
        Intent homepage = new Intent(GoFbLogin.this, ActivityMainObjectives.class);

        Bundle mBundle = new Bundle();
        mBundle.putString(getString(R.string.common_auth), str);
        homepage.putExtras(mBundle);
        startActivity(homepage);
        finish();
    }

    private void login() {

        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), getString(R.string.go_fb_login_enterEmail), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), getString(R.string.go_fb_login_enterPassword), Toast.LENGTH_SHORT).show();
            return;
        }

        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(GoFbLogin.this, new OnCompleteListener<AuthResult>() {
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
                                Toast.makeText(GoFbLogin.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {

                            if (email.equals("raghu123@gmail.com") && password.equals(getString(R.string.common_firebase))) {
                                Intent intent = new Intent(GoFbLogin.this, DbMainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                RedirectToNext(getString(R.string.common_firebase));
                            }
                        }
                    }
                });
    }


    public void ForgotPassword(View view) {
        Intent intent = new Intent(GoFbLogin.this, forgotpassword.class);
        startActivity(intent);

    }
}





