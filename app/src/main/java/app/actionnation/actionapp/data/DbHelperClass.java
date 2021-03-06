package app.actionnation.actionapp.data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.actionnation.actionapp.ActivityAttention;
import app.actionnation.actionapp.ActivityExercise;
import app.actionnation.actionapp.ActivityGameCreation;
import app.actionnation.actionapp.ActivityIntegrity;
import app.actionnation.actionapp.ActivityReading;
import app.actionnation.actionapp.ActivityTimerWindow;
import app.actionnation.actionapp.ActivityYourTeam;
import app.actionnation.actionapp.CommonClass;
import app.actionnation.actionapp.Database_Content.Person_Integrity;
import app.actionnation.actionapp.Database_Content.Personal_Book;
import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Database_Content.Personal_Excercise;
import app.actionnation.actionapp.Database_Content.Personal_Habit;
import app.actionnation.actionapp.Database_Content.Personal_Statement;
import app.actionnation.actionapp.Database_Content.Personal_Visualization;
import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.Database_Content.UserCaptainRatings;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Database_Content.UserProfile;
import app.actionnation.actionapp.Database_Content.UserStories;
import app.actionnation.actionapp.Database_Content.UserTeam;
import app.actionnation.actionapp.FragmentSelfDream;
import app.actionnation.actionapp.HabitTraking;
import app.actionnation.actionapp.R;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.displaydata;

public class DbHelperClass {

    public DbHelperClass() {

    }

    private DatabaseReference mReferenceActionUniversity;


