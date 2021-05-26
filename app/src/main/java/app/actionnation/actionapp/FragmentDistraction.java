package app.actionnation.actionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserStorageGameObject;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDistraction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDistraction extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Button btnSubmit, btnDistractionList;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    ArrayList<String> strAttPattern = new ArrayList<>();
    String TAG = "attention";
    LinearLayout linearLayout;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentDistraction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDistraction.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDistraction newInstance(String param1, String param2) {
        FragmentDistraction fragment = new FragmentDistraction();
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

        View view = inflater.inflate(R.layout.fragment_distraction, container, false);
        btnDistractionList = view.findViewById(R.id.btn_distractionlist);
        linearLayout = view.findViewById(R.id.ll_distraction);


        CommonClass cl = new CommonClass();

        Calendar c = Calendar.getInstance();

        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);

        DbHelper db = new DbHelper(getActivity());

        Log.d(TAG, "Enter Db");

        ArrayList<Integer> arrayGameScore = getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

        String usrId = fetchUserId(FirebaseAuth.getInstance());

        Cursor res = db.getDataAttention(String.valueOf(dayOfYear), usrId, String.valueOf(yr));
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                Log.d(TAG, "Enter Db no count inside while" + res.getString(4));

                if (res.getString(4).equals("1")) {
                    strAttPattern.add(res.getString(2));
                    Log.d(TAG, "Enter Db");
                }
            }
        }
        res.close();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.listDistraction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);

        Log.d(TAG, "Enter Db fetch");

        fetch();

        btnDistractionList.setOnClickListener(this);

        return view;
    }

    public void submitWin() {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CommonClass cls = new CommonClass();
        Calendar c = Calendar.getInstance();

        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);
        ArrayList<String> userArray = cls.fetchUserArray(FirebaseAuth.getInstance());
        String usrId = userArray.get(0);
        String userName = userArray.get(1);
        DbHelper db = new DbHelper(getActivity());
        Cursor cus = db.getAttentionScore(usrId, dayOfYear, yr);
        int totalGameScore = 0;

        int attentionScore = 0;
        int attentionTot = 0;
        if (cus.getCount() > 0) {
            cus.moveToFirst();

            attentionScore = Integer.parseInt(cus.getString(Constants.Game_AS_DistractionScore));
            attentionTot = Integer.parseInt(cus.getString(Constants.Game_AS_TotDistraction));

        }
        cus.close();
        double gameDistractionScore = (double) (attentionTot - attentionScore) / attentionTot;

        gameDistractionScore = gameDistractionScore * 100;

        int databaseScore = (int) gameDistractionScore;
        if (databaseScore == 0)
            databaseScore = 100;

        Log.d(TAG, "Enter Db");

        ArrayList<String> arrayCaptains = getActivity().getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
        UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
        userStorageGameObject.setGameDocumentId(getActivity().getIntent().getStringExtra(Constants.Intent_GameDocumentId));
        userStorageGameObject.setUserCoinsPerDay(getActivity().getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
        userStorageGameObject.setUserExellenceBar(getActivity().getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));



        UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr, arrayCaptains, userName, userStorageGameObject);

        userGame.setUserDistractionScore(databaseScore);

        ArrayList<Integer> arrayGameScore = getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

        ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserDistractionScore, databaseScore, arrayGameScore, userGame, getContext());

        if (arrayNewGameScore.size() == 20) {
            userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
            arrayGameScore = arrayNewGameScore;
            totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
        } else {
            userGame.setUserTotatScore(databaseScore);
            totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

        }

        DbHelperClass dbHelperClass = new DbHelperClass();

        dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), getContext(), userGame, rootRef, getString(R.string.fs_Usergame_userDistractionScore), databaseScore, totalGameScore);
        cls.makeSnackBar(linearLayout);
    }


    private void showEditDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentDataInsertion editNameFragment = FragmentDataInsertion.newInstance(getString(R.string.Page_Redirect_Attention));
        editNameFragment.show(fm, "fragment_edit_name");

        editNameFragment.onCancel(new DialogInterface() {
            @Override
            public void cancel() {

            }

            @Override
            public void dismiss() {
                Intent i = new Intent(getContext(), ActivityAttention.class);

                Bundle mBundle = new Bundle();
                mBundle.putString(Constants.common_auth, Constants.common_google);
                mBundle.putStringArrayList(Constants.Intent_ArrayCaptain, getActivity().getIntent().getStringArrayListExtra(((getString(R.string.Intent_ArrayCaptain)))));
                mBundle.putIntegerArrayList(Constants.Intent_ArrayGameScore, getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore))));
                i.putExtras(mBundle);
                getContext().startActivity(i);


            }
        });
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_distractionlist) {
            showEditDialog();
        }

    }


    private void fetch() {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_PersonalDistraction)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);

        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterDistraction(adapter, getString(R.string.fs_PersonalDistraction), query1, getActivity(), strAttPattern, usrId);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    private String fetchUserId(FirebaseAuth mAuth) {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        return usrId;
    }


}