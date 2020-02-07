package app.actionnation.actionapp.admin;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import app.actionnation.actionapp.AdminActionQuestionnare;
import app.actionnation.actionapp.Admin_Challenges;
import app.actionnation.actionapp.Admin_Education;
import app.actionnation.actionapp.CertificateGenerator;
import app.actionnation.actionapp.DbMainActivity;
import app.actionnation.actionapp.DetailsActivity;
import app.actionnation.actionapp.R;
import app.actionnation.actionapp.adminleadership;

public class AdminCommonClass {

    public MenuItem AdminMenuSelected(MenuItem item, Context ct) {
        int id = item.getItemId();

        switch (id) {
            case R.id.ad_category:
                Intent homepage = new Intent(ct, DbMainActivity.class);
                ct.startActivity(homepage);
                break;
            case R.id.ad_Education:
                homepage = new Intent(ct, Admin_Education.class);
                ct.startActivity(homepage);
                break;
            case R.id.ad_Challenges:
                homepage = new Intent(ct, Admin_Challenges.class);
                ct.startActivity(homepage);
                break;
            case R.id.ad_Leadership:
                homepage = new Intent(ct, adminleadership.class);
                ct.startActivity(homepage);
                break;
            case R.id.ad_setImage:
                homepage = new Intent(ct, CertificateGenerator.class);
                ct.startActivity(homepage);
                break;
            case R.id.ad_editAdmin:
                homepage = new Intent(ct, DetailsActivity.class);
                ct.startActivity(homepage);
                break;
            case R.id.ad_questionnare:
                homepage = new Intent(ct, AdminActionQuestionnare.class);
                ct.startActivity(homepage);
                break;
        }
        return item;
    }


}