    public void insertFireStoreData(String collectionReference, final Context ct, Personal_Habit dataObject, FirebaseFirestore db) {
        CommonClass cls = new CommonClass();
        CollectionReference collectionRef = db.collection(collectionReference);
        collectionRef.add(dataObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                CommonClass cls = new CommonClass();

                cls.callToast(ct, "Inserting Done!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, e.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }


    public void insertFireVisualization(String collectionReference, final Context ct, Personal_Visualization dataObject, FirebaseFirestore db) {
        CollectionReference collectionRef = db.collection(collectionReference);
        collectionRef.add(dataObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                CommonClass cls = new CommonClass();

                cls.callToast(ct, "Inserting Done!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, e.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }


    public void insertFireDistraction(String collectionReference, final Context ct, Personal_Distraction dataObject, FirebaseFirestore db) {
        CollectionReference collectionRef = db.collection(collectionReference);
        collectionRef.add(dataObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                CommonClass cls = new CommonClass();

                cls.callToast(ct, "Inserting Done!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, e.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }


    public void insertFireBook(String collectionReference, final Context ct, Personal_Book dataObject, FirebaseFirestore db) {
        CollectionReference collectionRef = db.collection(collectionReference);
        collectionRef.add(dataObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                CommonClass cls = new CommonClass();

                cls.callToast(ct, "Inserting Done!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, e.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    public void insertFireExcercise(String collectionReference, final Context ct, Personal_Excercise dataObject, FirebaseFirestore db) {
        CollectionReference collectionRef = db.collection(collectionReference);
        collectionRef.add(dataObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                CommonClass cls = new CommonClass();

                cls.callToast(ct, "Inserting Done!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, e.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }


    public void insertFireUserProfile(String collectionReference, final Context ct, UserProfile dataObject, FirebaseFirestore db) {

        DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id());

        docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, "Deletion Done", Toast.LENGTH_LONG);

            }
        });
    }

    public void insertFireCaptainRatings(String collectionReference, final Context ct, UserCaptainRatings dataObject, FirebaseFirestore db) {

        DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id() + dataObject.getUserCaptainFb_Id());

        docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, "Deletion Done", Toast.LENGTH_LONG);

            }
        });
    }

    public void insertFireUserStories
            (String collectionReference, final Context ct, UserStories dataObject, FirebaseFirestore db) {

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();

        DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id() + ts);

        docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, "Deletion Done", Toast.LENGTH_LONG);

            }
        });
    }


    public void insertFireUserTeam(String collectionReference, final Context ct, UserTeam dataObject, FirebaseFirestore db) {

        DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id());

        docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, "Deletion Done", Toast.LENGTH_LONG);

            }
        });
    }


    public void updateFireUserProfile(String collectionReference, final Context ct, String userId, String dataVariable, String dataObject, FirebaseFirestore db) {

        Map<String, Object> userVariable = new HashMap<>();
        userVariable.put(dataVariable, dataObject);

        db.collection(collectionReference).document(userId)
                .update(userVariable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    public void updateFireUserProfileRating(String collectionReference, final Context ct, String userId, float captainRating, int totalNumberOfUsers, FirebaseFirestore db) {

        Map<String, Object> userVariable = new HashMap<>();
        userVariable.put(Constants.UserProfile_UserRating, captainRating);
        userVariable.put(Constants.UserProfile_UserNoOfRatings, totalNumberOfUsers);

        db.collection(collectionReference).document(userId)
                .update(userVariable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    public void updateFireUserProfile(String collectionReference, final Context ct, String userId, String dataVariable, Object dataObject, FirebaseFirestore db) {

        Map<String, Object> userVariable = new HashMap<>();
        userVariable.put(dataVariable, dataObject);

        db.collection(collectionReference).document(userId)
                .update(userVariable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    public void insertFireUserGame(final String collectionReference, final Context ct, final UserGame dataObject, final FirebaseFirestore db, String dataVariable, int dataObjectUpdate, int totalScore) {

        final int i = 0;
        Map<String, Object> userVariable = new HashMap<>();
        userVariable.put(dataVariable, dataObjectUpdate);
        userVariable.put("userTotatScore", totalScore);


        if (totalScore == 0) {
            DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id() + String.valueOf(dataObject.getDayOfTheYear()) + String.valueOf(dataObject.getYear()));
            docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);
                }
            });
        } else {
            db.collection(collectionReference).document(dataObject.getFb_Id() + String.valueOf(dataObject.getDayOfTheYear()) + String.valueOf(dataObject.getYear()))
                    .update(userVariable)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ct, "Updation Success", Toast.LENGTH_LONG);

                        }
                    });
        }

      /*
        db.collection(collectionReference).document(dataObject.getFb_Id() + String.valueOf(dataObject.getDayOfTheYear()) + String.valueOf(dataObject.getYear()))
                .update(userVariable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ct, "Updation Success", Toast.LENGTH_LONG);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id() + String.valueOf(dataObject.getDayOfTheYear()) + String.valueOf(dataObject.getYear()));
                docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ct, "Insertion failure", Toast.LENGTH_LONG);

                    }
                });
            }
        });*/


    }


    public void insertFireUserGame(final String collectionReference, final Context ct, final UserGame dataObject, final FirebaseFirestore db, String dataVariable, int dataObjectUpdate) {

        final int i = 0;
        Map<String, Object> userVariable = new HashMap<>();
        userVariable.put(dataVariable, dataObjectUpdate);

        db.collection(collectionReference).document(dataObject.getFb_Id() + String.valueOf(dataObject.getDayOfTheYear()) + String.valueOf(dataObject.getYear()))
                .update(userVariable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ct, "Updation Success", Toast.LENGTH_LONG);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id() + String.valueOf(dataObject.getDayOfTheYear()) + String.valueOf(dataObject.getYear()));
                docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ct, "Insertion failure", Toast.LENGTH_LONG);

                    }
                });
            }
        });
    }

    public void insertFireTeamGame(final String collectionReference, final Context ct, final TeamGame dataObject, final FirebaseFirestore db) {

        DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id() + String.valueOf(dataObject.getStartDay()) + String.valueOf(dataObject.getStartYear()));

        docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, "Deletion Done", Toast.LENGTH_LONG);

            }
        });
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapter(FirestoreRecyclerAdapter adapter, String collectionReference, com.google.firebase.firestore.Query query) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference cr = rootRef.collection(collectionReference);

        FirestoreRecyclerOptions<Personal_Statement> options = new FirestoreRecyclerOptions.Builder<Personal_Statement>()
                .setQuery(query, Personal_Statement.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Personal_Statement, displaydata.ViewHolderActionPhilosopy>(options) {

            @Override
            protected void onBindViewHolder(@NonNull displaydata.ViewHolderActionPhilosopy holder, int position, @NonNull Personal_Statement model) {
                holder.mIdView.setText(" Purpose: " + model.getPersonnalPurpose() + "\n \n Mission: " + model.getPersonalMission() + "\n \n Vision: " + model.getPersonalVision());

            }

            @NonNull
            @Override
            public displaydata.ViewHolderActionPhilosopy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actionphilosophy_list, parent, false);
                return new displaydata.ViewHolderActionPhilosopy(view);
            }
        };
        return adapter;
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterDream(FirestoreRecyclerAdapter adapter, String collectionReference, com.google.firebase.firestore.Query query) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference cr = rootRef.collection(collectionReference);

        FirestoreRecyclerOptions<Personal_Statement> options = new FirestoreRecyclerOptions.Builder<Personal_Statement>()
                .setQuery(query, Personal_Statement.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Personal_Statement, FragmentSelfDream.ViewHolderSelfDream>(options) {

            @Override
            protected void onBindViewHolder(@NonNull FragmentSelfDream.ViewHolderSelfDream holder, int position, @NonNull Personal_Statement model) {
                holder.mIdView.setText(" Purpose: " + model.getPersonnalPurpose() + "\n \n Mission: " + model.getPersonalMission() + "\n \n Vision: " + model.getPersonalVision());

            }

            @NonNull
            @Override
            public FragmentSelfDream.ViewHolderSelfDream onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actionphilosophy_list, parent, false);
                return new FragmentSelfDream.ViewHolderSelfDream(view);
            }
        };
        return adapter;
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterCaptains(FirestoreRecyclerAdapter adapter, final String collectionReference, com.google.firebase.firestore.Query query, final Context ctx, final ArrayList<String> strCaptains, final String fbId) {
        final FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Log.d(TAG, "Enter Db firestore");
        FirestoreRecyclerOptions<UserTeam> options = new FirestoreRecyclerOptions.Builder<UserTeam>()
                .setQuery(query, UserTeam.class)
                .build();
        Log.d(TAG, "Enter Db 1 firestore");
        adapter = new FirestoreRecyclerAdapter<UserTeam, ActivityYourTeam.ViewHolderSelectCaptain>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ActivityYourTeam.ViewHolderSelectCaptain holder, int position, @NonNull final UserTeam model) {
                holder.mIdSelectCaptain.setText(model.getTeamName());

                if (strCaptains != null) {
                    if (strCaptains.contains(model.getFb_Id())) {
                        holder.mIdSelectCaptain.setChecked(true);
                    }
                }

                holder.mIdSelectCaptain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        if (isChecked == true) {

                            DocumentReference docRef = rootRef.collection("UserProfile").document(fbId);

                            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    UserProfile userProfile = documentSnapshot.toObject(UserProfile.class);


                                    if (userProfile.getTeamCaptains() != null && userProfile.getTeamCaptains().size() > 0) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                                        builder.setTitle("Already you have a captain!!");
                                        builder.setMessage("Are you sure?");

                                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                ArrayList<String> newCaptain = new ArrayList<>();

                                                newCaptain.add(fbId);
                                                newCaptain.add(model.getFb_Id());
                                                newCaptain.add(model.getTeamName());
                                                DbHelperClass dbh = new DbHelperClass();
                                                dbh.updateFireUserProfile("UserProfile", ctx, fbId, "teamCaptains", newCaptain, rootRef);

                                                Toast.makeText(ctx, "Added to the new Captain team!", Toast.LENGTH_SHORT);
                                                dialog.dismiss();

                                            }
                                        });

                                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {


                                                Toast.makeText(ctx, "Not Added to the team!", Toast.LENGTH_SHORT);
                                                // Do nothing
                                                dialog.dismiss();
                                            }
                                        });

                                        AlertDialog alert = builder.create();
                                        alert.show();


                                    } else {
                                        ArrayList<String> newCaptain = new ArrayList<>();

                                        newCaptain.add(fbId);
                                        newCaptain.add(model.getFb_Id());
                                        DbHelperClass dbh = new DbHelperClass();
                                        dbh.updateFireUserProfile("UserProfile", ctx, fbId, "teamCaptains", newCaptain, rootRef);

                                        Toast.makeText(ctx, "Added to the new Captain team!", Toast.LENGTH_SHORT);

                                    }


                                }
                            });

                            Toast.makeText(ctx, "A new Captain!", Toast.LENGTH_LONG);

                        } else {
                            ArrayList<String> newCaptain = new ArrayList<>();

                            DbHelperClass dbh = new DbHelperClass();
                            dbh.updateFireUserProfile("UserProfile", ctx, fbId, "teamCaptains", newCaptain, rootRef);

                            Toast.makeText(ctx, "Captain deleted!", Toast.LENGTH_LONG);

                        }
                    }
                });
            }


            @NonNull
            @Override
            public ActivityYourTeam.ViewHolderSelectCaptain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lb_layout_selectcaptain, parent, false);
                return new ActivityYourTeam.ViewHolderSelectCaptain(view);
            }
        };
        return adapter;
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterHabits(FirestoreRecyclerAdapter adapter, final String collectionReference, com.google.firebase.firestore.Query query, final Context ctx, final String fbId, final ArrayList<String> habitPattern) {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        final FirestoreRecyclerOptions<Personal_Habit> options = new FirestoreRecyclerOptions.Builder<Personal_Habit>()
                .setQuery(query, Personal_Habit.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Personal_Habit, HabitTraking.ViewHolderHabit>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final HabitTraking.ViewHolderHabit holder, int position, @NonNull final Personal_Habit model) {


                if (habitPattern != null) {
                    if (habitPattern.contains(model.getHabit())) {
                        holder.mIdCheckBox.setChecked(true);
                    }
                }

                holder.mIdHabit.setText(model.getHabit());
                holder.mIdCheckBox.setText(model.getHabit());

                //holder.btnHabitSubmit.setTag(getSnapshots().getSnapshot(position).getId() + "," + model.getHabitDayOfTheYear() + "," + model.getHabitDays() + "," + model.getPowerLimit());
                holder.btnHabitSubmit.setTag(model.getHabit());
                holder.mIdCheckBox.setTag(model.getHabit());


                holder.btnHabitSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent homepage = new Intent(ctx, ActivityTimerWindow.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString(Constants.common_auth, Constants.common_google);
                        mBundle.putString(Constants.Page_Redirect, Constants.Page_Redirect_Habit);
                        mBundle.putString("Habit_Name", holder.btnHabitSubmit.getTag().toString());
                        mBundle.putString("Habit_Total", String.valueOf(options.getSnapshots().size()));
                        homepage.putExtras(mBundle);
                        ctx.startActivity(homepage);
                    }
                });

                holder.mIdCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        Calendar cal = Calendar.getInstance();
                        int dayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);
                        int yr = cal.get(Calendar.YEAR);

                        CommonClass cls = new CommonClass();
                        String usrId = fbId;

                        DbHelper db = new DbHelper(ctx);
                        if (isChecked == true) {
                            cls.SubmitHabitScore(Constants.Game_CommonScore, options.getSnapshots().size(), holder.mIdCheckBox.getTag().toString(), db, usrId, dayOfTheYear, yr);
                            db.insertHabitDayTrack(holder.mIdCheckBox.getTag().toString(), usrId, dayOfTheYear, yr, Constants.Status_One);
                            Toast.makeText(ctx, "Well Done", Toast.LENGTH_LONG);
                        } else {
                            cls.SubmitHabitScore(Constants.Game_CommonScore_Negative, options.getSnapshots().size(), holder.mIdCheckBox.getTag().toString(), db, usrId, dayOfTheYear, yr);
                            db.deleteHabitDayTrack(holder.mIdCheckBox.getTag().toString(), usrId, dayOfTheYear, yr);
                            Toast.makeText(ctx, "Well Done", Toast.LENGTH_LONG);
                        }
                    }
                });
            }

            @NonNull
            @Override
            public HabitTraking.ViewHolderHabit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_habittraking, parent, false);
                return new HabitTraking.ViewHolderHabit(view);
            }
        };
        return adapter;
    }

    String TAG = "DBHELPER CLASS";

    public FirestoreRecyclerAdapter GetFireStoreAdapterDistraction(FirestoreRecyclerAdapter adapter, final String collectionReference, com.google.firebase.firestore.Query query, final Context ctx, final ArrayList<String> strAttn, final String fbId) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Log.d(TAG, "Enter Db firestore");
        CollectionReference cr = rootRef.collection(collectionReference);
        final FirestoreRecyclerOptions<Personal_Distraction> options = new FirestoreRecyclerOptions.Builder<Personal_Distraction>()
                .setQuery(query, Personal_Distraction.class)
                .build();
        Log.d(TAG, "Enter Db 1 firestore");

        adapter = new FirestoreRecyclerAdapter<Personal_Distraction, ActivityAttention.ViewHolderDistraction>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ActivityAttention.ViewHolderDistraction holder, int position, @NonNull final Personal_Distraction model) {
                holder.mIdDistraction.setText(model.getDistrationName());

                Log.d(TAG, "Enter Db inside Bind");

                if (strAttn != null) {
                    if (strAttn.contains(model.getDistrationName())) {
                        holder.chkDistraction.setChecked(true);
                    }
                }

                holder.chkDistraction.setTag(model.getDistrationName());
                holder.chkDistraction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Calendar cal = Calendar.getInstance();
                        int dayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);
                        int yr = cal.get(Calendar.YEAR);
                        DbHelper db = new DbHelper(ctx);

                        CommonClass cls = new CommonClass();
                        if (isChecked == true) {
                            db.insertAttention(fbId, holder.chkDistraction.getTag().toString(), 1, dayOfTheYear, yr);
                            cls.InsertAttentionScore(db, fbId, dayOfTheYear, yr, 1, 0, options.getSnapshots().size(), 0);

                        } else {
                            db.updateDataAttention(fbId, holder.chkDistraction.getTag().toString(), 0, dayOfTheYear, yr);
                            cls.InsertAttentionScore(db, fbId, dayOfTheYear, yr, -1, 0, options.getSnapshots().size(), 0);

                        }
                    }

                });
            }

            @NonNull
            @Override
            public ActivityAttention.ViewHolderDistraction onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_distractionlist, parent, false);
                return new ActivityAttention.ViewHolderDistraction(view);
            }
        };
        return adapter;
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterTrueLearning(FirestoreRecyclerAdapter adapter, final String collectionReference, com.google.firebase.firestore.Query query, final Context ctx, final ArrayList<String> strAttn) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Log.d(TAG, "Enter Db firestore");
        CollectionReference cr = rootRef.collection(collectionReference);
        FirestoreRecyclerOptions<Personal_Distraction> options = new FirestoreRecyclerOptions.Builder<Personal_Distraction>()
                .setQuery(query, Personal_Distraction.class)
                .build();
        Log.d(TAG, "Enter Db 1 firestore");

        adapter = new FirestoreRecyclerAdapter<Personal_Distraction, ActivityAttention.ViewHolderDistraction>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ActivityAttention.ViewHolderDistraction holder, int position, @NonNull final Personal_Distraction model) {
                holder.mIdDistraction.setText(model.getDistrationName());

                Log.d(TAG, "Enter Db inside Bind");

                if (strAttn != null) {
                    if (strAttn.contains(model.getDistrationName())) {
                        holder.chkDistraction.setChecked(true);
                    }
                }

                holder.chkDistraction.setTag(model.getDistrationName());
                holder.chkDistraction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Calendar cal = Calendar.getInstance();
                        int dayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);
                        int yr = cal.get(Calendar.YEAR);

                        DbHelper db = new DbHelper(ctx);
                        if (isChecked == true) {
                            db.insertAttention("", holder.chkDistraction.getTag().toString(), 1, dayOfTheYear, yr);
                        } else {
                            db.updateDataAttention("", holder.chkDistraction.getTag().toString(), 0, dayOfTheYear, yr);
                        }
                    }

                });
            }

            @NonNull
            @Override
            public ActivityAttention.ViewHolderDistraction onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_distractionlist, parent, false);
                return new ActivityAttention.ViewHolderDistraction(view);
            }
        };
        return adapter;
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterBooks(FirestoreRecyclerAdapter adapter, final String collectionReference, com.google.firebase.firestore.Query query, final String strDropDown) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference cr = rootRef.collection(collectionReference);

        FirestoreRecyclerOptions<Personal_Book> options = new FirestoreRecyclerOptions.Builder<Personal_Book>()
                .setQuery(query, Personal_Book.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Personal_Book, ActivityReading.ViewHolderReading>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ActivityReading.ViewHolderReading holder, int position, @NonNull Personal_Book model) {
                holder.mIdReading.setText(model.getBook_Name());
                holder.chkReading.setTag(getSnapshots().getSnapshot(position).getId());
                if (strDropDown.equals(Constants.ar_BookAlRead)) {
                    holder.chkReading.setVisibility(View.INVISIBLE);
                }
                holder.chkReading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference noteRef = db.collection(collectionReference).document(holder.chkReading.getTag().toString());
                            int bookStatus = 0;
                            if (strDropDown.equals(Constants.ar_BookReading)) {
                                bookStatus = Integer.parseInt(Constants.ar_BookAlRead_Number);
                            } else if (strDropDown.equals(Constants.ar_BookToRead)) {
                                bookStatus = Integer.parseInt(Constants.ar_BookReading_Number);
                            }
                            noteRef.update("status", bookStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });
                        }
                    }
                });
            }

            @NonNull
            @Override
            public ActivityReading.ViewHolderReading onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_readingtracking, parent, false);
                return new ActivityReading.ViewHolderReading(view);
            }
        };
        return adapter;
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterExcercise(FirestoreRecyclerAdapter adapter,
                                                                 final String collectionReference, com.google.firebase.firestore.Query query, final Context ctx) {

        FirestoreRecyclerOptions<Personal_Excercise> options = new FirestoreRecyclerOptions.Builder<Personal_Excercise>()
                .setQuery(query, Personal_Excercise.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Personal_Excercise, ActivityExercise.ViewHolderExcercise>(options) {
            @NonNull
            @Override
            public ActivityExercise.ViewHolderExcercise onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_exercise, parent, false);
                return new ActivityExercise.ViewHolderExcercise(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ActivityExercise.ViewHolderExcercise holder, int position, @NonNull Personal_Excercise model) {
                holder.mIdExcercise.setText("Exercise:" + model.getExcerciseName());
                holder.mIdExcercise.setTag(model.getExcerciseName());
                holder.btnExeRoutineSubmit.setTag(model.getExcerciseName());

                // Spinner Drop down elements
                List<String> categories = new ArrayList<String>();
                categories.add("--Select Time--");
                categories.add("5 Minutes");
                categories.add("10 Minutes");
                categories.add("15 Minutes");
                categories.add("20 Minutes");
                categories.add("30 Minutes");
                categories.add("60 Minutes");
                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, categories);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                holder.spnExe.setAdapter(dataAdapter);

                holder.btnExeRoutineSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DbHelper dbh = new DbHelper(ctx);

                        if (holder.spnExe.getSelectedItem().toString().equals("5 Minutes")) {
                            dbh.insertExcercise(holder.btnExeRoutineSubmit.getTag().toString(), holder.btnExeRoutineSubmit.getTag().toString() + ",5", holder.spnExe.getSelectedItem().toString());
                        } else if (holder.spnExe.getSelectedItem().toString().equals("10 Minutes")) {
                            dbh.insertExcercise(holder.btnExeRoutineSubmit.getTag().toString(), holder.btnExeRoutineSubmit.getTag().toString() + ",10", holder.spnExe.getSelectedItem().toString());
                        } else if (holder.spnExe.getSelectedItem().toString().equals("15 Minutes")) {
                            dbh.insertExcercise(holder.btnExeRoutineSubmit.getTag().toString(), holder.btnExeRoutineSubmit.getTag().toString() + ",15", holder.spnExe.getSelectedItem().toString());
                        } else if (holder.spnExe.getSelectedItem().toString().equals("20 Minutes")) {
                            dbh.insertExcercise(holder.btnExeRoutineSubmit.getTag().toString(), holder.btnExeRoutineSubmit.getTag().toString() + ",20", holder.spnExe.getSelectedItem().toString());
                        } else if (holder.spnExe.getSelectedItem().toString().equals("30 Minutes")) {
                            dbh.insertExcercise(holder.btnExeRoutineSubmit.getTag().toString(), holder.btnExeRoutineSubmit.getTag().toString() + ",30", holder.spnExe.getSelectedItem().toString());
                        } else if (holder.spnExe.getSelectedItem().toString().equals("60 Minutes")) {
                            dbh.insertExcercise(holder.btnExeRoutineSubmit.getTag().toString(), holder.btnExeRoutineSubmit.getTag().toString() + ",60", holder.spnExe.getSelectedItem().toString());
                        }
                    }
                });
            }


        };
        return adapter;
    }


    public void insertFireStoreDataIntegrity(String collectionReference, final Context ct, Person_Integrity dataObject, FirebaseFirestore db) {
        CommonClass cls = new CommonClass();
        CollectionReference collectionRef = db.collection(collectionReference);
        collectionRef.add(dataObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                CommonClass cls = new CommonClass();

                cls.callToast(ct, "Inserting Done!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, e.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterIntegrity(FirestoreRecyclerAdapter adapter, final String collectionReference, com.google.firebase.firestore.Query query, final Context ctx, final String usrId) {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference cr = rootRef.collection(collectionReference);
        final FirestoreRecyclerOptions<Person_Integrity> options = new FirestoreRecyclerOptions.Builder<Person_Integrity>()
                .setQuery(query, Person_Integrity.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Person_Integrity, ActivityIntegrity.ViewHolderIntegrity>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ActivityIntegrity.ViewHolderIntegrity holder, final int position, @NonNull Person_Integrity model) {
                holder.mIdIntegrity.setText(model.getPromiseDescription());
                CommonClass cls = new CommonClass();
                String displayDate = cls.compareDates(model.getPromiseDate(), true);


                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(model.getPromiseDate());
                Calendar calToday = Calendar.getInstance();

                long diffDates = model.getPromiseDate() - calToday.getTimeInMillis();
                long daysToDo = diffDates / Constants.milliDay;

                final int dayOfTheYear = calToday.get(Calendar.DAY_OF_YEAR);
                final int yr = calToday.get(Calendar.YEAR);


                if (cal.get(Calendar.YEAR) == calToday.get(Calendar.YEAR) &&
                        cal.get(Calendar.MONTH) == calToday.get(Calendar.MONTH) &&
                        cal.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH)) {
                    holder.mIdNoOfDays.setTextColor(Color.RED);
                }

                if (model.getStatus() == 0) {
                    holder.chkSelect.setChecked(true);
                } else {
                    holder.chkSelect.setChecked(false);
                }

                String displayTime = cls.compareDates(model.getPromiseDate(), false);
                holder.mIdNoOfDays.setText(Constants.NoOfDaysToFullfillPromise + String.valueOf(daysToDo));
                holder.mIdIntegrityDate.setText(displayDate);
                holder.mIdIntegrityTime.setText(displayTime);
                holder.chkSelect.setTag(getSnapshots().getSnapshot(position).getId());

                holder.chkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        int pos = options.getSnapshots().size();

                        if (isChecked == true) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference noteRef = db.collection(collectionReference).document(holder.chkSelect.getTag().toString());
                            int bookStatus = 0;
                            noteRef.update("status", bookStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                }
                            });

                            insertWord(usrId, dayOfTheYear, yr, ctx, 1, pos);
                        } else {

                            insertWord(usrId, dayOfTheYear, yr, ctx, -1, pos);

                        }
                    }


                });
            }


            private void insertWord(String usrId, int dayOfTheYear, int yr, Context ctx, int wordAgreement, int wordAgreementItems) {
                DbHelper db = new DbHelper(ctx);
                Cursor csrIntegrityGame = db.getIntegrityScore(usrId, dayOfTheYear, yr);
                int selfWin = 0, placeWin = 0, wordAgreementDb = 0, wordAgreementItemsDb = 0;
                float respectWork = 0;
                if (csrIntegrityGame.getCount() > 0) {

                    while (csrIntegrityGame.moveToNext()) {

                        selfWin = Integer.parseInt(csrIntegrityGame.getString(2));
                        placeWin = Integer.parseInt(csrIntegrityGame.getString(3));
                        wordAgreementDb = Integer.parseInt(csrIntegrityGame.getString(4));
                        respectWork = Integer.parseInt(csrIntegrityGame.getString(5));
                        wordAgreementItemsDb = Integer.parseInt(csrIntegrityGame.getString(9));
                    }
                    db.updateIntegrityScore(selfWin, placeWin, wordAgreementDb + wordAgreement, wordAgreementItems, respectWork, usrId, dayOfTheYear, yr
                            , 1);

                } else {
                    db.insertIntegrityScore(selfWin, placeWin, wordAgreement, wordAgreementItems, respectWork, usrId, dayOfTheYear, yr
                            , 1);
                }
            }

            @NonNull
            @Override
            public ActivityIntegrity.ViewHolderIntegrity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_integritytracking, parent, false);
                return new ActivityIntegrity.ViewHolderIntegrity(view);
            }
        };
        return adapter;
    }


    public void updateVisualization(String collectionReference, String strDocId, String visualizationStatement) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection(collectionReference).document(strDocId);

        noteRef.update("personVisualStatement", visualizationStatement).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterTeamGame(FirestoreRecyclerAdapter adapter, final String collectionReference, com.google.firebase.firestore.Query query, final Context ctx, final String fbId) {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        final FirestoreRecyclerOptions<UserGame> options = new FirestoreRecyclerOptions.Builder<UserGame>()
                .setQuery(query, UserGame.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<UserGame, ActivityYourTeam.ViewHolderTeam>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ActivityYourTeam.ViewHolderTeam holder, int position, @NonNull UserGame model) {

                CommonClass cls = new CommonClass();
                int TotalScore = cls.userGameScore(model);
                float percentScore = ((float) model.getUserTotatScore() / Constants.Game_userTotalScore) * 100;
                int finalPercent = (int) percentScore;
                holder.mIdGameScore.setText(String.valueOf(finalPercent) + "%");
                //holder.btnHabitSubmit.setTag(getSnapshots().getSnapshot(position).getId() + "," + model.getHabitDayOfTheYear() + "," + model.getHabitDays() + "," + model.getPowerLimit());
                holder.mIdPlayerName.setText(model.getUserName());
            }


            @NonNull
            @Override
            public ActivityYourTeam.ViewHolderTeam onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_captainplayerdisplay, parent, false);
                return new ActivityYourTeam.ViewHolderTeam(view);
            }
        };

        return adapter;
    }


    public void insertFireUserComments
            (String collectionReference, final Context ct, UserStories dataObject, final FirebaseFirestore db, final int commentCount) {

        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        final String documentId = dataObject.getUserStoryId();

        DocumentReference docRef = db.collection(collectionReference).document(dataObject.getFb_Id() + ts);

        docRef.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                int storyCommentCount = commentCount;
                DocumentReference noteRef = db.collection("UserStories").document(documentId);
                noteRef.update("userCommentCount", storyCommentCount + 1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, "Deletion Done", Toast.LENGTH_LONG);

            }
        });
    }

    public FirestoreRecyclerAdapter GetFireStoreAdapterSelectGame(com.google.firebase.firestore.Query query) {
        final FirestoreRecyclerOptions<TeamGame> options = new FirestoreRecyclerOptions.Builder<TeamGame>()
                .setQuery(query, TeamGame.class)
                .build();
        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<TeamGame, ActivityGameCreation.ViewHolderSelectGame>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ActivityGameCreation.ViewHolderSelectGame holder, int position, @NonNull TeamGame model) {
                //      holder.mIdSelectGame.setText(model.getGameName());
                holder.mIdGameName.setText(model.getFb_Id());
            }

            @NonNull
            @Override
            public ActivityGameCreation.ViewHolderSelectGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_gameselection, parent, false);
                return new ActivityGameCreation.ViewHolderSelectGame(view);
            }
        };
        return adapter;
    }


    public FirestoreRecyclerAdapter GetFireStoreAdapterDistraction(com.google.firebase.firestore.Query query, FirestoreRecyclerAdapter adapter) {
        final FirestoreRecyclerOptions<Personal_Distraction> options = new FirestoreRecyclerOptions.Builder<Personal_Distraction>()
                .setQuery(query, Personal_Distraction.class)
                .build();
        Log.d(TAG, "Enter Db 1 firestore");

        adapter = new FirestoreRecyclerAdapter<Personal_Distraction, ActivityAttention.ViewHolderDistraction>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ActivityAttention.ViewHolderDistraction holder, int position, @NonNull final Personal_Distraction model) {
                holder.mIdDistraction.setText(model.getDistrationName());

                Log.d(TAG, "Enter Db inside Bind");


                holder.chkDistraction.setTag(model.getDistrationName());

            }

            @NonNull
            @Override
            public ActivityAttention.ViewHolderDistraction onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_distractionlist, parent, false);
                return new ActivityAttention.ViewHolderDistraction(view);
            }
        };
        return adapter;
    }

}
