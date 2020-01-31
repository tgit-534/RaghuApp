package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.actionnation.actionapp.Database_Content.Category;
import app.actionnation.actionapp.admin.AdminCommonClass;

public class DbMainActivity extends AppCompatActivity {
    private static final String TAG = "DatabaseActivity";
    EditText name, loc, desig;
    Button saveBtn, detailsBtn, redirectBack,educationBtn, challengesBtn, btnCertGenerator;
    Intent intent;
    DatabaseReference databaseActionUniversity;
    public static final String Firebase_Server_URL = "https://tgit-4c9c3.firebaseio.com/";
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_main);

       // = FirebaseDatabase.getInstance().getReference().child("Category");

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        databaseActionUniversity = FirebaseDatabase.getInstance().getReference().child("Category");

        name = (EditText)findViewById(R.id.txtName);

        saveBtn = (Button)findViewById(R.id.btnSave);

        category = new Category();

        Log.d(TAG, "2:");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "1:");
                category.setStatus(1);
                category.setCategoryName(name.getText().toString());

                databaseActionUniversity.push().setValue(category);
                Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void Add_Category()
    {
              String edtCategoryName = name.getText().toString().trim();
              String edtCategoryStatus;

              if(!TextUtils.isEmpty(edtCategoryName))
              {
                String id=  databaseActionUniversity.push().getKey();
                Category ct = new Category(id,edtCategoryName,1);

                databaseActionUniversity.child(id).setValue(ct);
                Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();


              }else {
                  Toast.makeText(getApplicationContext(), "Details not Inserted Successfully", Toast.LENGTH_SHORT).show();

              }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menuadmin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdminCommonClass acc = new AdminCommonClass();
        return super.onOptionsItemSelected(acc.AdminMenuSelected(item,DbMainActivity.this));
    }







}