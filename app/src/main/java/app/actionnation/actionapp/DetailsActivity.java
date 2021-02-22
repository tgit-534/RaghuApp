
package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import app.actionnation.actionapp.Database_Content.Category;
import app.actionnation.actionapp.Database_Content.Challenges;
import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.Database_Content.Education;
import app.actionnation.actionapp.Database_Content.leadershipprogram;

public class DetailsActivity extends AdminCommonClass {
    Intent intent;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference myRef;
    private RecyclerView recyclerView;

    private static final String TAG = "Db_Firebase";
    Button btnAdmin;
    Spinner spinner;
    FirebaseRecyclerAdapter fbAdapter;
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        spinner = findViewById(R.id.SpnDifferent);
        generatePublicMenu();


        String[] data = {getString(R.string.fb_Challenges),
                getString(R.string.fb_Education),
                getString(R.string.fb_category),
                getString(R.string.fb_leadershipProg),
                getString(R.string.fb_CommonData_Db)
                
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsActivity.this, android.R.layout.simple_spinner_item, data);

        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnAdmin = findViewById(R.id.btnAdminDA);

        recyclerView = findViewById(R.id.editDataRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
        recyclerView.setHasFixedSize(true);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equals(getString(R.string.fb_leadershipProg))) {
                    updataLeadershipProgram(myRef.child(getString(R.string.fb_leadershipProg)));
                } else if (selectedItem.equals(getString(R.string.fb_category))) {

                } else if (selectedItem.equals(getString(R.string.fb_Education))) {
                    updataEducation(myRef.child(getString(R.string.fb_Education)));
                } else if (selectedItem.equals(getString(R.string.fb_CommonData_Db))) {
                    updateCommonData(myRef.child(getString(R.string.fb_CommonData_Db)));
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       //    userID = user.getUid();


        //   lv.setAdapter(adapter);
        Button back = (Button) findViewById(R.id.btnAdminDA);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailsActivity.this, DbMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void passRef(DatabaseReference myRef, final String refChild) {
        Bundle extras = getIntent().getExtras();

        //Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_Challenges)).orderByChild("edu_Id").equalTo(value);


    }


    public void updataChallenges(DatabaseReference dbRef, String eduid) {

        Query qry = dbRef.orderByChild(getString(R.string.fb_eduId_ChallengesTable)).equalTo(eduid);

        FirebaseRecyclerOptions<Challenges> options = new FirebaseRecyclerOptions.Builder<Challenges>()
                .setQuery(qry, Challenges.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<Challenges, FindViewHolderEdit>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FindViewHolderEdit holder, int position, @NonNull Challenges model) {
                holder.mContentView.setText((model.getChallenge_Name()));
                holder.mContentButton.setTag(fbAdapter.getRef(position).getKey());
                holder.mContentId.setText(fbAdapter.getRef(position).getKey());
                holder.mContentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent homepage = new Intent(DetailsActivity.this, Admin_Challenges.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString(getString(R.string.Intent_GetChallengeId), holder.mContentButton.getTag().toString());
                        homepage.putExtras(mBundle);
                        startActivity(homepage);
                    }
                });

            }

