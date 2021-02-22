package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSelfDream#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSelfDream extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    public FragmentSelfDream() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSelfDream.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSelfDream newInstance(String param1, String param2) {
        FragmentSelfDream fragment = new FragmentSelfDream();
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

        View view = inflater.inflate(R.layout.fragment_self_dream, container, false);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        onlineUserID = mAuth.getCurrentUser().getUid();
        FirebaseUser user = mAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.listSelfDream);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(false);
        fetch();
        return view;
    }




    private void fetch() {
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
            adapter = dbh.GetFireStoreAdapterDream(adapter, getString(R.string.fs_Personal_statements), query1);

            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }

        public static class ViewHolderSelfDream extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        // public CommonData mItem;

        public ViewHolderSelfDream(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_name_philosophy);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }



}