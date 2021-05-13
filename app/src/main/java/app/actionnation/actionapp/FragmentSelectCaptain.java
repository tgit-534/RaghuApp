package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.data.DbHelperClass;
import app.actionnation.actionapp.data.DbHelperClass2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSelectCaptain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSelectCaptain extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageButton imgCancel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirestoreRecyclerAdapter adapter;

    public FragmentSelectCaptain() {
        // Required empty public constructor
    }

    public static FragmentSelectCaptain newInstance(ArrayList<String> captains) {
        FragmentSelectCaptain fragment = new FragmentSelectCaptain();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, captains);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSelectCaptain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSelectCaptain newInstance(String param1, String param2) {
        FragmentSelectCaptain fragment = new FragmentSelectCaptain();
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
    TextView tvCaptainStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_captain, container, false);
        recyclerView = view.findViewById(R.id.recyclerCaptainDialog);
        imgCancel = view.findViewById(R.id.ImgBtnDialogCaptainCancel);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        tvCaptainStatus = view.findViewById(R.id.tv_fm_captainStatus);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);

        fetchChooseGame();

        if (adapter.getItemCount() == 0) {
            tvCaptainStatus.setVisibility(View.VISIBLE);
            tvCaptainStatus.setText("You have no Captains to select!");
            recyclerView.setVisibility(View.INVISIBLE);
        }


       /* BeliefAdapter mAdapter = new BeliefAdapter(ActivityOurBelief.this, res);
        btnBelief.setTag(mAdapter.getItemCount());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));*/


        // fetchChooseCaptain();
        return view;
    }


    private  void fetchChooseGame() {
        FirebaseFirestore db;
        FirebaseAuth mAuth;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();

        Calendar cNew = Calendar.getInstance();

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }
        com.google.firebase.firestore.Query query1 = db.collection(getString(R.string.fs_TeamGame)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);
        //whereLessThan(getString(R.string.fs_TeamGame_StartDate), cNew.getTimeInMillis());

        DbHelperClass2 dbh = new DbHelperClass2();
        adapter = dbh.GetFireStoreAdapterSelectGame(query1);
        adapter.startListening();
        recyclerView.setAdapter(adapter);




    }



    private void fetchChooseCaptain() {
        FirebaseFirestore db;
        FirebaseAuth mAuth;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();

        ArrayList<String> strArrayCaptain = getActivity().getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        com.google.firebase.firestore.Query query1 = db.collection(getString(R.string.fs_UserTeam))
                .whereArrayContains(getString(R.string.fs_UserTeam_teamMembers), mAuth.getCurrentUser().getEmail());

        DbHelperClass dbh = new DbHelperClass();

        adapter = dbh.GetFireStoreAdapterCaptains(adapter, getString(R.string.fs_UserTeam), query1, getContext(), strArrayCaptain, usrId);
        if (adapter.getItemCount() == 0) {
            tvCaptainStatus.setVisibility(View.VISIBLE);
            tvCaptainStatus.setText("You have no Captains to select!");
            recyclerView.setVisibility(View.INVISIBLE);
        }
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

}