package app.actionnation.actionapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.actionnation.actionapp.Database_Content.Challenges;
import app.actionnation.actionapp.Database_Content.UserCourse;

public class OneChallenge_Expand extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemReselectedListener {

    Button btnShare, btnCertGenerator, btnShareWithfrieds;
    Button btnSubmitright;
    DatabaseReference rootRef, demoRef;
    TextView tx_Oce_ChallengeName, tx_Oce_ChallengeDesc, tx_Oce_ChallengeNumber, tv_oce_ChallengeExpand;
    private final int RESULT_LOAD_IMAGE = 20;
    private String userInputValue = "My Certificate";
    private String imagePath = "";
    private Uri pickedImage;
    private static final int PICK_IMAGE_REQUEST = 234;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private static final String TAG = "234";
    private ValueEventListener challengeListener, challengeListner2;

    String EduId;
    private ImageView mImageView;
    private Uri filePath;
    private GoogleSignInClient mGoogleSignInClient;
    private int countValue;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    FirebaseRecyclerAdapter fbAdapter;
    RecyclerView recyclerView;
    String StrValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_challenge__expand);
        btnSubmitright = findViewById(R.id.btn_oce_SubmitRight);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);

        //bottomNavigationView

        String strDisplayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();



        String strEduname = "";
        if (extras != null) {
            StrValue = extras.getString(getString(R.string.Intent_GetChallengeId));
            strEduname = extras.getString(getString(R.string.Intent_EduName));

        }
        // getSupportActionBar().setTitle(getString(R.string.OCE_ChallengeNo)+ strChallengeNumber);
        getSupportActionBar().setTitle(strEduname);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CommonClass cl = new CommonClass();
        mGoogleSignInClient = cl.GoogleStart(OneChallenge_Expand.this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.rv_ShowChallangesDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(OneChallenge_Expand.this));

        btnSubmitright.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menulogout:

                Intent in = getIntent();
                String StrData = in.getStringExtra(getString(R.string.Intent_Auth));

                CommonClass cl = new CommonClass();

                if (StrData.equals(getString(R.string.common_google)) || StrData.equals(getString(R.string.common_firebase))) {
                    cl.signOut(OneChallenge_Expand.this, mGoogleSignInClient);
                }
                break;
            case android.R.id.home:
                GoToHome();
                break;
            case R.id.itemFriendsInvite:
                InviteToActionNation obj = new InviteToActionNation();
                obj.CreateLink(OneChallenge_Expand.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void GoToHome() {

        Bundle extras = getIntent().getExtras();
        String strEduId = "";
        String strEduName = "";
        if (extras != null) {
            strEduId = extras.getString(getString(R.string.Intent_EduId));
            strEduName = extras.getString(getString(R.string.Intent_EduName));
        }
        Intent homepage = new Intent(OneChallenge_Expand.this, Detail_Challenges.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(getString(R.string.Intent_Auth), getString(R.string.common_google));
        mBundle.putString(getString(R.string.Intent_EduId), strEduId);
        mBundle.putString(getString(R.string.Intent_EduName), strEduName);
        homepage.putExtras(mBundle);
        startActivity(homepage);
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            CommonClass cs = new CommonClass();
            mImageView.setBackground(cs.convertImagePathToDrawable(imagePath));
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
      /*  File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");*/

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + getString(R.string.file_CertificateStorate));

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = getString(R.string.file_certificate_startformat) + timeStamp + getString(R.string.file_imageformat_jpg);
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);

        return mediaFile;
    }


    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(pictureFile); // out is your output file
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
            } else {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            }

        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_oce_SubmitRight) {
            submitChallenge();
        }
    }

    private void shareAboutApp() {
        InviteToActionNation obj = new InviteToActionNation();
        obj.CreateLink(OneChallenge_Expand.this);
    }


    private void CertGenerator() {
        Bundle extras = getIntent().getExtras();
        EduId = extras.getString(getString(R.string.Intent_EduId));
        final int ChallengesNumber = Integer.parseInt(extras.getString(getString(R.string.Intent_ChallengesNumber)));


        if (Integer.parseInt(tx_Oce_ChallengeNumber.getTag().toString()) == ChallengesNumber) {
            if (userInputValue.equals("") || userInputValue.isEmpty()) {
                return;
            }
            CommonClass cs = new CommonClass();

            Bitmap bm = cs.ProcessingBitmap(userInputValue, OneChallenge_Expand.this);

            String pathLink = Environment.getExternalStorageState() + File.separator + "testing.jpg";

            // Check whether this app has write external storage permission or not.
            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(OneChallenge_Expand.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            // If do not grant write external storage permission.
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                // Request user to grant write external storage permission.
                ActivityCompat.requestPermissions(OneChallenge_Expand.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            }

            storeImage(bm);
            Toast.makeText(OneChallenge_Expand.this, getString(R.string.OCE_CheckGalleryForCertificate), Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(OneChallenge_Expand.this, getString(R.string.OCE_ChallengeComplete), Toast.LENGTH_LONG).show();
        }
    }

    private void submitChallenge() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        Bundle extras = getIntent().getExtras();
        EduId = extras.getString(getString(R.string.Intent_EduId));
        final int ChallengesNumber = Integer.parseInt(extras.getString(getString(R.string.Intent_ChallengesNumber)));
        demoRef = FirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_UserCourse)).child(usrId);
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int eduCount = 0;
                if (dataSnapshot.hasChildren()) {
                    // String strDbEduId = dataSnapshot.getKey();
                    String strDbEduId = "";
                    String strScreenEduId = EduId;

                    int DbChallengeNumber = 1;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        DbChallengeNumber = ds.getValue(UserCourse.class).getChallenge_Number();
                        strDbEduId = ds.getKey();

                        if (strScreenEduId.equals(strDbEduId)) //Education Id
                        {
                            eduCount = eduCount + 1;
                            if (DbChallengeNumber >= ChallengesNumber) {
                                return;
                            } else {
                                demoRef.child(strDbEduId).child(getString(R.string.fb_challengeNumber)).setValue(ChallengesNumber);
                            }
                        }
                    }
                }
                if (eduCount == 0) {
                    demoRef = demoRef.child(EduId);
                    UserCourse uc = new UserCourse();
                    uc.setChallenge_Number(ChallengesNumber);
                    demoRef.setValue(uc);
                }

                CommonClass cls = new CommonClass();
                cls.callToast(OneChallenge_Expand.this, getString(R.string.OCE_ToastPressBackButton));
                GoToHome();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_share:
                shareAboutApp();
                break;
            case R.id.navigation_downloadCertificate:
                CommonClass cls = new CommonClass();
                cls.callToast(OneChallenge_Expand.this, getString(R.string.OCE_Toast_CertificateUnableMessage));

                //CertGenerator();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            StrValue = extras.getString(getString(R.string.Intent_GetChallengeId));
        }
        final String strEduName = extras.getString(getString(R.string.Intent_EduName));
        // Query qry = mFirebaseDatabase.getInstance().getReference().child("Challenges").orderByChild("eduid").equalTo(strEduId);
        Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges)).orderByKey().equalTo(StrValue);

        FirebaseRecyclerOptions<Challenges> options = new FirebaseRecyclerOptions.Builder<Challenges>()
                .setQuery(qry, Challenges.class)
                .build();
        fbAdapter = new FirebaseRecyclerAdapter<Challenges, ChallengesViewHolder1>(options) {
            @NonNull
            @Override
            public ChallengesViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // return null;
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onechallenges_viewholder, parent, false);
                ChallengesViewHolder1 viewHolder = new ChallengesViewHolder1(view);
                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull ChallengesViewHolder1 holder, int position, @NonNull Challenges model) {
                String strChallengeId = fbAdapter.getRef(position).getKey();
                if (strChallengeId.equals(StrValue)) {
                    holder.cv_ChallengeNo.setText(getString(R.string.OCE_ChallengeNo) + model.getChallengenumber()+".");
                    holder.cv_ChallengeDesc.setText(model.getChallenge_Desc());
                    holder.cv_ChallengeName.setText(model.getChallenge_Name());
                }
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static class ChallengesViewHolder1 extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView cv_ChallengeNo;
        public final TextView cv_ChallengeName;
        public final TextView cv_ChallengeDesc;
        public Challenges mItem;

        public ChallengesViewHolder1(View view) {
            super(view);
            mView = view;

            cv_ChallengeNo = view.findViewById(R.id.tv_ocv_EduName);
            cv_ChallengeName = view.findViewById(R.id.tv_ocv_ChallengeName);
            cv_ChallengeDesc = view.findViewById(R.id.tv_ocv_ChallengeDesc);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + cv_ChallengeName.getText() + "'";
        }
    }


}