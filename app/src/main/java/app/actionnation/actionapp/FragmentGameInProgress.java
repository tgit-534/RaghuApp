package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.adapters.ValidationData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGameInProgress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGameInProgress extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Controls Intialization
    TextView tvGameName, tvGameStartDate, tvGameEndDate, tvStatus;
    Button btnAddPlayers;
    FirebaseAuth mAuth;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentGameInProgress() {
        // Required empty public constructor
    }

    public FragmentGameInProgress(String gameDocumentId) {
        // Required empty public constructor
        mParam1 = gameDocumentId;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGameInProgress.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGameInProgress newInstance(String param1, String param2) {
        FragmentGameInProgress fragment = new FragmentGameInProgress();
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
        View view = inflater.inflate(R.layout.fragment_game_in_progress, container, false);
        tvGameName = view.findViewById(R.id.tv_fg_gameInProgress_gameName);
        tvGameStartDate = view.findViewById(R.id.tv_fg_gameInProgress_gameStartDate);
        tvGameEndDate = view.findViewById(R.id.tv_fg_gameInProgress_gameEndDate);
        btnAddPlayers = view.findViewById(R.id.btn_fg_gameInProgress_btnAddPlayers);
        tvStatus = view.findViewById(R.id.tv_fg_gameInProgress_gameStatus);
        mainDataUpdate(mParam1);

        return view;
    }

    //Update the User Data
    protected void mainDataUpdate(String documentGameId) {
        FirebaseFirestore rootRef;

        rootRef = FirebaseFirestore.getInstance();

        DocumentReference docRef = rootRef.collection(getString(R.string.fs_TeamGame)).document(documentGameId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                TeamGame teamGame = documentSnapshot.toObject(TeamGame.class);
                if (teamGame != null) {
                    updateFromTheGameDefine(teamGame);
                }
            }
        });


    }


    protected void updateFromTheGameDefine(TeamGame teamGame)
    {

        tvGameName.setText(teamGame.getGameName());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();


        long startDate = teamGame.getStartDate();
        long endDate = teamGame.getEndDate();
        ValidationData validationData = new ValidationData();
        Boolean boolStatus = validationData.validateDateInBetween(startDate, endDate);
        if (boolStatus) {
            tvStatus.setText(getString(R.string.Status_Active));
        } else {
            tvStatus.setText(getString(R.string.Status_InActive));
        }

        if (teamGame.getGameDocumentId() != null) {
            if (teamGame.getGameDocumentId().contains(fbUser.getUid())) {

                if (boolStatus) {
                    // btnAddPlayers.setVisibility(View.VISIBLE);
                }

            }
        }

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTimeInMillis(teamGame.getStartDate());
        tvGameStartDate.setText("Game Start Date:" + formatter.format(calendarStart.getTime()));

        Calendar calendarEnd = Calendar.getInstance();
        calendarStart.setTimeInMillis(teamGame.getEndDate());
        tvGameEndDate.setText("Game Start Date:" + formatter.format(calendarStart.getTime()));
    }


}