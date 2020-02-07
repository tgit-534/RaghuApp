package app.actionnation.actionapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import app.actionnation.actionapp.Database_Content.Challenges;


public class RedirectFromMain extends AppCompatActivity implements MainListFragment.OnListFragmentInteractionListener {

    //Fragments attached MainListFragment

    androidx.appcompat.widget.Toolbar toolbar;
    androidx.cardview.widget.CardView cv;

    ViewPager viewPager;
    TabLayout tabLayout;
    TabsAdapter tabsAdapter;

    private static final String TAG = "GoogleActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(TAG, "Toobar0:success");
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Toobar1:success");
        setContentView(R.layout.activity_redirect_from_main);
        Log.d(TAG, "Toobar2:success");
        Log.d(TAG, "signInWithCredential:success");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Contact"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(tabsAdapter);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.RFM_Prefs), MODE_PRIVATE);

        boolean isFirstRun = sharedPreferences.getBoolean(getString(R.string.RFM_FirstRun), true);
        if (isFirstRun) {

            CommonClass cls = new CommonClass();

            final AlertDialog mBuilder = new AlertDialog.Builder(RedirectFromMain.this).create();
            View mView = getLayoutInflater().inflate(R.layout.dialog_app, null);
            ImageButton ib = (ImageButton) mView.findViewById(R.id.ib_da_inf);

            mBuilder.setView(mView);
            mBuilder.setCancelable(false);
            mBuilder.show();
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBuilder.dismiss();
                }
            });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.RFM_FirstRun), false);
            editor.commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public void onListFragmentInteraction(Challenges item) {
        // something
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CommonClass cls = new CommonClass();
        return super.onOptionsItemSelected(cls.menuGenerationGeneral(RedirectFromMain.this,item, mGoogleSignInClient));
    }




    private void signOut() {
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        // LoginManager.getInstance().logOut();
        Intent intent = new Intent(RedirectFromMain.this, GoFbLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public static class FindViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;

        public final ImageView mImageView;
        public Challenges mItem;


        public FindViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_name);
            mImageView = (ImageView) view.findViewById(R.id.imgBtn);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }


}


