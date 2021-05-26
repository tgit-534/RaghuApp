package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
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
import java.util.List;

import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.Storage.Constants;
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
    TextView tvHeading, tvStatusAddPeople;
    LinearLayout linearLayout;

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
        linearLayout = view.findViewById(R.id.ll_showSelectGame);
        tvStatusAddPeople = view.findViewById(R.id.tv_fm_membersStatus);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        showGameSelection();
        return view;
    }

    protected void showGameSelection() {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();

        Calendar cNew = Calendar.getInstance();

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }

        String className = getContext().getClass().getName();

        com.google.firebase.firestore.Query fbQuery = null;

        switch (className) {
            case Constants.ClassName_GameTracking:
                fbQuery = db.collection(getString(R.string.fs_TeamGame)).whereArrayContains(getString(R.string.fs_TeamGame_gamePlayers), mAuth.getCurrentUser().getEmail()).whereGreaterThan(getString(R.string.fs_TeamGame_StartDate), cNew.getTimeInMillis());
                break;

            case Constants.ClassName_GameCreation:
                fbQuery = db.collection(getString(R.string.fs_TeamGame)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId).whereGreaterThan(getString(R.string.fs_TeamGame_StartDate), cNew.getTimeInMillis());
                break;

        }
        final String fbId = usrId;
        //whereLessThan(getString(R.string.fs_TeamGame_StartDate), cNew.getTimeInMillis());

        DbHelperClass2 dbh = new DbHelperClass2();
        final FirestoreRecyclerOptions<TeamGame> options = new FirestoreRecyclerOptions.Builder<TeamGame>()
                .setQuery(fbQuery, TeamGame.class)
                .build();
        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<TeamGame, ActivityGameCreation.ViewHolderSelectGame>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ActivityGameCreation.ViewHolderSelectGame holder, int position, @NonNull TeamGame model) {
                //      holder.mIdSelectGame.setText(model.getGameName());
                holder.mIdGameName.setText("Game Name: " + model.getGameName());
                Calendar cal = new GregorianCalendar();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                final String className = getContext().getClass().getName();

                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(model.getStartDate());
                holder.mIdGameStart.setText("Game Start Date:" + formatter.format(calendarStart.getTime()));

                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(model.getEndDate());
                holder.mIdGameEnd.setText("Game End Date:" + formatter.format(calendarEnd.getTime()));


                switch (className) {
                    case Constants.ClassName_GameTracking:

                        String checkBoxAssignedData = getSnapshots().getSnapshot(position).getId() + getString(R.string.fm_fieldPartition)
                                + String.valueOf(model.getStartDate()) + getString(R.string.fm_fieldPartition)
                                + String.valueOf(model.getEndDate()) + getString(R.string.fm_fieldPartition) + String.valueOf(model.getGamePlayers().size())+ getString(R.string.fm_fieldPartition) + String.valueOf(model.getCoinsAtStake());
                        holder.mIdSelectGame.setTag(checkBoxAssignedData);

                        break;

                    case Constants.ClassName_GameCreation:
                        List<String> gameCreationObjectArray = new ArrayList<>();

                        if (model.getGamePlayers() != null) {
                            gameCreationObjectArray = model.getGamePlayers();

                            gameCreationObjectArray.add(getString(R.string.fm_arrayListPartition));
                            gameCreationObjectArray.add(getSnapshots().getSnapshot(position).getId());
                            gameCreationObjectArray.add(String.valueOf(model.getStartDate()));
                            gameCreationObjectArray.add(String.valueOf(model.getEndDate()));
                            holder.mIdSelectGame.setTag(gameCreationObjectArray);
                        } else {
                            gameCreationObjectArray.add(getSnapshots().getSnapshot(position).getId());
                            gameCreationObjectArray.add(String.valueOf(model.getStartDate()));
                            gameCreationObjectArray.add(String.valueOf(model.getEndDate()));
                            holder.mIdSelectGame.setTag(gameCreationObjectArray);
                        }

                        break;
                }


                holder.mIdSelectGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            DbHelperClass2 dbc = new DbHelperClass2();
                            ArrayList<String> dataVariables = new ArrayList<>();


                            ArrayList<String> players = (ArrayList<String>) tvHeading.getTag();
                            ArrayList<Object> dataObjects = new ArrayList<>();


                            switch (className) {
                                case Constants.ClassName_GameTracking:

                                    dataVariables.add(getString(R.string.fs_UserProfile_gameDocId));
                                    dataVariables.add(getString(R.string.fs_TeamGame_noOfPlayers));
                                    dataVariables.add(getString(R.string.fs_UserProfile_userCoinsPerDay));


                                    String[] strObj = holder.mIdSelectGame.getTag().toString().split(getString(R.string.fm_fieldPartition));

                                    dataObjects.add(holder.mIdSelectGame.getTag().toString());
                                    dataObjects.add(Integer.valueOf(strObj[3]));
                                    dataObjects.add(Integer.valueOf(strObj[4]));


                                    dbc.updateFireUserData(getString(R.string.fs_UserProfile), fbId, dataVariables, dataObjects, db);

                                    break;

                                case Constants.ClassName_GameCreation:

                                    ArrayList<String> gameDataObjects = (ArrayList<String>) holder.mIdSelectGame.getTag();

                                    dataVariables.add(getString(R.string.fs_TeamGame_gamePlayers));
                                    dataVariables.add(getString(R.string.fs_TeamGame_noOfPlayers));


                                    if (gameDataObjects.contains(getString(R.string.fm_arrayListPartition))) {
                                        if (gameDataObjects != null) {
                                            int partitionLine = gameDataObjects.indexOf(getString(R.string.fm_arrayListPartition));
                                            List<String> alreadyGamePlayers = gameDataObjects.subList(0, partitionLine);
                                            String gameDocId = gameDataObjects.get(partitionLine + 1);
                                            alreadyGamePlayers.addAll(players);
                                            ArrayList<String> dataInsert = new ArrayList<>();

                                            for (String data : alreadyGamePlayers) {
                                                dataInsert.add(data);

                                            }

                                            dataObjects.add(alreadyGamePlayers);
                                            dataObjects.add(alreadyGamePlayers.size());
                                            dbc.updateFireUserData(getString(R.string.fs_TeamGame), gameDocId, dataVariables, dataObjects, db);

                                        }

                                    } else {

                                        if (players != null) {
                                            ArrayList<String> playersNew = new ArrayList<>();
                                            playersNew.add(fbUser.getEmail());
                                            playersNew.addAll(players);

                                            dataObjects.add(playersNew);
                                            dataObjects.add(playersNew.size());
                                            dbc.updateFireUserData(getString(R.string.fs_TeamGame), gameDataObjects.get(0), dataVariables, dataObjects, db);
                                        } else {
                                            CommonClass cls = new CommonClass();
                                            cls.makeSnackBar(linearLayout, "There are no new users to add!");
                                        }
                                    }

                                    break;
                            }

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
        if (playersList != null || playersList.size() > 0) {
            tvStatusAddPeople.setText("People are ready to Add!");

        }


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