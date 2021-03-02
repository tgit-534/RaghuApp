package app.actionnation.actionapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
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
 * Use the {@link FragmentTraction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTraction extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnTractionList, btnSubmitTractionScore;
    RecyclerView recyclerView;
    ArrayList<String> strTractionPattern = new ArrayList<>();
    FirebaseAuth mAuth;
    String usrId;
    FirebaseUser fbUser;

    public FragmentTraction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTraction.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTraction newInstance(String param1, String param2) {
        FragmentTraction fragment = new FragmentTraction();
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


        View view = inflater.inflate(R.layout.fragment_traction, container, false);
        btnTractionList = view.findViewById(R.id.btn_TractionList);
        btnTractionList.setOnClickListener(this);

        btnSubmitTractionScore = view.findViewById(R.id.btn_traction_Submit);
        btnSubmitTractionScore.setOnClickListener(this);

        Calendar c = Calendar.getInstance();

        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        DbHelper db = new DbHelper(getActivity());

        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        Cursor res = db.getTractionList(usrId);
        Cursor res1 = db.getTractionDayList(usrId, dayOfYear);
        res1.moveToFirst();
        if (res1.getCount() > 0) {
            while (res1.moveToNext()) {


                if (res1.getString(3).equals("1")) {
                    strTractionPattern.add(res1.getString(2));
                }
            }
        }
        res.moveToFirst();
        recyclerView = view.findViewById(R.id.listTraction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TractionAdapter mAdapter = new TractionAdapter(getActivity(), res, usrId, strTractionPattern);
        recyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btn_TractionList) {
            Intent homepage = new Intent(getActivity(), HappinessContent.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
            mBundle.putString(getString(R.string.Page_Redirect), getString(R.string.Page_Redirect_Traction));
            homepage.putExtras(mBundle);
            startActivity(homepage);
        }
        if (i == R.id.btn_traction_Submit) {

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            CommonClass cls = new CommonClass();
            Calendar c = Calendar.getInstance();

            int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
            int yr = c.get(Calendar.YEAR);
            String usrId = fetchUserId(FirebaseAuth.getInstance());

            DbHelper db = new DbHelper(getActivity());
            Cursor cus = db.getAttentionScore(usrId, dayOfYear, yr);

            int attentionScore = 0;
            int attentionTot = 0;
            if (cus.getCount() > 0) {
                cus.moveToFirst();

                attentionScore = Integer.parseInt(cus.getString(Constants.Game_AS_TractionScore));
                attentionTot = Integer.parseInt(cus.getString(Constants.Game_AS_TotTraction));

            }
            double gameDistractionScore = (double) attentionScore / attentionTot;

            gameDistractionScore = gameDistractionScore * 100;

            int databaseScore = (int) gameDistractionScore;


            ArrayList<String> arrayCaptains = getActivity().getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
            UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr, arrayCaptains);
            userGame.setUserTractionScore(databaseScore);

            DbHelperClass dbHelperClass = new DbHelperClass();

            dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), getContext(), userGame, rootRef, getString(R.string.fs_Usergame_userTractionScore), databaseScore);
        }
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