package app.actionnation.actionapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import app.actionnation.actionapp.Database_Content.NationalLeadershipWeek;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.Upload;

public class nlw extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemReselectedListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    GoogleSignInClient mGoogleSignInClient;
    private final int RESULT_LOAD_IMAGE = 20;
    private String userInputValue = "";
    private String imagePath = "";
    private Uri pickedImage;

    //uri to store file
    private Uri filePath;

    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mReferenceActionUniversity;


    ImageButton ibtnInviteFriends, ibtnSelect;
    Button ibtnUpload;
    ImageView imgView;

    EditText etStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nlw);
        activityInitializations();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ibtn_nlw_ChooseImage) {
            showFileChooser();
        } else if (i == R.id.ibtn_nlw_Upload) {
            uploadFile();
        }
    }


    private void insertData(String strUrl, String getUid) {
        mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child("NationalLeadershipWeek");

        NationalLeadershipWeek nlw = new NationalLeadershipWeek();
        nlw.setStatus(1);
        nlw.setLeadershipStory(etStory.getText().toString());
        nlw.setLeadershipTaskPic(strUrl);
        nlw.setUserId(getUid);
        mReferenceActionUniversity.push().setValue(nlw);

/*
        GlideApp.with(getApplicationContext())
                .load(strUrl)
                .into(imgView);
*/


    }

    ////Selecting an image
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //Activity Initializations
    private void activityInitializations() {
             ibtnSelect = findViewById(R.id.ibtn_nlw_ChooseImage);
        ibtnUpload = findViewById(R.id.ibtn_nlw_Upload);
        etStory = findViewById(R.id.etxt_nlw_LeadershipAct);
        imgView = findViewById(R.id.iv_nlw_Image);

        ibtnSelect.setOnClickListener(this);
        ibtnUpload.setOnClickListener(this);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        CommonClass cl = new CommonClass();
        Bundle extras = getIntent().getExtras();
        String strEduName = "";
        if (extras != null) {

            strEduName = extras.getString(getString(R.string.Intent_EduName));
        }
        getSupportActionBar().setTitle(strEduName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation_single);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);


        mGoogleSignInClient = cl.GoogleStart(nlw.this);
        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_NLW);

        mReferenceActionUniversity = FirebaseDatabase.getInstance().getReference().child("Education");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
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
                String StrData = in.getStringExtra("auth");
                CommonClass cl = new CommonClass();
                if (StrData.equals("fb")) {
                    cl.logoutFromFacebook(nlw.this);
                } else if (StrData.equals("google")) {
                    cl.signOut(nlw.this, mGoogleSignInClient);
                }
                break;
            case android.R.id.home:
                Intent homepage = new Intent(nlw.this, RedirectFromMain.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("auth", "google");
                homepage.putExtras(mBundle);
                startActivity(homepage);
                break;
            case R.id.itemFriendsInvite:
                InviteToActionNation obj = new InviteToActionNation();
                obj.CreateLink(nlw.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            final StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_NLW + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri downloadUrl = uri;
                                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                    Upload upload = new Upload(user1.getUid(), downloadUrl.getPath());
                                    insertData(downloadUrl.toString(), user1.getUid());
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }

    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    protected void onStart() {
        super.onStart();
        //  fbAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // fbAdapter.stopListening();
    }


    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_share_single:
                CommonClass cls = new CommonClass();
                cls.ShareToOtherPlatforms("", "", "", "", nlw.this);
        }
    }

}
