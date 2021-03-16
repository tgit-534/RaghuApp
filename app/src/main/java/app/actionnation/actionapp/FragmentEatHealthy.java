package app.actionnation.actionapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEatHealthy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEatHealthy extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    Button btn_Submit_Score;
    FirebaseAuth mAuth;
    String usrId;
    ArrayList<String> strEHPattern = new ArrayList<>();
    String TAG = "Fragment Eat Healthy";
    int ehEatStatus;

    private FirebaseDatabase mFirebaseDatabase;
    FirebaseRecyclerAdapter fbAdapter;


    public FragmentEatHealthy() {
        // Required empty public constructor
    }

    public FragmentEatHealthy(int EatStatus) {
        ehEatStatus = EatStatus;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEatHealthy.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEatHealthy newInstance(String param1, String param2) {
        FragmentEatHealthy fragment = new FragmentEatHealthy();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eat_healthy, container, false);

        recyclerView = view.findViewById(R.id.listEatHealthy);
        btn_Submit_Score = view.findViewById(R.id.btn_EatHealthy_Submit);

        btn_Submit_Score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                CommonClass cls = new CommonClass();
                Calendar c = Calendar.getInstance();

                int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
                int yr = c.get(Calendar.YEAR);
                ArrayList<String> userArray = cls.fetchUserArray(FirebaseAuth.getInstance());
                usrId = userArray.get(0);
                String userName = userArray.get(1);
                DbHelper db = new DbHelper(getActivity());
                Cursor cus = db.getEatHealthyScore(usrId, dayOfYear, yr);
                String strDataVariable = "";
                int healthGamePointer = 0;

                double gameEatFoodScore = 0;
                int eatScore = 0;
                int EatScoreTot = 0;
                if (cus.getCount() > 0) {
                    cus.moveToFirst();

                    ArrayList<String> arrayCaptains = getActivity().getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
                    UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr, arrayCaptains, userName);
                    if (ehEatStatus == Constants.aaq_EatHealthy_Number) {
                        eatScore = Integer.parseInt(cus.getString(Constants.Game_AS_EatFoodScore));
                        EatScoreTot = Integer.parseInt(cus.getString(Constants.Game_AS_TotEatFoodScore));
                        strDataVariable = getString(R.string.fs_Usergame_userEatHealthyScore);


                        gameEatFoodScore = (double) (eatScore) / EatScoreTot;
                        gameEatFoodScore = gameEatFoodScore * 100;
                        userGame.setUserEatHealthyScore((int) gameEatFoodScore);
                        healthGamePointer = Constants.Game_CP__UserEatHealthyScore;

                    } else if (ehEatStatus == Constants.aaq_AvoidFood_Number) {
                        eatScore = Integer.parseInt(cus.getString(Constants.Game_AS_AvoidFoodScore));
                        EatScoreTot = Integer.parseInt(cus.getString(Constants.Game_AS_TotAvoidFoodScore));
                        strDataVariable = getString(R.string.fs_Usergame_userAvoidfoodScore);

                        gameEatFoodScore = (double) (eatScore) / EatScoreTot;
                        gameEatFoodScore = gameEatFoodScore * 100;

                        userGame.setUserAvoidForHealthScore((int) gameEatFoodScore);
                        healthGamePointer = Constants.Game_CP__UserAvoidForHealthScore;

                    }

                    int totalGameScore = 0;
                    ArrayList<Integer> arrayGameScore = getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                    ArrayList<Integer> arrayNewGameScore = cls.createGameScore(healthGamePointer, (int) gameEatFoodScore, arrayGameScore, userGame, getContext());

                    if (arrayNewGameScore.size() == 20) {
                        userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                        arrayGameScore = arrayNewGameScore;
                        totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                    } else {
                        userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Status_Zero));
                        totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);
                    }

                    DbHelperClass dbHelperClass = new DbHelperClass();
                    dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), getContext(), userGame, rootRef, strDataVariable, (int) gameEatFoodScore, totalGameScore);
                }


            }
        });


        usrId = fetchUserId();
        Calendar c = Calendar.getInstance();

        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);
        DbHelper db = new DbHelper(getActivity());

        Log.d(TAG, "Enter Db");

        Cursor res = db.getEatHealthyList(usrId, dayOfYear, yr);
        if (res != null) {
            if (res.getCount() > 0) {

                while (res.moveToNext()) {
                    Log.d(TAG, "Enter Db no count inside while" + res.getString(1));

                    if (res.getString(3).equals("1")) {
                        strEHPattern.add(res.getString(2));
                        Log.d(TAG, "Enter Db");
                    }
                }
            }
        }

        CommonClass cl = new CommonClass();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        fetch(strEHPattern, ehEatStatus);
        return view;
    }


    private void fetch(final ArrayList<String> strEPattern, final int eatStatus) {

        // Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("status").equalTo(6);

        Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("status").equalTo(ehEatStatus);

        FirebaseRecyclerOptions<CommonData> options = new FirebaseRecyclerOptions.Builder<CommonData>()
                .setQuery(qry, CommonData.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<CommonData, ActivityEatHealthy.ViewHolderEatHealthy>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ActivityEatHealthy.ViewHolderEatHealthy holder, int position, @NonNull CommonData model) {
                holder.mId.setText(model.getDataContent());
                if (strEPattern != null) {
                    if (strEPattern.contains(model.getDataContent())) {
                        Log.d(TAG, "Enter Checked changed no count inside while");

                        holder.chk.setChecked(true);
                        Log.d(TAG, "Enter Checked changed no count inside while After Checked + " + model.getDataContent());

                    }
                }

                holder.chk.setTag(model.getDataContent());

                holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        CommonClass cls = new CommonClass();
                        Calendar cal = Calendar.getInstance();
                        int dayOfTheYear = cal.get(Calendar.DAY_OF_YEAR);
                        int yr = cal.get(Calendar.YEAR);

                        DbHelper db = new DbHelper(getActivity());
                        if (isChecked == true) {
                            db.insertEatHealthyList(holder.chk.getTag().toString(), 1, usrId, dayOfTheYear, yr);

                            if (eatStatus == Constants.aaq_EatHealthy_Number)
                                cls.InsertEatHealthyScore(db, usrId, dayOfTheYear, yr, Constants.Game_CommonScore, 0, fbAdapter.getSnapshots().size(), 0);
                            else if (eatStatus == Constants.aaq_AvoidFood_Number)
                                cls.InsertEatHealthyScore(db, usrId, dayOfTheYear, yr,
                                        0, Constants.Game_CommonScore,
                                        0, fbAdapter.getSnapshots().size());
                        } else {
                            db.updateEatHealthyList(holder.chk.getTag().toString(), 0, usrId, dayOfTheYear, yr);

                            if (eatStatus == Constants.aaq_EatHealthy_Number)
                                cls.InsertEatHealthyScore(db, usrId, dayOfTheYear, yr, Constants.Game_CommonScore_Negative, 0, fbAdapter.getSnapshots().size(), 0);
                            else if (eatStatus == Constants.aaq_AvoidFood_Number)
                                cls.InsertEatHealthyScore(db, usrId, dayOfTheYear, yr,
                                        0, Constants.Game_CommonScore_Negative,
                                        0, fbAdapter.getSnapshots().size());
                        }
                    }

                });
            }

            @NonNull
            @Override
            public ActivityEatHealthy.ViewHolderEatHealthy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_distractionlist, parent, false);
                ActivityEatHealthy.ViewHolderEatHealthy viewHolder = new ActivityEatHealthy.ViewHolderEatHealthy(view);
                return viewHolder;
            }
        };
        recyclerView.setAdapter(fbAdapter);
        fbAdapter.startListening();
    }

    protected String fetchUserId() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        return usrId;
    }
}