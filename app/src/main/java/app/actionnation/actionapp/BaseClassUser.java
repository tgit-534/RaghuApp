package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.Storage.Constants;

public class BaseClassUser extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;

    FirebaseAuth mAuth;


    protected void generatePublicMenu() {

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CommonClass cl = new CommonClass();
        mGoogleSignInClient = cl.GoogleStart(this);
        int i = 0;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return true;
    }


    protected String fetchUserId() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        return usrId;
    }

    protected ArrayList<String> fetchUserArray() {
        ArrayList<String> strUser = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
            strUser.add(usrId);
            strUser.add(fbUser.getDisplayName());
            strUser.add(fbUser.getEmail());
        }
        return strUser;
    }


    // 0 day 1 year
    protected int fetchDate(int i) {
        Calendar c = Calendar.getInstance();

        int returnvalue = 0;
        if (i == 0)
            returnvalue = c.get(Calendar.DAY_OF_YEAR);
        else if (i == 1) {
            returnvalue = c.get(Calendar.YEAR);
        }
        return returnvalue;
    }

    private static final String TAG = "BaseClassUser: Logs:";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menulogout:
                Log.d(TAG, "1");
                Intent in = getIntent();
                String StrData = in.getStringExtra(getString(R.string.common_auth));
                Log.d(TAG, StrData);
                CommonClass cl = new CommonClass();
                if (StrData.equals(getString(R.string.common_google))) {
                    cl.signOut(this, mGoogleSignInClient);
                    finish();
                    Intent intent = new Intent(this, GoFbLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            case android.R.id.home:
                String className = this.getClass().getName();
                Intent homepage = null;
                switch (className) {
                    case Constants.ClassName_HabitInside:
                        homepage = new Intent(this, HabitTraking.class);
                        break;
                    case Constants.ClassName_RespectYourWorkWin:
                        homepage = new Intent(this, ActivityIntegrityMain.class);
                        break;
                    case Constants.ClassName_WordWin:
                        homepage = new Intent(this, ActivityIntegrityMain.class);
                        break;
                    default:
                        homepage = new Intent(this, ActivityMainObjectives.class);
                        break;
                }
                Bundle mBundle = new Bundle();
                mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage.putExtras(mBundle);
                startActivity(homepage);
                break;
            case R.id.itemFriendsInvite:
                InviteToActionNation obj = new InviteToActionNation();
                obj.CreateLink(this);
                break;
            case R.id.ActionNationPhilosophy:
                Intent homepage1 = new Intent(this, displaydata.class);
                Bundle mBundle1 = new Bundle();
                mBundle1.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage1.putExtras(mBundle1);
                startActivity(homepage1);
                break;
            case R.id.PersonalDetails:
                Intent homepage2 = new Intent(this, PersondetailsActivity.class);
                Bundle mBundle2 = new Bundle();
                mBundle2.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage2.putExtras(mBundle2);
                startActivity(homepage2);
                break;

            case R.id.Support:

                ArrayList<String> arrayListUser = fetchUserArray();
                Intent homepage5 = new Intent(this, ActivitySupport.class);
                Bundle mBundle5 = new Bundle();
                mBundle5.putString(getString(R.string.common_auth), getString(R.string.common_google));
                mBundle5.putString(getString(R.string.common_name), arrayListUser.get(1));
                mBundle5.putString(getString(R.string.common_enterEmail), arrayListUser.get(2));


                homepage5.putExtras(mBundle5);
                startActivity(homepage5);
                break;

            case R.id.PersonalObjectives:
                Intent homepage3 = new Intent(this, ActivityMainObjectives.class);
                Bundle mBundle3 = new Bundle();
                mBundle3.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage3.putExtras(mBundle3);
                startActivity(homepage3);
                break;

            case R.id.testCode:
                Intent homepage4 = new Intent(this, ActivitySupport.class);
                Bundle mBundle4 = new Bundle();
                mBundle4.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage4.putExtras(mBundle4);
                startActivity(homepage4);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void makeSnackBar(View cl)
    {

        Snackbar snackbar = Snackbar
                .make(cl, "Well Done!", Snackbar.LENGTH_LONG);

        snackbar.show();
    }


}
