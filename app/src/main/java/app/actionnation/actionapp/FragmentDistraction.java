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

        CommonClass cl = new CommonClass();

        Calendar c = Calendar.getInstance();

        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        DbHelper db = new DbHelper(getActivity());

        Log.d(TAG, "Enter Db");

        Cursor res = db.getDataAttention(String.valueOf(dayOfYear));
        if (res.getCount() == 0) {
            Log.d(TAG, "Enter Db no count");
            // return;
        } else {
            while (res.moveToNext()) {
                Log.d(TAG, "Enter Db no count inside while" + res.getString(4));

                if (res.getString(4).equals("1")) {
                    strAttPattern.add(res.getString(2));
                    Log.d(TAG, "Enter Db");
                }
            }
        }

        mAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.listDistraction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);

        Log.d(TAG, "Enter Db fetch");

        fetch();
        btnDistractionList = view.findViewById(R.id.btn_distractionlist);
        btnSubmit = view.findViewById(R.id.btn_att_Submit);

        btnSubmit.setOnClickListener(this);

        btnDistractionList.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_att_Submit) {

        } else if (i == R.id.btn_distractionlist) {
            Intent homepage = new Intent(getActivity(), PersondetailsActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
            mBundle.putString(getString(R.string.Page_Redirect), getString(R.string.Page_Redirect_Attention));
            homepage.putExtras(mBundle);
            startActivity(homepage);
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
        adapter.startListening();
    }


}