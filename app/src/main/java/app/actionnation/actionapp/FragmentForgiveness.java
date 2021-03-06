package app.actionnation.actionapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserStorageGameObject;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentForgiveness#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentForgiveness extends Fragment implements View.OnClickListener {

    Button btn_finish_Forgiveness_Inside, btn_finish_Forgiveness_outside;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerViewEmotions;
    FirebaseAuth mAuth;
    String TAG = "True Learning";
    int dayOfYear, yr;
    private ArrayList<String> strEmotionData = new ArrayList<>();
    LinearLayout linearLayout;


    private FirebaseDatabase mFirebaseDatabase;
    FirebaseRecyclerAdapter fbAdapter;

    public FragmentForgiveness() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentForgiveness.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentForgiveness newInstance(String param1, String param2) {
        FragmentForgiveness fragment = new FragmentForgiveness();
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

        View view = inflater.inflate(R.layout.fragment_forgiveness, container, false);


        //recyclerView = view.findViewById(R.id.listForgiveness);
        recyclerViewEmotions = view.findViewById(R.id.listEmotions);
        btn_finish_Forgiveness_Inside = view.findViewById(R.id.btn_forgive_Self);
        btn_finish_Forgiveness_outside = view.findViewById(R.id.btn_forgive_Outside);
        linearLayout = view.findViewById(R.id.ll_forgiveness);


        btn_finish_Forgiveness_outside.setOnClickListener(this);
        btn_finish_Forgiveness_Inside.setOnClickListener(this);

        CommonClass cls = new CommonClass();


        DbHelper db = new DbHelper(getActivity());

        Log.d(TAG, "Enter Db");


        Cursor res = db.getHappinessScore(fetchUserId(), cls.fetchDate(0), cls.fetchDate(1));
        if (res.getCount() > 0) {

            while (res.moveToNext()) {
                Log.d(TAG, "Enter Db no count inside while" + res.getString(1));

                if (res.getString(3).equals("1")) {

                    btn_finish_Forgiveness_Inside.setTextColor(Color.BLACK);
                }

                if (res.getString(4).equals("1")) {
                    btn_finish_Forgiveness_outside.setTextColor(Color.BLACK);
                }
                strEmotionData.add(res.getString(5));
                strEmotionData.add(res.getString(6));
                strEmotionData.add(res.getString(7));
                strEmotionData.add(res.getString(8));
                strEmotionData.add(res.getString(9));
                strEmotionData.add(res.getString(10));
                strEmotionData.add(res.getString(11));
                strEmotionData.add(res.getString(12));
            }
        }
        res.close();

        CommonClass cl = new CommonClass();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        recyclerViewEmotions.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewEmotions.setHasFixedSize(false);
        fetch(fetchUserId(), strEmotionData);
        return view;
    }

    private void fetch(final String usrId, final ArrayList<String> arrEmotionalData) {
        //final ArrayList<String> strTPattern
        // Query qry = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("status").equalTo(6);


        //recyclerView.setAdapter(fbAdapter);


        Query qry1 = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild("status").equalTo(10);

        FirebaseRecyclerOptions<CommonData> options1 = new FirebaseRecyclerOptions.Builder<CommonData>()
                .setQuery(qry1, CommonData.class)
                .build();

        fbAdapter = new FirebaseRecyclerAdapter<CommonData, ViewHolderEmotions>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderEmotions holder, int position, @NonNull CommonData model) {
                holder.mIdEmotion.setText(model.getDataContent());
                holder.btnAdd.setTag(model.getDataContent());
                holder.btnSubtract.setTag(model.getDataContent());

                String strData = model.getDataContent();


                if (arrEmotionalData.size() > 0) {
                    if (strData.contains(getString(R.string.HP_AngerEmotion))) {
                        holder.midEmotionCountNegative.setText(arrEmotionalData.get(0));

                        holder.midEmotionCountPositive.setText(arrEmotionalData.get(1));
                    } else if (strData.contains(getString(R.string.HP_HateEmotion))) {
                        holder.midEmotionCountNegative.setText(arrEmotionalData.get(2));

                        holder.midEmotionCountPositive.setText(arrEmotionalData.get(3));
                    } else if (strData.contains(getString(R.string.HP_SadEmotion))) {
                        holder.midEmotionCountNegative.setText(arrEmotionalData.get(4));

                        holder.midEmotionCountPositive.setText(arrEmotionalData.get(5));
                    } else if (strData.contains(getString(R.string.HP_EnvyEmotion))) {
                        holder.midEmotionCountNegative.setText(arrEmotionalData.get(6));

                        holder.midEmotionCountPositive.setText(arrEmotionalData.get(7));
                    }
                }


                holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass cls = new CommonClass();

                        Calendar c = Calendar.getInstance();
                        dayOfYear = c.get(Calendar.DAY_OF_YEAR);
                        yr = c.get(Calendar.YEAR);
                        DbHelper db = new DbHelper(getActivity());
                        int returnData = 0;

                        String btnAddTag = holder.btnAdd.getTag().toString();

                        if (btnAddTag.contains(getString(R.string.HP_AngerEmotion))) {
                            returnData = cls.SubmitHappinessGame(Constants.HP_AngerExplode, db, usrId, dayOfYear, yr);

                        } else if (btnAddTag.contains(getString(R.string.HP_EnvyEmotion))) {
                            returnData = cls.SubmitHappinessGame(Constants.HP_EnvyExplode, db, usrId, dayOfYear, yr);

                        } else if (btnAddTag.contains(getString(R.string.HP_HateEmotion))) {
                            returnData = cls.SubmitHappinessGame(Constants.HP_HateExplode, db, usrId, dayOfYear, yr);

                        } else if (btnAddTag.contains(getString(R.string.HP_SadEmotion))) {
                            returnData = cls.SubmitHappinessGame(Constants.HP_SadExplode, db, usrId, dayOfYear, yr);

                        }
                        holder.midEmotionCountPositive.setText(String.valueOf(returnData));


                    }
                });


                holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass cls = new CommonClass();

                        Calendar c = Calendar.getInstance();
                        dayOfYear = c.get(Calendar.DAY_OF_YEAR);
                        yr = c.get(Calendar.YEAR);
                        DbHelper db = new DbHelper(getActivity());
                        int returnData = 0;

                        String btnAddTag = holder.btnAdd.getTag().toString();

                        if (btnAddTag.contains(getString(R.string.HP_AngerEmotion))) {
                            returnData = cls.SubmitHappinessGame(Constants.HP_AngerControl, db, usrId, dayOfYear, yr);

                        } else if (btnAddTag.contains(getString(R.string.HP_EnvyEmotion))) {
                            returnData = cls.SubmitHappinessGame(Constants.HP_EnvyControl, db, usrId, dayOfYear, yr);

                        } else if (btnAddTag.contains(getString(R.string.HP_HateEmotion))) {
                            returnData = cls.SubmitHappinessGame(Constants.HP_HateControl, db, usrId, dayOfYear, yr);

                        } else if (btnAddTag.contains(getString(R.string.HP_SadEmotion))) {
                            returnData = cls.SubmitHappinessGame(Constants.HP_SadControl, db, usrId, dayOfYear, yr);

                        }
                        holder.midEmotionCountNegative.setText(String.valueOf(returnData));

                    }
                });

            }

            @NonNull
            @Override
            public ViewHolderEmotions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_emotions, parent, false);
                ViewHolderEmotions viewHolder = new ViewHolderEmotions(view);
                return viewHolder;
            }
        };
        recyclerViewEmotions.setAdapter(fbAdapter);


        fbAdapter.startListening();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        CommonClass cls = new CommonClass();

        ArrayList<String> userArray = cls.fetchUserArray(FirebaseAuth.getInstance());
        String usrId = userArray.get(0);
        String userName = userArray.get(1);
        Calendar c = Calendar.getInstance();

        dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        yr = c.get(Calendar.YEAR);
        DbHelper db = new DbHelper(getActivity());
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
        userStorageGameObject.setGameDocumentId(getActivity().getIntent().getStringExtra(Constants.Intent_GameDocumentId));
        userStorageGameObject.setUserCoinsPerDay(getActivity().getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
        userStorageGameObject.setUserExellenceBar(getActivity().getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));

        UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr, userName, userStorageGameObject);

        DbHelperClass dbHelperClass = new DbHelperClass();

        int totalGameScore = 0;
        ArrayList<Integer> arrayGameScore = new ArrayList<>();
        if (btn_finish_Forgiveness_Inside.getTag() == null) {
            arrayGameScore = getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));
            btn_finish_Forgiveness_Inside.setTag(arrayGameScore);
        } else {
            arrayGameScore = (ArrayList<Integer>) btn_finish_Forgiveness_Inside.getTag();
        }


        if (i == R.id.btn_forgive_Self) {
            btn_finish_Forgiveness_Inside.setTextColor(Color.BLACK);
            cls.SubmitHappinessGame(Constants.HP_ForgiveInsideScore, db, usrId, dayOfYear, yr);
            userGame.setUserForgivenessSelfScore(Constants.Game_Forgiveness_Self);


            ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserForgivenessSelfScore, Constants.Game_Forgiveness_Self, arrayGameScore, userGame, getContext());

            if (arrayNewGameScore.size() == 20) {
                userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                arrayGameScore = arrayNewGameScore;
                totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
            } else {
                userGame.setUserTotatScore(Constants.Game_Forgiveness_Self);
                totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

            }
            btn_finish_Forgiveness_Inside.setTag(arrayGameScore);

            dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), getContext(), userGame, rootRef, getString(R.string.fs_Usergame_userForgivenessSelfScore), Constants.Game_Forgiveness_Self, totalGameScore);
            cls.makeSnackBar(linearLayout);

        } else if (i == R.id.btn_forgive_Outside) {
            btn_finish_Forgiveness_outside.setTextColor(Color.BLACK);
            cls.SubmitHappinessGame(Constants.HP_ForgiveOutsideScore, db, usrId, dayOfYear, yr);
            userGame.setUserForgivenessOutsideScore(Constants.Game_Forgiveness_Outside);

            ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserForgivenessOutsideScore, Constants.Game_Forgiveness_Outside, arrayGameScore, userGame, getContext());

            if (arrayNewGameScore.size() == 20) {
                userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                arrayGameScore = arrayNewGameScore;
                totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
            } else {
                userGame.setUserTotatScore(Constants.Game_Forgiveness_Outside);
                totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

            }
            btn_finish_Forgiveness_Inside.setTag(arrayGameScore);

            dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), getContext(), userGame, rootRef, getString(R.string.fs_Usergame_userForgivenessOutsideScore), Constants.Game_Forgiveness_Outside, totalGameScore);
            cls.makeSnackBar(linearLayout);

        }
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


    public static class ViewHolderEmotions extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdEmotion, midEmotionCountNegative, midEmotionCountPositive;
        public final Button btnAdd, btnSubtract;

        public Personal_Distraction mItem;

        public ViewHolderEmotions(View view) {
            super(view);
            mView = view;
            mIdEmotion = view.findViewById(R.id.txtEmotion);
            btnAdd = view.findViewById(R.id.btnPlusEmotion);
            btnSubtract = view.findViewById(R.id.btnMinusEmotion);
            midEmotionCountNegative = view.findViewById(R.id.emotionCountNegative);
            midEmotionCountPositive = view.findViewById(R.id.emotionCountPositive);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdEmotion.getText() + "'";
        }
    }
}