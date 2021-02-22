 package app.actionnation.actionapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import app.actionnation.actionapp.Database_Content.CommonData;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActionPhilosophy.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionPhilosophy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionPhilosophy extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    FirestoreRecyclerAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    //Firebase from Data


    RecyclerView recyclerView;
    private String onlineUserID, strTab;
    private static final String TAG = "ActionPhilosophy:Log";

    FirebaseRecyclerAdapter fbAdapter;

    private OnFragmentInteractionListener mListener;

    public ActionPhilosophy() {
        // Required empty public constructor
    }

    public ActionPhilosophy(String str) {

        strTab = str;
    }


    // TODO: Rename and change types and number of parameters
    public static ActionPhilosophy newInstance(String param1, String param2) {
        ActionPhilosophy fragment = new ActionPhilosophy();
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
        View view = inflater.inflate(R.layout.fragment_action_philosophy, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        onlineUserID = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.listPhilosophy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(false);
        fetch();
        return view;
    }


    private void fetch() {
        if (strTab.equals(getString(R.string.aaq_MVP))) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            final FirebaseUser fbUser = mAuth.getCurrentUser();
            String usrId = "";
            if (mAuth.getCurrentUser() != null) {
                usrId = fbUser.getUid();
            } else {
                return;
            }
            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

            com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_Personal_statements)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);

            DbHelperClass dbh = new DbHelperClass();
            adapter = dbh.GetFireStoreAdapter(adapter, getString(R.string.fs_Personal_statements), query1);

            recyclerView.setAdapter(adapter);
            adapter.startListening();
        } else if (strTab.equals(getString(R.string.aaq_values)) || strTab.equals(getString(R.string.aaq_Affirmations)) || strTab.equals(getString(R.string.aaq_description))) {
            Query query = FirebaseDatabase.getInstance()
                    .getReference()
                    .child(getString(R.string.fb_CommonData_Db));
            if (strTab.equals(getString(R.string.aaq_values)))
                query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild(getString(R.string.fb_status)).equalTo(Integer.valueOf(getString(R.string.aaq_values_Number)));
            if (strTab.equals(getString(R.string.aaq_Affirmations)))
                query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild(getString(R.string.fb_status)).equalTo(Integer.valueOf(getString(R.string.aaq_Affirmations_Number)));
            if (strTab.equals(getString(R.string.aaq_description)))
                query = mFirebaseDatabase.getInstance().getReference().child(getString(R.string.fb_CommonData_Db)).orderByChild(getString(R.string.fb_status)).equalTo(Integer.valueOf(getString(R.string.aaq_description_Number)));

            FirebaseRecyclerOptions<CommonData> options = new FirebaseRecyclerOptions.Builder<CommonData>()
                    .setQuery(query, CommonData.class)
                    .build();
            String S = strTab;

            fbAdapter = new FirebaseRecyclerAdapter<CommonData, displaydata.ViewHolderActionPhilosopy>(options) {
                @Override
                protected void onBindViewHolder(@NonNull displaydata.ViewHolderActionPhilosopy holder, int position, @NonNull CommonData model) {
                    holder.mIdView.setText(model.getDataContent());
                }

                @NonNull
                @Override
                public displaydata.ViewHolderActionPhilosopy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.actionphilosophy_list, parent, false);
                    displaydata.ViewHolderActionPhilosopy viewHolder = new displaydata.ViewHolderActionPhilosopy(view);
                    return viewHolder;
                }
            };
            recyclerView.setAdapter(fbAdapter);
            fbAdapter.startListening();
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
        if (fbAdapter != null)
            fbAdapter.stopListening();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
