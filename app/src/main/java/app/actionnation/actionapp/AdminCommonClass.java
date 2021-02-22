package app.actionnation.actionapp;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminCommonClass extends AppCompatActivity {


    protected void generatePublicMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        setSupportActionBar(toolbar);
    }

    protected MenuItem AdminMenuSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.ad_category:
                Intent homepage = new Intent(getApplicationContext(), DbMainActivity.class);
                startActivity(homepage);
                break;
            case R.id.ad_Education:
                homepage = new Intent(getApplicationContext(), Admin_Education.class);
                startActivity(homepage);
                break;
            case R.id.ad_Challenges:
                homepage = new Intent(getApplicationContext(), Admin_Challenges.class);
                startActivity(homepage);
                break;
            case R.id.ad_Leadership:
                homepage = new Intent(getApplicationContext(), adminleadership.class);
                startActivity(homepage);
                break;
            case R.id.ad_setImage:
                homepage = new Intent(getApplicationContext(), CertificateGenerator.class);
                startActivity(homepage);
                break;
            case R.id.ad_editAdmin:
                homepage = new Intent(getApplicationContext(), DetailsActivity.class);
                startActivity(homepage);
                break;
            case R.id.ad_questionnare:
                homepage = new Intent(getApplicationContext(), AdminActionQuestionnare.class);
                startActivity(homepage);
                break;
        }
        return item;
    }

}
