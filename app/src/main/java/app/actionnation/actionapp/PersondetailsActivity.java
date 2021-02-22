package app.actionnation.actionapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Database_Content.Personal_Habit;
import app.actionnation.actionapp.Database_Content.Personal_Statement;
import app.actionnation.actionapp.Database_Content.Personal_Visualization;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class PersondetailsActivity extends BaseClassUser implements View.OnClickListener {
    Button btnSave, btnSaveHabit, btnVisualizationSubmit, btnImgSave, btnDistractionSubmit;
    ImageButton btnImgChoose, btnPrev, btnNxt;

    EditText etPurpose, etMission, etVision, etHabit, etHabitDescription, etVisualization, etDistraction;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    ViewFlipper vf;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    Bitmap bitmap;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    String usrId;
    FirebaseUser fbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persondetails);
        generatePublicMenu();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }

        btnSave = findViewById(R.id.btnPdSubmit);
        btnPrev = findViewById(R.id.btnPdPrev);
        btnNxt = findViewById(R.id.btnPdNext);
        btnSaveHabit = findViewById(R.id.btnPdSaveHabit);
        btnVisualizationSubmit = findViewById(R.id.btnPdSaveVisualization);
        btnImgSave = findViewById(R.id.btnPdSaveImages);
        btnImgChoose = findViewById(R.id.btnPdChooseFiles);
        btnDistractionSubmit = findViewById(R.id.btnPdSaveDistration);


        etMission = findViewById(R.id.txtPdMission);
        etPurpose = findViewById(R.id.txtPdPurpose);
        etHabit = findViewById(R.id.txtPdHabit);
        etHabitDescription = findViewById(R.id.txtPdHabitDescription);
        etVisualization = findViewById(R.id.txtPdVisualizationText);
        etDistraction = findViewById(R.id.etDistration);

        etVision = findViewById(R.id.txtPdVision);
        vf = findViewById(R.id.vfPersonalDetails);

        Bundle extras = getIntent().getExtras();
        String pageRedirect = "";
        if (extras != null) {
            pageRedirect = extras.getString(getString(R.string.Page_Redirect));
        }

        if (pageRedirect != null) {
            if (pageRedirect.equals(getString(R.string.Page_Redirect_Visualization))) {
                GetVisualizationData(usrId);
                vf.setDisplayedChild(2);
            } else if (pageRedirect.equals(getString(R.string.Page_Redirect_Habit))) {
                GetVisualizationData(usrId);
                vf.setDisplayedChild(1);
            } else if (pageRedirect.equals(getString(R.string.Page_Redirect_Attention))) {
                GetVisualizationData(usrId);
                vf.setDisplayedChild(3);
            }
        }
        btnSave.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnNxt.setOnClickListener(this);
        btnSaveHabit.setOnClickListener(this);
        btnVisualizationSubmit.setOnClickListener(this);
        btnImgSave.setOnClickListener(this);
        btnImgChoose.setOnClickListener(this);
        btnDistractionSubmit.setOnClickListener(this);


    }


    //vfPersonalDetails
    @Override
    public void onClick(View view) {
        int i = view.getId();
        CommonClass cls = new CommonClass();
        if (i == R.id.btnPdSubmit) {
            if (TextUtils.isEmpty(etMission.getText()) || TextUtils.isEmpty(etPurpose.getText()) || TextUtils.isEmpty(etVision.getText())) {
                cls.callToast(PersondetailsActivity.this, "The Edit Text boxes are empty!");
            } else {
                CollectionReference dbPersonalStatements = db.collection(getString(R.string.fs_Personal_statements));
                Personal_Statement ps = new Personal_Statement();
                ps.setFb_Id(usrId);
                ps.setPersonalMission(etMission.getText().toString());
                ps.setPersonnalPurpose(etPurpose.getText().toString());
                ps.setPersonalVision(etVision.getText().toString());
                ps.setStatus(1);
                dbPersonalStatements.add(ps).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        CommonClass cls = new CommonClass();

                        cls.callToast(PersondetailsActivity.this, "Inserting Done!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PersondetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                    }
                });
                clearForm((ViewGroup) findViewById(R.id.vfPersonalDetails));
            }
        } else if (i == R.id.btnPdPrev) {
            vf.showPrevious();
            if (vf.getDisplayedChild() == 2) {
                GetVisualizationData(usrId);
            }

        } else if (i == R.id.btnPdNext) {
            vf.showNext();
            if (vf.getDisplayedChild() == 2) {
                GetVisualizationData(usrId);
            }
        } else if (i == R.id.btnPdSaveHabit) {
            DbHelperClass Db = new DbHelperClass();

            if (TextUtils.isEmpty(etHabit.getText().toString()) || TextUtils.isEmpty(etHabitDescription.getText().toString())) {
                cls.callToast(PersondetailsActivity.this, "The Edit Text boxes are empty!");
            } else {
                Personal_Habit ph = new Personal_Habit();
                ph.setFb_Id(usrId);
                Calendar c = Calendar.getInstance();
                long presentDate = c.getTimeInMillis();
                int dayOfYear = c.get(Calendar.DAY_OF_YEAR);

                ph.setHabit(etHabit.getText().toString());
                ph.setHabitWorks(etHabitDescription.getText().toString());
                ph.setStatus(Integer.parseInt(getString(R.string.Status_Active_Number)));
                ph.setHabitDayOfTheYear(dayOfYear);
                ph.setHabitDays(Integer.parseInt(getString(R.string.ht_HabitDaysInitialization)));
                ph.setHabitDate(presentDate);

                Db.insertFireStoreData(getString(R.string.fs_PersonalHabits), PersondetailsActivity.this, ph, db);
                DbHelper dbh = new DbHelper(PersondetailsActivity.this);
                dbh.insertHabit(usrId, etHabit.getText().toString(), 0, "Dummy");
            }
            clearForm((ViewGroup) findViewById(R.id.vfPersonalDetails));
        } else if (i == R.id.btnPdSaveVisualization) {
            DbHelperClass Db = new DbHelperClass();
            Personal_Visualization pv = new Personal_Visualization();
            if (TextUtils.isEmpty(etVisualization.getText().toString())) {
                cls.callToast(PersondetailsActivity.this, "The Edit Text boxes are empty!");
            } else {
                if (btnVisualizationSubmit.getText().equals("Edit")) {
                    Db.updateVisualization(getString(R.string.fs_PersonVisualization), etVisualization.getTag().toString(), etVisualization.getText().toString());
                } else {
                    pv.setFb_Id(usrId);
                    pv.setPersonVisualStatement(etVisualization.getText().toString());
                    pv.setStatus(1);
                    Db.insertFireVisualization(getString(R.string.fs_PersonVisualization), PersondetailsActivity.this, pv, db);
                    DbHelper dbh = new DbHelper(PersondetailsActivity.this);
                    dbh.insertHabit(usrId, etHabit.getText().toString(), 0, "Dummy");
                }
            }
            clearForm((ViewGroup) findViewById(R.id.vfPersonalDetails));


        } else if (i == R.id.btnPdSaveImages) {
            if (bitmap != null)
                InsertImageIntoPhone(bitmap);
            else
                cls.callToast(PersondetailsActivity.this, "Choose an Image!");

            clearForm((ViewGroup) findViewById(R.id.vfPersonalDetails));

        } else if (i == R.id.btnPdChooseFiles) {
            showFileChooser();
            clearForm((ViewGroup) findViewById(R.id.vfPersonalDetails));
        } else if (i == R.id.btnPdSaveDistration) {
            DbHelperClass Db = new DbHelperClass();
            Personal_Distraction pd = new Personal_Distraction();

            pd.setFb_Id(usrId);
            pd.setDistrationName(etDistraction.getText().toString());
            pd.setStatus(1);
            Db.insertFireDistraction(getString(R.string.fs_PersonalDistraction), PersondetailsActivity.this, pd, db);
            DbHelper dbh = new DbHelper(PersondetailsActivity.this);
            dbh.insertHabit(usrId, etHabit.getText().toString(), 0, "Dummy");
            clearForm((ViewGroup) findViewById(R.id.vfPersonalDetails));
        }
    }

    private void GetVisualizationData(String usrId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(getString(R.string.fs_PersonVisualization)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshots = task.getResult();
                    for (DocumentSnapshot readData : queryDocumentSnapshots.getDocuments()) {

                        etVisualization.setText(readData.get(getString(R.string.fs_PersonalVisualization_columnn)).toString());
                        etVisualization.setTag(readData.getId());
                        btnVisualizationSubmit.setText("Edit");
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       /* Toast.makeText(FirestoreActivity22.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Androidview", e.getMessage());*/
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    String TAG = "PersonalDetails";

    private void InsertImageIntoPhone(Bitmap bm) {
        String pathLink = Environment.getExternalStorageState() + File.separator + "testing.jpg";

        // Check whether this app has write external storage permission or not.
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(PersondetailsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // If do not grant write external storage permission.
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(PersondetailsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
        }
        storeImage(bm);
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


    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
      /*  File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");*/

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + getString(R.string.file_Visualization));

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
        String mImageName = getString(R.string.file_Visualization_startformat) + timeStamp + getString(R.string.file_imageformat_jpg);
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }



}
