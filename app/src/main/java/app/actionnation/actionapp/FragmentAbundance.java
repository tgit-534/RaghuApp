package app.actionnation.actionapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
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
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAbundance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAbundance extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Button btnSubmit, btnAbundanceList;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    ArrayList<String> strAttPattern = new ArrayList<>();
    String TAG = "attention";

    public FragmentAbundance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAbundance.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAbundance newInstance(String param1, String param2) {
        FragmentAbundance fragment = new FragmentAbundance();
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

        View view = inflater.inflate(R.layout.fragment_abundance, container, false);

        recyclerView = view.findViewById(R.id.listAbundance);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AbundanceAdapter mAdapter = new AbundanceAdapter(getActivity(), getAllItems());
        recyclerView.setAdapter(mAdapter);

        Log.d(TAG, "Enter Db fetch");
        // fetch();
        btnAbundanceList = view.findViewById(R.id.btn_abundancelist);
        btnSubmit = view.findViewById(R.id.btn_abundance_Finish);
        btnSubmit.setOnClickListener(this);
        btnAbundanceList.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        CommonClass cls = new CommonClass();
        String usrId = fetchUserId();
        Calendar c = Calendar.getInstance();
        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);
        DbHelper db = new DbHelper(getActivity());
        if (i == R.id.btn_abundance_Finish) {
            cls.SubmitHappinessGame(Constants.HP_AbundanceScore, db, usrId, dayOfYear, yr);

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

            UserGame userGame = cls.loadUserGame(usrId, dayOfYear, yr);
            userGame.setUserAbundanceScore(Constants.Game_Abundance);
            DbHelperClass dbHelperClass = new DbHelperClass();
            dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), getContext(), userGame, rootRef, getString(R.string.fs_Usergame_userAbundanceScore), String.valueOf(Constants.Game_Abundance));

        } else if (i == R.id.btn_abundancelist) {
            Intent homepage = new Intent(getActivity(), HappinessContent.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
            mBundle.putString(getString(R.string.Page_Redirect), getString(R.string.Page_Redirect_Abundance));
            homepage.putExtras(mBundle);
            startActivity(homepage);
        }
    }

    private Cursor getAllItems() {
        DbHelper dbHelper = new DbHelper(getActivity());
        Cursor res = dbHelper.getAbundance(fetchUserId());
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