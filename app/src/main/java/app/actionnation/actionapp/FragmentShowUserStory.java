package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentShowUserStory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentShowUserStory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentShowUserStory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentShowUserStory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentShowUserStory newInstance(String param1, String param2) {
        FragmentShowUserStory fragment = new FragmentShowUserStory();
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
        View view = inflater.inflate(R.layout.fragment_show_user_story, container, false);
        recyclerView = view.findViewById(R.id.listYourStory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        fetch();
        return view;
    }


    private void fetch() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_UserStories))
                .whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        // com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_UserStories)).orderBy("Date").limit(1);


        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterUserStories(getString(R.string.fs_UserStories), query1, getContext(), usrId);

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    public static class ViewHolderUserStory extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdStory;
        public final TextView mNoOfLikes;
        public final TextView mNoOfComments;
        public final TextView mNoOfShares;

        public final ImageButton mBtnLikes;
        public final ImageButton mBtnShares;
        public final ImageButton mBtnComments;

        public final de.hdodenhof.circleimageview.CircleImageView mUserImage;
        public final TextView mUserName;

       /*
        public final ImageButton mLikes;
        public final ImageButton mReshare;
        public final ImageButton mComment;*/

        public ViewHolderUserStory mItem;

        public ViewHolderUserStory(View view) {
            super(view);
            mView = view;
            mIdStory = view.findViewById(R.id.tv_lo_userStory);
            mNoOfLikes = view.findViewById(R.id.tv_lo_countUserLikes);
            mNoOfComments = view.findViewById(R.id.tv_lo_countUserComments);
            mNoOfShares = view.findViewById(R.id.tv_lo_countUserShares);
            mUserImage = view.findViewById(R.id.profile_lo_image);
            mUserName = view.findViewById(R.id.tv_lo_userName);
            mBtnLikes = view.findViewById(R.id.imgBtn_lo_countUserLikes);
            mBtnShares = view.findViewById(R.id.imgBtn_lo_countUserShares);
            mBtnComments = view.findViewById(R.id.imgBtn_lo_countUserComments);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdStory.getText() + "'";
        }
    }

}