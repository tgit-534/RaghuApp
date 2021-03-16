package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.Storage.GraphDataObject;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGameGraph#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGameGraph extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    TextView tvTeamName;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final String TAG = getClass().getSimpleName();
    private FirebaseFirestore db;
    int graphStatus;
    BarChart barChartGame;
    ArrayList<String> activityArrayCaptains;
    ArrayList<GraphDataObject> graphDataObjects;

    public FragmentGameGraph() {
        // Required empty public constructor
    }

    public FragmentGameGraph(int graphPosition, ArrayList<String> arrayCaptains) {
        graphStatus = graphPosition;
        activityArrayCaptains = arrayCaptains;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGameGraph.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGameGraph newInstance(String param1, String param2) {
        FragmentGameGraph fragment = new FragmentGameGraph();
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
        View view = inflater.inflate(R.layout.fragment_game_graph, container, false);

        tvTeamName = view.findViewById(R.id.fg_tv_TeamName);

        recyclerView = view.findViewById(R.id.listPlayerScores);
        activityArrayCaptains = getActivity().getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
        String userId = "";
        String[] strUser;
        if (graphStatus == Constants.Status_Zero) {
            strUser = fetchUserId();
            userId = strUser[0];
            tvTeamName.setText(strUser[1]);
        } else if (graphStatus == Constants.Status_One) {
            if (activityArrayCaptains != null && activityArrayCaptains.size() > 0)
                userId = activityArrayCaptains.get(1);
            if (activityArrayCaptains.size() > 2)
                tvTeamName.setText(activityArrayCaptains.get(2));

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        fetch(userId);
        return view;
    }



    protected String[] fetchUserId() {
        String[] strUser = new String[2];
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
            strUser[0] = usrId;
            strUser[1] = fbUser.getDisplayName();

        }
        return strUser;
    }

    private void fetch(String usrId) {

        Calendar c = Calendar.getInstance();
        int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
        int yr = c.get(Calendar.YEAR);
        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser fbUser = mAuth.getCurrentUser();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        com.google.firebase.firestore.Query query = rootRef.collection(getString(R.string.fs_UserGame))
                .whereArrayContains(getString(R.string.fs_Usergame_teamCaptains), usrId).whereEqualTo(getString(R.string.fs_Usergame_dayOfTheYear), dayOfYear).whereEqualTo(getString(R.string.fs_Usergame_yeare), yr).orderBy(getString(R.string.fs_Usergame_userTotatScore), Query.Direction.DESCENDING);

        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterTeamGame(adapter, getString(R.string.fs_UserGame), query, getContext(), usrId);

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}