package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserShowCaseGame;
import app.actionnation.actionapp.adapters.GameTrackingAdapter;
import app.actionnation.actionapp.adapters.ShowGameTrackingAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGameTracker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGameTracker extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnGameStakes;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    TextView txtNoOfPlayers, txtTotalScore;
    FirebaseAuth mAuth;
    FirebaseFirestore rootRef;
    LinearLayout linearLayout;
    int noOfWinners;
    int noOfLosers;

    private ArrayList<UserGame> dataModalArrayList;

    private ArrayList<UserShowCaseGame> dataGameArrayList;


    private GameTrackingAdapter dataRVAdapter;
    private FirebaseFirestore db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentGameTracker() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGameTracker.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGameTracker newInstance(String param1, String param2) {
        FragmentGameTracker fragment = new FragmentGameTracker();
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
        View view = inflater.inflate(R.layout.fragment_game_tracker, container, false);
        btnGameStakes = view.findViewById(R.id.btn_gameStake);
        linearLayout = view.findViewById(R.id.ll_gametracker);
        recyclerView = view.findViewById(R.id.listGameTracker);

        btnGameStakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        rootRef = FirebaseFirestore.getInstance();

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        }

        String fullDocumentId = getActivity().getIntent().getStringExtra((getString(R.string.Intent_gameDocumentObject)));

        if (fullDocumentId != null) {
            String[] strGameObject = getActivity().getIntent().getStringExtra((getString(R.string.Intent_gameDocumentObject)))
                    .split(getString(R.string.fm_fieldPartition));
            int noOfPlayers = getActivity().getIntent().getIntExtra(getString(R.string.Intent_NoOfPlayes), 0);

            db = FirebaseFirestore.getInstance();

            // creating our new array list
            dataModalArrayList = new ArrayList<>();
            dataGameArrayList = new ArrayList<>();
            recyclerView.setHasFixedSize(true);


            // adding horizontal layout manager for our recycler view.
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // adding our array list to our recycler view adapter class.
            dataRVAdapter = new GameTrackingAdapter(dataGameArrayList, getContext(), noOfWinners, noOfLosers, 7);

            // setting adapter to our recycler view.
            recyclerView.setAdapter(dataRVAdapter);


            fetch(fbUser.getUid(), strGameObject, noOfPlayers);
        } else {
            CommonClass cls = new CommonClass();
            cls.makeSnackBar(linearLayout, "You haven't a game!");
        }

        return view;
    }

    private void showEditDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentStakeGame editNameFragment = FragmentStakeGame.newInstance(getString(R.string.Page_Redirect_Attention));
        editNameFragment.show(fm, "fragment_edit_name");
    }


    private void fetch(String usrId, String[] gameObject, int noOfPlayers) {

        Calendar c = Calendar.getInstance();
        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);
        mAuth = FirebaseAuth.getInstance();


        if (gameObject.length > 2) {
            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTimeInMillis(Long.parseLong(gameObject[1]));

            noOfWinners = 0;
            noOfLosers = 0;
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTimeInMillis(Long.parseLong(gameObject[2]));

            final FirebaseUser fbUser = mAuth.getCurrentUser();

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

            com.google.firebase.firestore.Query query = rootRef.collection(getString(R.string.fs_UserGame))
                    .whereEqualTo(getString(R.string.fs_Usergame_gameDocumentId), gameObject[0]).whereEqualTo(getString(R.string.fs_Usergame_dayOfTheYear), dayOfYear).whereEqualTo(getString(R.string.fs_Usergame_yeare), yr).whereGreaterThanOrEqualTo(getString(R.string.fs_Usergame_timestamp), calendarStart.getTime())
                    .whereLessThanOrEqualTo(getString(R.string.fs_Usergame_timestamp), calendarEnd.getTime()).orderBy(getString(R.string.fs_Usergame_timestamp)).orderBy(getString(R.string.fs_Usergame_userTotatScore), Query.Direction.DESCENDING);

            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    // after getting the data we are calling on success method
                    // and inside this method we are checking if the received
                    // query snapshot is empty or not.
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // if the snapshot is not empty we are hiding our
                        // progress bar and adding our data in a list.
                        int coinsTotal = 0;

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            // after getting this list we are passing that
                            // list to our object class.
                            UserGame dataModal = d.toObject(UserGame.class);
                            UserShowCaseGame userShowCaseGame = new UserShowCaseGame();

                            CommonClass cls = new CommonClass();
                            int TotalScore = cls.userGameScore(dataModal);
                            float percentScore = ((float) dataModal.getUserTotatScore() / Constants.Game_userTotalScore) * 100;
                            int finalPercent = (int) percentScore;
                            userShowCaseGame.setUserScore(finalPercent);
                            userShowCaseGame.setUserName(dataModal.getUserName());

                            userShowCaseGame.setCountCoinWinners(noOfWinners);
                            userShowCaseGame.setCountCoinLosers(noOfLosers);

                            if (percentScore >= dataModal.getUserExellenceBar()) {
                                userShowCaseGame.setUserCoins(dataModal.getUserCoinsPerDay());
                                noOfWinners = noOfWinners + 1;
                                userShowCaseGame.setCountCoinWinners(noOfWinners);
                            } else {
                                userShowCaseGame.setUserCoins(Constants.Status_Zero);
                                noOfLosers = noOfLosers + 1;
                                userShowCaseGame.setCountCoinLosers(noOfLosers);
                            }


                            // and we will pass this object class
                            // inside our arraylist which we have
                            // created for recycler view.

                            dataGameArrayList.add(userShowCaseGame);
                            dataModalArrayList.add(dataModal);
                        }
                        // after adding the data to recycler view.
                        // we are calling recycler view notifyDataSetChanged
                        // method to notify that data has been changed in recycler view.


                        dataRVAdapter.notifyDataSetChanged();
                    } else {
                        // if the snapshot is empty we are
                        // displaying a toast message.
                        Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // if we do not get any data or any error we are displaying
                    // a toast message that we do not get any data
                    Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                }
            });

            /*adapter = GetFireStoreAdapterShowGame(query, usrId);

            recyclerView.setAdapter(adapter);*/
        }
    }


    private FirestoreRecyclerAdapter GetFireStoreAdapterShowGame(com.google.firebase.firestore.Query query, final String fbId) {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        final FirestoreRecyclerOptions<UserGame> options = new FirestoreRecyclerOptions.Builder<UserGame>()
                .setQuery(query, UserGame.class)
                .build();


        adapter = new ShowGameTrackingAdapter(options);

        return adapter;

    }


   /* private void fetch2(String gameDocumentId) {
        db = FirebaseFirestore.getInstance();

        // creating our new array list
        dataModalArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);


        // adding horizontal layout manager for our recycler view.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // adding our array list to our recycler view adapter class.
        dataRVAdapter = new GameTrackingAdapter(dataModalArrayList, getContext());

        // setting adapter to our recycler view.
        recyclerView.setAdapter(dataRVAdapter);
        //loadrecyclerViewData(gameDocumentId);

        int i = 0;
    }*/




}
