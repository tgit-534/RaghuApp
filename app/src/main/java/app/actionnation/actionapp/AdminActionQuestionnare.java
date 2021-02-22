package app.actionnation.actionapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.Upload;

public class AdminActionQuestionnare extends AdminCommonClass implements View.OnClickListener {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReferenceActionUniversity;
    EditText etData, etDataNumber, etDataExpand;
    Spinner spnStatus;
    Button btnSave;
    ImageButton imgChooseImage;
    Bundle extras;
    String StrCommonDataId;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;

    //firebase objects
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_action_questionnare);
        generatePublicMenu();
        extras = getIntent().getExtras();
        storageReference = FirebaseStorage.getInstance().getReference();

        InitializeControls();
        setSpinnerStatus();
        if (extras != null) {
            StrCommonDataId = extras.getString(getString(R.string.Intent_CommonDataId));
            mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).child(StrCommonDataId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String strCommonData = dataSnapshot.getValue(CommonData.class).getDataString();
                    String strCommonDataExpand = dataSnapshot.getValue(CommonData.class).getDataContent();
                    String strDataNumber = String.valueOf(dataSnapshot.getValue(CommonData.class).getDataNumber());
                    btnSave.setText(R.string.AC_Editbtn);
                    btnSave.setTag(StrCommonDataId);
                    etData.setText(strCommonData);
                    etDataExpand.setText(strCommonDataExpand);
                    etDataNumber.setText(strDataNumber);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        btnSave.setOnClickListener(this);
        imgChooseImage.setOnClickListener(this);
    }

    private void InitializeControls() {
        etData = findViewById(R.id.et_aaq_Data);
        etDataNumber = findViewById(R.id.et_aaq_Number);
        etDataExpand = findViewById(R.id.et_aaq_DataExpand);
        spnStatus = findViewById(R.id.spn_aaq_Status);
        btnSave = findViewById(R.id.btn_aaq_Save);
        imgChooseImage = findViewById(R.id.abtn_display_ChooseImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdminMenuSelected(item);
        return super.onOptionsItemSelected(AdminMenuSelected(item));
    }


    private void setSpinnerStatus() {
        String[] data = {getString(R.string.aaq_CareerQuestionnare),
                getString(R.string.aaq_values),
                getString(R.string.aaq_Affirmations),
                getString(R.string.aaq_Inactive),
                getString(R.string.aaq_description),
                getString(R.string.aaq_Display_fields),
                getString(R.string.aaq_EatHealthy),
                getString(R.string.aaq_Happiness),
                getString(R.string.aaq_True_Learning),
                getString(R.string.aaq_Forgiveness),
                getString(R.string.aaq_Emotions),
                getString(R.string.aaq_Nature),
                getString(R.string.aaq_Story),
                getString(R.string.aaq_AvoidFood)


        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminActionQuestionnare.this, android.R.layout.simple_spinner_item, data);
        spnStatus.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        CommonClass cls = new CommonClass();
        int i = view.getId();
        if (i == R.id.btn_aaq_Save) {
            if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Display_fields))) {
                uploadFile();
                return;
            }
            CommonData aq = new CommonData();
            aq.setDataString(etData.getText().toString());
            aq.setDataContent(etDataExpand.getText().toString());
            if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_CareerQuestionnare))) {
                aq.setStatus(Integer.parseInt(getString(R.string.aaq_CareerQuestionnare_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_values))) {
                aq.setStatus(Integer.parseInt(getString(R.string.aaq_values_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Affirmations))) {
                aq.setStatus(Integer.parseInt(getString(R.string.aaq_Affirmations_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Inactive))) {
                aq.setStatus(Integer.parseInt(getString(R.string.aaq_Inactive_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_description))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_description_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_True_Learning))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_True_Learning_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Happiness))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_Happiness_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_EatHealthy))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_EatHealthy_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Forgiveness))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_Forgiveness_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Emotions))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_Emotions_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Nature))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_Nature_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_Story))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_Story_Number)));
            } else if (spnStatus.getSelectedItem().equals(getString(R.string.aaq_AvoidFood))) {

                aq.setStatus(Integer.parseInt(getString(R.string.aaq_AvoidFood_Number)));
            }


            aq.setDataNumber(Integer.parseInt(etDataNumber.getText().toString()));
            if (extras != null) {
                mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).child(StrCommonDataId);

                mReferenceActionUniversity.setValue(aq);
                cls.callToast(AdminActionQuestionnare.this, getString(R.string.Toast_Inserted));
            } else {
                mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db));

                mReferenceActionUniversity.push().setValue(aq);
                cls.callToast(AdminActionQuestionnare.this, getString(R.string.Toast_Inserted));
            }
        } else if (i == R.id.abtn_display_ChooseImage) {
            showFileChooser();
        }

    }


    private void insertDataIntoCommonData(String StrUrl) {
        CommonClass cls = new CommonClass();
        CommonData aq = new CommonData();
        aq.setDataString(etData.getText().toString());
        aq.setDataContent(etDataExpand.getText().toString());
        aq.setStatus(Integer.parseInt(getString(R.string.aaq_Display_fields_Number)));
        aq.setDataUrl(StrUrl);
        aq.setDataNumber(Integer.parseInt(etDataNumber.getText().toString()));
        if (extras != null) {
            mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).child(StrCommonDataId);

            mReferenceActionUniversity.setValue(aq);
            cls.callToast(AdminActionQuestionnare.this, getString(R.string.Toast_Inserted));
        } else {
            mReferenceActionUniversity = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db));

            mReferenceActionUniversity.push().setValue(aq);
            cls.callToast(AdminActionQuestionnare.this, getString(R.string.Toast_Inserted));
        }

    }


    //Uploading IMages
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
            final StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_DisplayFields + System.currentTimeMillis() + "." + getFileExtension(filePath));

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
                                    insertDataIntoCommonData(downloadUrl.toString());
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





}