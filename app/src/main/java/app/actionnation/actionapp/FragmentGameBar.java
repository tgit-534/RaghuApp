package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import app.actionnation.actionapp.Database_Content.UserGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGameBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGameBar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    NumberProgressBar numberProgressBar;
    FirebaseFirestore rootRef;
    FirebaseAuth mAuth;
    TextView textViewGame;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentGameBar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGameBar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGameBar newInstance(String param1, String param2) {
        FragmentGameBar fragment = new FragmentGameBar();
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
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_bar, container, false);
        textViewGame = view.findViewById(R.id.tvGame);

        updateGameBar();

        numberProgressBar = view.findViewById(R.id.numberProgressGame);






        /*barChartGame = view.findViewById(R.id.barchart_Game);

        BarEntry barEntry = new BarEntry(500, 100);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(barEntry);


        ArrayList<String> labels = new ArrayList<>();
        labels.add("bond");
        BarDataSet barDataSet = new BarDataSet(barEntries, "Game Score");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Bidda");
        barChartGame.setDescription(description);

        BarData barData = new BarData(barDataSet);
        barChartGame.setData(barData);

        XAxis xAxis = barChartGame.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        xAxis.setPosition(XAxis.XAxisPosition.TOP);

        xAxis.setDrawGridLines(false);

        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setLabelRotationAngle(270);
        barChartGame.animateY(2000);
        barChartGame.invalidate();*/


        return view;


    }

    int GameScore;

    private void updateGameBar() {
        rootRef = FirebaseFirestore.getInstance();


        String usrId = fetchUserId();
        Calendar c = Calendar.getInstance();

        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);
        DbHelper db = new DbHelper(getActivity());
        CommonClass cls = new CommonClass();
        DocumentReference docRef = rootRef.collection(getString(R.string.fs_UserGame)).document(fetchUserId() + String.valueOf(dayOfYear) + String.valueOf(yr));

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserGame userGame = documentSnapshot.toObject(UserGame.class);

                if (userGame != null) {
                    int totalscore = Integer.valueOf(userGame.getUserTotatScore());
                    textViewGame.setTag(totalscore);
                    numberProgressBar.setMax(Constants.Game_userTotalScore);
                    numberProgressBar.setProgress(totalscore);
                    numberProgressBar.setOnProgressBarListener(new OnProgressBarListener() {
                        @Override
                        public void onProgressChange(int current, int max) {
                            if (current == max) {
                                numberProgressBar.setProgress(0);
                            }
                        }
                    });
                }
            }
        });
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