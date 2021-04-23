package app.actionnation.actionapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Database_Content.UserProfile;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityMainObjectives extends BaseClassUser implements View.OnClickListener {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    private static final String TAG = "ActionMainObjective:Log";
    de.hdodenhof.circleimageview.CircleImageView profileImage, profileImagePlus;
    FloatingActionButton fab;
    RatingBar ratingBar;
    // ImageView imageView;
    Uri uri;
    FirebaseFirestore rootRef;
    TextView userName, userDesc;
    UserGame userGame = new UserGame();
    ArrayList<Integer> userGameArray = new ArrayList<>();
    ArrayList<String> userCaptainArray = new ArrayList<>();
    ImageButton imgProfile, imgCaptain, imgStory;
    String strUserRating;
    float[] userRatingFloatArray = new float[2];

    private static FragmentManager fragmentManager;

    FirebaseRecyclerAdapter fbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_objectives);
        generatePublicMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        profileImageWork();
        imgProfile = findViewById(R.id.imgBtn_profile);

        imgCaptain = findViewById(R.id.imgBtn_Captain);
        imgStory = findViewById(R.id.imgBtn_YourStory);
        ratingBar = findViewById(R.id.rb_captainRatings);
        userName = findViewById(R.id.et_amo_username);
        userDesc = findViewById(R.id.et_amo_userDesc);
        recyclerView = findViewById(R.id.listMainObjective);

        Log.d(TAG, "Uid :" + fetchUserId() + " " +String.valueOf(fetchDate(0)) +" "+ String.valueOf(fetchDate(1)));


        userGameArray = mainDataUpdate();

        //BarChart
        fragmentManager = getSupportFragmentManager();

        Fragment argumentFragment = new FragmentGameBar();//Get Fragment Instance
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerBarChart, argumentFragment).commit();


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });

        imgCaptain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRatingFloatArray = (float[]) fab.getTag();
                Intent homepage4 = new Intent(ActivityMainObjectives.this, ActivityYourTeam.class);
                Bundle mBundle4 = new Bundle();
                mBundle4.putString(getString(R.string.common_auth), getString(R.string.common_google));
                mBundle4.putFloatArray(getString(R.string.Intent_ArrayRating), (float[]) fab.getTag());
                mBundle4.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                homepage4.putExtras(mBundle4);
                startActivity(homepage4);
            }
        });


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileCompleteFunction();
            }
        });

        imgStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage4 = new Intent(ActivityMainObjectives.this, ActivityUserStory.class);
                Bundle mBundle4 = new Bundle();
                mBundle4.putString(getString(R.string.common_auth), getString(R.string.common_google));
                if (imgStory.getTag() != null)
                    mBundle4.putString(getString(R.string.Intent_UserImagePath), imgStory.getTag().toString());
                homepage4.putExtras(mBundle4);
                startActivity(homepage4);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.listMainObjective);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        recyclerView.setHasFixedSize(false);
        fetch(userGameArray);
    }


    @Override
    public void onStart() {
        super.onStart();
        fbAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fbAdapter.stopListening();

    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();

        String imgUrl = "";
        if (imgStory.getTag() != null)
            imgUrl = imgStory.getTag().toString();

        FragmentCreateStory editNameDialogFragment = FragmentCreateStory.newInstance(imgUrl);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    private void profileCompleteFunction() {

        Intent homepage = new Intent(ActivityMainObjectives.this, ActivityProfileComplete.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
        homepage.putExtras(mBundle);
        startActivity(homepage);

    }


    private void fetch(final ArrayList<Integer> userGameArray) {
        Query query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild(getString(R.string.fb_status)).equalTo(Integer.valueOf(getString(R.string.aaq_Display_fields_Number)));
        //Query query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("dataNumber");
        Log.d(TAG, "0");

        FirebaseRecyclerOptions<CommonData> options = new FirebaseRecyclerOptions.Builder<CommonData>()
                .setQuery(query, CommonData.class)
                .build();
        Log.d(TAG, "1");

        fbAdapter = new FirebaseRecyclerAdapter<CommonData, ViewHolderMainObjective>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderMainObjective holder, final int position, @NonNull CommonData model) {
                //holder.mIdView.setText(model.getDataContent());
                holder.mIdView.setText(model.getDataString());
                holder.mImageView.setTag(model.getDataNumber());
                String strFirstLetter = model.getDataString().substring(0, 1);
                Log.d(TAG, "2");

                holder.mIdViewFirstLetter.setText(strFirstLetter);

                //Setting the check when things are done

                switch (position) {
                    case 0:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserPlaceWinScore) > 0 && userGameArray.get(Constants.Game_CP__UserSelfWinScore) > 0 && userGameArray.get(Constants.Game_CP__UserWordWinScore) > 0 && userGameArray.get(Constants.Game_CP__UserWorkWinScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 1:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserDistractionScore) > 0 && userGameArray.get(Constants.Game_CP__UserTractionScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserMeditationScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 3:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserTrueLearningScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 4:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserGratitudeScore) > 0 && userGameArray.get(Constants.Game_CP__UserForgivenessOutsideScore) > 0 && userGameArray.get(Constants.Game_CP__UserForgivenessSelfScore) > 0 && userGameArray.get(Constants.Game_CP__UserAbundanceScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 5:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserEatHealthyScore) > 0 && userGameArray.get(Constants.Game_CP__UserAvoidForHealthScore) > 0 && userGameArray.get(Constants.Game_CP__UserExerciseScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 6:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserHabitsScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 7:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserExperienceNatureScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 8:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserRevealStoryScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 9:
                        if (userGameArray == null || userGameArray.size() == 0)
                            break;
                        if (userGameArray.get(Constants.Game_CP__UserOurBeliefScore) > 0) {
                            holder.mImageViewCheck.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        break;
                }


                // final String key =  userList.get(position).getKey();

                /*Glide.with(ActivityMainObjectives.this)
                        .asBitmap()
                        .load(model.getDataUrl())
                        //  .override(180, 180)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                holder.mImageView.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                            }
                        });*/

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int itemPosition = position;
                        Intent i = null;
                        switch (position) {
                            case 0:
                                i = new Intent(holder.mIdView.getContext(), ActivityIntegrityMain.class);
                                break;
                            case 1:
                                i = new Intent(holder.mIdView.getContext(), ActivityAttention.class);
                                break;
                            case 2:
                                i = new Intent(holder.mIdView.getContext(), MeditationActivity.class);
                                break;
                            case 3:
                                i = new Intent(holder.mIdView.getContext(), Activity_TrueLearning.class);
                                break;
                            case 4:
                                i = new Intent(holder.mIdView.getContext(), ActivityHappiness.class);
                                break;
                            case 5:
                                i = new Intent(holder.mIdView.getContext(), ActivityEatHealthy.class);
                                break;
                            case 6:
                                i = new Intent(holder.mIdView.getContext(), HabitTraking.class);
                                break;
                            case 7:
                                i = new Intent(holder.mIdView.getContext(), ActivityExperienceNature.class);
                                break;
                            case 8:
                                i = new Intent(holder.mIdView.getContext(), ActivityRevealStory.class);
                                break;
                            case 9:
                                i = new Intent(holder.mIdView.getContext(), ActivityOurBelief.class);
                                break;
                        }

                        Bundle mBundle = new Bundle();
                        mBundle.putString(Constants.common_auth, Constants.common_google);
                        mBundle.putStringArrayList(Constants.Intent_ArrayCaptain, (ArrayList<String>) imgProfile.getTag());
                        mBundle.putIntegerArrayList(Constants.Intent_ArrayGameScore, userGameArray);
                        i.putExtras(mBundle);
                        holder.mView.getContext().startActivity(i);
                    }
                });


                holder.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_Integrity))) {
                            Intent homepage = new Intent(view.getContext(), ActivityIntegrityMain.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);
                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_Attention))) {
                            Intent homepage = new Intent(view.getContext(), ActivityAttention.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_Meditation))) {
                            Intent homepage = new Intent(view.getContext(), MeditationActivity.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);

                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_TrueLearning))) {
                            Intent homepage = new Intent(view.getContext(), Activity_TrueLearning.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);

                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_Happy))) {
                            Intent homepage = new Intent(view.getContext(), ActivityHappiness.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);

                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_EatHealthy))) {
                            Intent homepage = new Intent(view.getContext(), ActivityExcerciseEat.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);

                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_Habit))) {
                            Intent homepage = new Intent(view.getContext(), HabitTraking.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);

                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_Excercise))) {
                            Intent homepage = new Intent(view.getContext(), ActivityExperienceNature.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);

                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_Review))) {
                            Intent homepage = new Intent(view.getContext(), ActivityRevealStory.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);

                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else if (Integer.parseInt(holder.mImageView.getTag().toString()) == Integer.parseInt(getString(R.string.Display_fields_Visualization))) {
                            Intent homepage = new Intent(view.getContext(), ActivityOurBelief.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                            mBundle.putStringArrayList(getString(R.string.Intent_ArrayCaptain), (ArrayList<String>) imgProfile.getTag());
                            mBundle.putIntegerArrayList(getString(R.string.Intent_ArrayGameScore), userGameArray);
                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        }
                    }
                });
            }

            @NonNull
            @Override
            public ActivityMainObjectives.ViewHolderMainObjective onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mainlist, parent, false);
                ActivityMainObjectives.ViewHolderMainObjective viewHolder = new ActivityMainObjectives.ViewHolderMainObjective(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
    }


    @Override
    public void onClick(View v) {

    }

    //Update the User Data
    private ArrayList<Integer> mainDataUpdate() {
        rootRef = FirebaseFirestore.getInstance();

        DocumentReference docRef = rootRef.collection(getString(R.string.fs_UserProfile)).document(fetchUserId());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserProfile userProfile = documentSnapshot.toObject(UserProfile.class);
                if (userProfile != null) {
                    String FirstName = userProfile.getUserFirstName();
                    String LastName = userProfile.getUserLastName(), UserDream = userProfile.getUserDream();

                    float userRating = userProfile.getUserRating();
                    int totalNoOfraters = userProfile.getUserNoOfRatings();

                    strUserRating = String.valueOf(userRating) + "," + String.valueOf(totalNoOfraters);
                    userRatingFloatArray[0] = userRating;
                    userRatingFloatArray[1] = totalNoOfraters;

                    userRating = userRating / totalNoOfraters;

                    ratingBar.setRating(userRating);
                    ratingBar.setEnabled(false);
                    fab.setTag(userRatingFloatArray);

                    if (FirstName != null || LastName != null || UserDream != null) {
                        userName.setText(userProfile.getUserFirstName() + " " + userProfile.getUserLastName());
                        userDesc.setText(userProfile.getUserDream());
                        userCaptainArray = (ArrayList<String>) userProfile.getTeamCaptains();
                        imgProfile.setTag(userProfile.getTeamCaptains());
                        imgStory.setTag(userProfile.getUserImagePath());
                        Glide.with(ActivityMainObjectives.this)
                                .asBitmap()
                                .load(userProfile.getUserImagePath())
                                //  .override(180, 180)
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        profileImage.setImageBitmap(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }

                                    @Override
                                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                        super.onLoadFailed(errorDrawable);
                                    }
                                });


                    }
                } else {
                    profileCompleteFunction();
                }

            }
        });

        CommonClass cls = new CommonClass();
        userGameArray = cls.getUserGameLocal(ActivityMainObjectives.this, fetchUserId());
        return userGameArray;

    }

    protected List<String> getCaptains() {
        List<String> captains = (List<String>) imgProfile.getTag();
        return captains;
    }


    ///Image Crop and Profile


    private void profileImageWork() {
        profileImage = findViewById(R.id.profile_image);
        profileImagePlus = findViewById(R.id.img_plus);

        profileImagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(ActivityMainObjectives.this);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);

            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                uri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                startCrop(imageuri);
            }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                profileImage.setImageURI(result.getUri());
                uploadFile(result.getUri());

                Toast.makeText(this, "Image Update Successfully", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);


    }


    private void uploadFile(Uri filePath) {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final String postId = FirebaseDatabase.getInstance().getReference().push().getKey();


            final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("ImagePosts/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() +
                            "/" + postId + "/post_image");
            //getting the storage reference
            //final StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_DisplayFields + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            storageReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri downloadUrl = uri;
                                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                    // Upload upload = new Upload(user1.getUid(), downloadUrl.getPath());
                                    DbHelperClass dbh = new DbHelperClass();

                                    //  dbh.updateFireUserProfile(getString(R.string.common_auth),ActivityMainObjectives.this, fetchUserId(),downloadUrl.getPath(),rootRef )
                                    dbh.updateFireUserProfile(getString(R.string.fs_UserProfile), ActivityMainObjectives.this, fetchUserId(), "userImagePath", downloadUrl.toString(), rootRef);

                                    // insertDataIntoCommonData(downloadUrl.toString());
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

    public static class ViewHolderMainObjective extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mImageView;
        public final ImageView mImageViewCheck;
        public final TextView mIdViewFirstLetter;

        public CommonData mItem;

        public ViewHolderMainObjective(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_name);
            mImageView = view.findViewById(R.id.imgBtn);
            mImageViewCheck = view.findViewById(R.id.imgTick);
            mIdViewFirstLetter = view.findViewById(R.id.item_name_FirstLetter);

            /*
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getAdapterPosition(), v);

                    }
                }
            });*/

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }


}
