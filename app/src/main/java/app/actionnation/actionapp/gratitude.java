package app.actionnation.actionapp;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gratitude#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gratitude extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String TAG = "Gratitude";
    int dayOfYear, yr;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth mAuth;
    DbHelper db;


    Button btnFinish;

    public gratitude() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gratitude.
     */
    // TODO: Rename and change types and number of parameters
    public static gratitude newInstance(String param1, String param2) {
        gratitude fragment = new gratitude();
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

    RecyclerView recyclerView;
    Button btnGratitude;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gratitude, container, false);

        btnFinish = view.findViewById(R.id.btn_happiness_finish);
        linearLayout = view.findViewById(R.id.ll_gratitude);

        db = new DbHelper(getActivity());


        Calendar c = Calendar.getInstance();

        dayOfYear = c.get(Calendar.DAY_OF_YEAR);


        Cursor res = db.getGratitudeDayList(String.valueOf(dayOfYear));
        if (res.getCount() > 0) {
            while (res.moveToNext()) {

                if (res.getString(3).equals("1")) {
                    strAttPattern.add(res.getString(1));
                    Log.d(TAG, "Enter Db");
                }
            }
        }

        recyclerView = view.findViewById(R.id.listGratitude);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GratitudeAdapter mAdapter = new GratitudeAdapter(getActivity(), getAllItems(), strAttPattern);
        recyclerView.setAdapter(mAdapter);
        btnGratitude = view.findViewById(R.id.gt_btn_gratitudelist);


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonClass cls = new CommonClass();

                Calendar c = Calendar.getInstance();

                dayOfYear = c.get(Calendar.DAY_OF_YEAR);
                yr = c.get(Calendar.YEAR);
                ArrayList<String> userArray = cls.fetchUserArray(FirebaseAuth.getInstance());
                String usrId = userArray.get(0);
                String userName = userArray.get(1);


                cls.SubmitHappinessGame(Constants.HP_GratitudeScore, db, usrId, dayOfYear, yr);

                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

                ArrayList<String> arrayCaptains = getActivity().getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
                UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr, arrayCaptains, userName);

                userGame.setUserGratitudeScore(Constants.Game_Gratitude);

                int totalGameScore = 0;
                ArrayList<Integer> arrayGameScore = getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserGratitudeScore, Constants.Game_Gratitude, arrayGameScore, userGame, getContext());

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(Constants.Game_Gratitude);
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

                }
                DbHelperClass dbHelperClass = new DbHelperClass();

                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), getContext(), userGame, rootRef, getString(R.string.fs_Usergame_userGratitudeScore), Constants.Game_Gratitude, totalGameScore);
                cls.makeSnackBar(linearLayout);
            }


        });

        btnGratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();

                /*Intent homepage = new Intent(getActivity(), HappinessContent.class);
                Bundle mBundle = new Bundle();
                mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                mBundle.putString(getString(R.string.Page_Redirect), getString(R.string.Page_Redirect_Gratitude));
                homepage.putExtras(mBundle);
                startActivity(homepage);*/
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    private void showEditDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentDataInsertion editNameFragment = FragmentDataInsertion.newInstance(getString(R.string.Page_Redirect_Gratitude));
        editNameFragment.show(fm, "fragment_edit_name");
    }

    ArrayList<String> strAttPattern = new ArrayList<>();

    private Cursor getAllItems() {
        DbHelper dbHelper = new DbHelper(getActivity());
        Cursor res = dbHelper.getGratitudeList();
        res.moveToFirst();
        return res;
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