            @NonNull
            @Override
            public FindViewHolderEdit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_edit_layout, parent, false);
                FindViewHolderEdit viewHolder = new FindViewHolderEdit(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }

    public void updataEducation(DatabaseReference dbRef) {
        Query qry = dbRef;

        FirebaseRecyclerOptions<Education> options = new FirebaseRecyclerOptions.Builder<Education>()
                .setQuery(qry, Education.class)
                .build();
        fbAdapter = new FirebaseRecyclerAdapter<Education, FindViewHolderEdit>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FindViewHolderEdit holder, int position, @NonNull Education model) {
                holder.mContentView.setText((model.getEdu_name()));
                holder.mContentButton.setTag(model.getStatus());
                holder.mContentId.setText(fbAdapter.getRef(position).getKey());
                holder.mContentChallenges.setTag(fbAdapter.getRef(position).getKey());
                if (model.getStatus() == Integer.parseInt(getString(R.string.AE_EduArea_Inspire_Number))) {
                    holder.mContentChallenges.setText(getString(R.string.AE_EduArea_Inspire));
                }

                holder.mContentChallenges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Integer.parseInt(holder.mContentButton.getTag().toString()) == Integer.parseInt(getString(R.string.AE_EduArea_Inspire_Number))) {
                            Intent homepage = new Intent(DetailsActivity.this, adminleadership.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putString(getString(R.string.Intent_EduId), holder.mContentChallenges.getTag().toString());
                            homepage.putExtras(mBundle);
                            startActivity(homepage);
                        } else {
                            updataChallenges(myRef.child(getString(R.string.fb_Challenges)), holder.mContentChallenges.getTag().toString());

                        }
                    }
                });
                holder.mContentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent homepage = new Intent(DetailsActivity.this, Admin_Education.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString(getString(R.string.Intent_EduId), holder.mContentChallenges.getTag().toString());
                        homepage.putExtras(mBundle);
                        startActivity(homepage);

                    }
                });
            }

            @NonNull
            @Override
            public FindViewHolderEdit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_edit_layout, parent, false);
                FindViewHolderEdit viewHolder = new FindViewHolderEdit(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();

    }

    public void updateCommonData(DatabaseReference dbRef) {
        FirebaseRecyclerOptions<CommonData> options = new FirebaseRecyclerOptions.Builder<CommonData>()
                .setQuery(dbRef, CommonData.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<CommonData, FindViewHolderEdit>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FindViewHolderEdit holder, int position, @NonNull CommonData model) {
                holder.mContentView.setText(String.valueOf(model.getDataNumber()));
                holder.mContentButton.setTag(fbAdapter.getRef(position).getKey());
                holder.mContentId.setText(model.getDataString());

                holder.mContentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent homepage = new Intent(DetailsActivity.this, AdminActionQuestionnare.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putString(getString(R.string.Intent_CommonDataId), holder.mContentButton.getTag().toString());
                        homepage.putExtras(mBundle);
                        startActivity(homepage);
                    }
                });

            }


            @NonNull
            @Override
            public FindViewHolderEdit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_edit_layout, parent, false);
                FindViewHolderEdit viewHolder = new FindViewHolderEdit(view);
                return viewHolder;
            }
        };

        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }


    public void updataLeadershipProgram(DatabaseReference dbRef) {
        FirebaseRecyclerOptions<leadershipprogram> options = new FirebaseRecyclerOptions.Builder<leadershipprogram>()
                .setQuery(dbRef, leadershipprogram.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<leadershipprogram, FindViewHolderEdit>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FindViewHolderEdit holder, int position, @NonNull leadershipprogram model) {
                holder.mContentView.setText((model.getLeadership_name()));
                holder.mContentButton.setTag(fbAdapter.getRef(position).getKey());
                holder.mContentId.setText(fbAdapter.getRef(position).getKey());
            }


            @NonNull
            @Override
            public FindViewHolderEdit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_edit_layout, parent, false);
                FindViewHolderEdit viewHolder = new FindViewHolderEdit(view);
                return viewHolder;
            }
        };

        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAuthListner != null) {
            mAuth.addAuthStateListener(mAuthListner);
        }
    }

    @Override
    public void onStop() {

        super.onStop();

        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }

    /**
     * customizable toast
     *
     * @param message
     */

    private void toastMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    public static class FindViewHolderEdit extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mContentId;
        public final Button mContentButton, mContentChallenges;

        public Challenges mItem;

        public FindViewHolderEdit(View view) {
            super(view);
            mView = view;

            mContentView = view.findViewById(R.id.editName);
            mContentId = view.findViewById(R.id.editId);
            mContentButton = view.findViewById(R.id.btnAdminEdit);
            mContentChallenges = view.findViewById(R.id.btnAdminEditChallenges);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}

