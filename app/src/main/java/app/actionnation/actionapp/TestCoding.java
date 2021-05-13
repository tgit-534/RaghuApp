package app.actionnation.actionapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.data.DbHelperClass;
import app.actionnation.actionapp.data.DbHelperClass2;

public class TestCoding extends AppCompatActivity {

    Button btnCreateLink, btnShareLink;
    private String[] pickerVals;
    NumberPicker picker1;

    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    ArrayList<String> strAttPattern = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_coding);
        btnCreateLink = findViewById(R.id.btn_tc_CreateLink);
        btnShareLink = findViewById(R.id.btn_tc_ShareLink);

        /*picker1 = findViewById(R.id.numberpicker_main_picker);
        picker1.setMaxValue(4);
        picker1.setMinValue(0);
        pickerVals  = new String[] {"dog", "cat", "lizard", "turtle", "axolotl"};
        picker1.setDisplayedValues(pickerVals);

        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = picker1.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
            }
        });*/


        recyclerView = findViewById(R.id.recyclerCaptainDialogTest);

        mAuth = FirebaseAuth.getInstance();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        fetchChooseGame();
/*
        btnCreateLink.setOnClickListener(new View.OnClickListener().OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateLink();
            }
        });*/

        btnCreateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                recyclerView.setLayoutManager(new LinearLayoutManager(TestCoding.this));
                recyclerView.setHasFixedSize(false);

                fetchChooseGame();

              //  CreateLink();

            }
        });
    }


    private void fetchChooseGame() {
        FirebaseFirestore db;
        FirebaseAuth mAuth;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();

        Calendar cNew = Calendar.getInstance();

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        com.google.firebase.firestore.Query query1 = db.collection(getString(R.string.fs_TeamGame)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);
        //whereLessThan(getString(R.string.fs_TeamGame_StartDate), cNew.getTimeInMillis());

        DbHelperClass2 dbh = new DbHelperClass2();
        adapter = dbh.GetFireStoreAdapterSelectGame(query1);

        recyclerView.setAdapter(adapter);




    }

    private void fetchDis() {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_PersonalDistraction)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);

        DbHelperClass dbh = new DbHelperClass();

        strAttPattern.add("ljlj");

        adapter = dbh.GetFireStoreAdapterDistraction(adapter, getString(R.string.fs_PersonalDistraction), query1, TestCoding.this, strAttPattern, usrId);


        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }




        public void CreateLink() {
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.thegreatindiantreasure.com/"))
                .setDomainUriPrefix("https://actionnation.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        shortenLongLink(dynamicLink.getUri());
        Log.e("TestCoding", dynamicLink.getUri().toString());
    }

    public void shortenLongLink(Uri shortUri) {
        // [START shorten_long_link]
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(shortUri)
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {

                            // Short link created

                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.e("Short Link TestCoding", shortLink.toString());

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);


                        } else {

                            // Error

                            /*

                            Android Studio Command Type	Mac OS X Shortcuts
Reformat code	OPTION + CMD + L
Show selected API documentation	F1 / FUNCTION + F1
Generate Source Code	CMD + N
Jump to source	CMD + DOWN ARROW KEY
Delete Line	CMD + BACKSPACE
Search by symbol name	OPTION + CMD + O
Build	CMD + F9
Build and Run	CTRL + R
Toggle Project Sidebar Visibility	CMD + 1
Open Class	CMD + O
Open File ( including resources)	CMD + SHIFT + O
Recent Files Opened	CMD + E
Recently edited files	CMD + SHIFT + E
Previous Next/Previous Error	F2 / FUNCTION F2
Last Edited Location	CMD + SHIFT + BACKSPACE
Last Location	CMD + [ and CMD + ]
Go to Declaration	CMD + B
Go to Super	CMD + Y
Next Word Navigation	ALT + LEFT/RIGHT ARROW KEY
Find	CMD + F
Find in Path	SHIFT + CMD + F
Refactor Class, Method	CTRL + T




                             */


                            // ...

                        }
                    }
                });
    }

}
