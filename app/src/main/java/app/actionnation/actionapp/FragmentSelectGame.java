package app.actionnation.actionapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
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
import app.actionnation.actionapp.data.OnFragmentRefreshMainListener;

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
    private int mParam2;

    private OnFragmentRefreshMainListener mListener;

    RecyclerView recyclerView;
    Button btnTest;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    TextView tvHeading, tvStatusAddPeople;
    LinearLayout linearLayout;

    public FragmentSelectGame() {
        // Required empty public constructor
    }


    public FragmentSelectGame(String param1, int memberStatus) {
        mParam1 = param1;
        mParam2 = memberStatus;

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
    public static FragmentSelectGame newInstance(String param1, int memberStatus) {
        FragmentSelectGame fragment = new FragmentSelectGame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, memberStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_game, container, false);

        recyclerView = view.findViewById(R.id.listSelectGame);
        linearLayout = view.findViewById(R.id.ll_showSelectGame);
        tvStatusAddPeople = view.findViewById(R.id.tv_fm_membersStatus);

        showGameSelection();
        return view;
    }

    protected void showGameSelection() {


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();

        String documentGameId = "";
        long currentGameStartDate = 0, currentGameEndDate = 0;

        if (mParam1 != null) {
            String[] gameObject = mParam1.split(getString(R.string.fm_fieldPartition));
            documentGameId = gameObject[0];
            if (gameObject.length > 1) {
                currentGameStartDate = Long.parseLong(gameObject[1]);
                currentGameEndDate = Long.parseLong(gameObject[2]);
            }
        }
        Calendar cNew = Calendar.getInstance();

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }

        com.google.firebase.firestore.Query fbQuery = null;


        if (mParam2 == Constants.Status_Zero) {
            fbQuery = db.collection(getString(R.string.fs_TeamGame)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId).whereGreaterThan(getString(R.string.fs_TeamGame_StartDate), cNew.getTimeInMillis());

        } else if (mParam2 == Constants.Status_One) {
            fbQuery = db.collection(getString(R.string.fs_TeamGame)).whereArrayContains(getString(R.string.fs_TeamGame_gamePlayers), mAuth.getCurrentUser().getEmail()).whereGreaterThan(getString(R.string.fs_TeamGame_StartDate), cNew.getTimeInMillis());

        } else if (mParam2 == Constants.Status_Two) {
            fbQuery = db.collection(getString(R.string.fs_TeamGame)).whereEqualTo(getString(R.string.fs_Usergame_gameDocumentId), documentGameId);
        }


        final String fbId = usrId;
        final String documentGameNewId = documentGameId;
        final long currentGameStartDateNew = currentGameStartDate, currentGameEndDateNew = currentGameEndDate;

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


                if (getSnapshots().getSnapshot(position).getId().equals(documentGameNewId)) {
                    holder.mIdSelectGame.setChecked(true);
                }

//9686142901 Dr. amulya
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(model.getStartDate());
                holder.mIdGameStart.setText("Game Start Date:" + formatter.format(calendarStart.getTime()));

                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(model.getEndDate());
                holder.mIdGameEnd.setText("Game End Date:" + formatter.format(calendarEnd.getTime()));

                if (documentGameNewId.startsWith(fbId)) {
                    List<String> gameCreationObjectArray = new ArrayList<>();

                    if (model.getGamePlayers() != null) {
                        gameCreationObjectArray = model.getGamePlayers();

                        gameCreationObjectArray.add(getString(R.string.fm_arrayListPartition));
                        gameCreationObjectArray.add(getSnapshots().getSnapshot(position).getId());
                        gameCreationObjectArray.add(String.valueOf(model.getStartDate()));
                        gameCreationObjectArray.add(String.valueOf(model.getEndDate()));
                        holder.mIdAddPlayers.setTag(gameCreationObjectArray);
                    } else {
                        gameCreationObjectArray.add(getSnapshots().getSnapshot(position).getId());
                        gameCreationObjectArray.add(String.valueOf(model.getStartDate()));
                        gameCreationObjectArray.add(String.valueOf(model.getEndDate()));
                        holder.mIdAddPlayers.setTag(gameCreationObjectArray);

                    }
                }
                String checkBoxAssignedData = getSnapshots().getSnapshot(position).getId() + getString(R.string.fm_fieldPartition)
                        + String.valueOf(model.getStartDate()) + getString(R.string.fm_fieldPartition)
                        + String.valueOf(model.getEndDate()) + getString(R.string.fm_fieldPartition) + model.getGameName();
                //+ getString(R.string.fm_fieldPartition) + String.valueOf(model.getGamePlayers().size()) + getString(R.string.fm_fieldPartition) + String.valueOf(model.getCoinsAtStake());
                holder.mIdSelectGame.setTag(checkBoxAssignedData);


                holder.mIdSelectGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {

                            final String gameObj = holder.mIdSelectGame.getTag().toString();

                            String[] arrayGameObj = gameObj.split(getString(R.string.fm_fieldPartition));


                            if ((currentGameStartDateNew != Constants.Status_Zero) ||
                                    (currentGameStartDateNew >= Long.parseLong(arrayGameObj[1]) && currentGameStartDateNew <= Long.parseLong(arrayGameObj[2])) ||
                                    (currentGameEndDateNew >= Long.parseLong(arrayGameObj[1]) && currentGameEndDateNew <= Long.parseLong(arrayGameObj[2]))) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle(getString(R.string.fm_AlertDialog_Replace));
                                builder.setMessage(getString(R.string.act_MainObjective_AlertDialog_Now));

                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        updateProfileForGame(documentGameNewId, fbId, gameObj, db);
                                    }
                                });

                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Snackbar snackbar1 = Snackbar.make(linearLayout, getString(R.string.fm_AlertDialog_NoReplace), Snackbar.LENGTH_SHORT);
                                        snackbar1.show();

                                        // Do nothing
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {
                                updateProfileForGame(documentGameNewId, fbId, gameObj, db);
                            }
                        }
                    }
                });


                holder.mIdAddPlayers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentSendGameInvitations editNameDialogFragment = FragmentSendGameInvitations.newInstance((ArrayList<String>) holder.mIdAddPlayers.getTag());
                        editNameDialogFragment.show(fm, "fragment_edit_name");
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


    private void updateProfileForGame(String documentGameNewId, String fbId, String gameObjectId, FirebaseFirestore db) {

        DbHelperClass2 dbc = new DbHelperClass2();
        ArrayList<String> dataVariables = new ArrayList<>();

        ArrayList<Object> dataObjects = new ArrayList<>();
        dataVariables.add(getString(R.string.fs_UserProfile_gameDocId));
        dataObjects.add(gameObjectId);
        dbc.updateFireUserData(getString(R.string.fs_UserProfile), fbId, dataVariables, dataObjects, db);
        mListener.refreshMain(gameObjectId);
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentRefreshMainListener) {
            mListener = (OnFragmentRefreshMainListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentCommunicationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}