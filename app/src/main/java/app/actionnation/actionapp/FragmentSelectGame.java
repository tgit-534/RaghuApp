package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.data.DbHelperClass2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSelectGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSelectGame extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView, recyclerViewSelectGame;
    Button btnTest;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    TextView tvHeading;

    public FragmentSelectGame() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSelectGame.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSelectGame newInstance(String param1, String param2) {
        FragmentSelectGame fragment = new FragmentSelectGame();
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
        View view = inflater.inflate(R.layout.fragment_select_game, container, false);

        recyclerView = view.findViewById(R.id.listSelectGame);
        tvHeading = view.findViewById(R.id.tv_fm_HeadingSelectGame);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        showGameSelection();
        return view;
    }

    protected void showGameSelection() {

        final FirebaseFirestore db;
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
        final FirestoreRecyclerOptions<TeamGame> options = new FirestoreRecyclerOptions.Builder<TeamGame>()
                .setQuery(query1, TeamGame.class)
                .build();
        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<TeamGame, ActivityGameCreation.ViewHolderSelectGame>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ActivityGameCreation.ViewHolderSelectGame holder, int position, @NonNull TeamGame model) {
                //      holder.mIdSelectGame.setText(model.getGameName());
                holder.mIdGameName.setText("Game Name: " + model.getGameName());
                Calendar cal = new GregorianCalendar();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(model.getStartDate());
                holder.mIdGameStart.setText("Game Start Date:" + formatter.format(calendarStart.getTime()));

                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(model.getEndDate());
                holder.mIdGameEnd.setText("Game End Date:" + formatter.format(calendarEnd.getTime()));


                holder.mIdSelectGame.setTag(getSnapshots().getSnapshot(position).getId());

                holder.mIdSelectGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {

                            DbHelperClass2 dbc = new DbHelperClass2();
                            dbc.updateFireUserData(getString(R.string.fs_TeamGame), holder.mIdSelectGame.getTag().toString(), getString(R.string.fs_TeamGame_gamePlayers), (ArrayList<String>)tvHeading.getTag(), db);
                        } else {

                        }

                    }
                });


            }

            @NonNull
            @Override
            public ActivityGameCreation.ViewHolderSelectGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_gameselection, parent, false);
                return new ActivityGameCreation.ViewHolderSelectGame(view);
            }
        };

        recyclerView.setAdapter(firestoreRecyclerAdapter);


    }

    public void onNameChange(ArrayList<String> playersList) {
        tvHeading.setTag(playersList);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (firestoreRecyclerAdapter != null)
            firestoreRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firestoreRecyclerAdapter.stopListening();

    }

}