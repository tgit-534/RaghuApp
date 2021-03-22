package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;

public class BaseClassUser extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;


    protected void generatePublicMenu() {

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CommonClass cl = new CommonClass();
        mGoogleSignInClient = cl.GoogleStart(this);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menulogout:
                Intent in = getIntent();
                String StrData = in.getStringExtra(getString(R.string.common_auth));
                CommonClass cl = new CommonClass();
                if (StrData.equals(getString(R.string.common_google))) {
                    cl.signOut(this, mGoogleSignInClient);
                }
                break;
            case android.R.id.home:
                Intent homepage = new Intent(this, RedirectFromMain.class);
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
            case R.id.PersonalObjectives:
                Intent homepage3 = new Intent(this, ActivityMainObjectives.class);
                Bundle mBundle3 = new Bundle();
                mBundle3.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage3.putExtras(mBundle3);
                startActivity(homepage3);
                break;

            case R.id.testCode:
                Intent homepage4 = new Intent(this, ActivityYourTeam.class);
                Bundle mBundle4 = new Bundle();
                mBundle4.putString(getString(R.string.common_auth), getString(R.string.common_google));
                homepage4.putExtras(mBundle4);
                startActivity(homepage4);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
