package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.UserStorageGameObject;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentExercise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentExercise extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static FragmentManager fragmentManager;

    Button btnFinish;
    int countData = 0;
    FirebaseAuth mAuth;
    String usrId = "";
    int dayOfTheYear, yr;
    LinearLayout linearLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentExercise() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentExercise.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentExercise newInstance(String param1, String param2) {
        FragmentExercise fragment = new FragmentExercise();
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
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        mAuth = FirebaseAuth.getInstance();

        btnFinish = view.findViewById(R.id.btn_fm_exe_finish);
        linearLayout = view.findViewById(R.id.ll_exercise);


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonClass cls = new CommonClass();
                DbHelper db = new DbHelper(getContext());
                ArrayList<String> strUser = cls.fetchUserArray(mAuth);
                dayOfTheYear = cls.fetchDate(0);
                yr = cls.fetchDate(1);
                cls.SubmitGenericGame(Constants.GS_exerciseScore, db, usrId, dayOfTheYear, yr);

                DbHelperClass dbHelperClass = new DbHelperClass();
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();


                UserStorageGameObject userStorageGameObject = new UserStorageGameObject();
                userStorageGameObject.setGameDocumentId(getActivity().getIntent().getStringExtra(Constants.Intent_GameDocumentId));
                userStorageGameObject.setUserCoinsPerDay(getActivity().getIntent().getIntExtra(Constants.Intent_GameCoinsPerDay, Constants.Status_Zero));
                userStorageGameObject.setUserExellenceBar(getActivity().getIntent().getIntExtra(Constants.Intent_ExcellenceBar, Constants.Status_Zero));

                UserGame userGame = cls.loadUserGame(strUser.get(0), dayOfTheYear, yr, strUser.get(1), userStorageGameObject);

                userGame.setUserExerciseScore(Constants.Game_Exercise);


                int totalGameScore = 0;
                ArrayList<Integer> arrayGameScore = getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore)));

                ArrayList<Integer> arrayNewGameScore = cls.createGameScore(Constants.Game_CP__UserExerciseScore, Constants.Game_Exercise, arrayGameScore, userGame, getContext());

                if (arrayNewGameScore.size() == 20) {
                    userGame.setUserTotatScore(arrayNewGameScore.get(Constants.Game_CP__UserTotatScore));
                    arrayGameScore = arrayNewGameScore;
                    totalGameScore = arrayGameScore.get(Constants.Game_CP__UserTotatScore);
                } else {
                    userGame.setUserTotatScore(Constants.Game_Exercise);
                    totalGameScore = arrayNewGameScore.get(Constants.Status_Zero);

                }


                dbHelperClass.insertFireUserGame(getString(R.string.fs_UserGame), getContext(), userGame, rootRef, getString(R.string.fs_Usergame_userExerciseScore), Constants.Game_Exercise, totalGameScore);
                cls.makeSnackBar(linearLayout);


            }
        });
        fragmentManager = getActivity().getSupportFragmentManager();
        Fragment argumentFragment = new timingset();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString(getString(R.string.Activity_Number), String.valueOf(Constants.GS_exerciseScore));

        argumentFragment.setArguments(data);//Finally set argument bundle to fragment
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerExercise, argumentFragment).commit();


        // Inflate the layout for this fragment
        return view;
    }
}