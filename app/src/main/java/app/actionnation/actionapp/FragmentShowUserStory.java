package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserStories;
import app.actionnation.actionapp.Storage.UserStorageStoryObject;
import app.actionnation.actionapp.adapters.showUserStoriesAdapter;

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
    private static final String ARG_PARAM3 = "param3";


    RecyclerView recyclerView;
    showUserStoriesAdapter adapter;
    FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mDocumentId, mFbId, mUserImageUrl, mUserName, mCommentCount, mCommentId;
    private int mStoryStaus;

    public FragmentShowUserStory() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentShowUserStory newInstance(String param1, int storyStatus, String documentId) {
        FragmentShowUserStory fragment = new FragmentShowUserStory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, storyStatus);
        args.putString(ARG_PARAM3, documentId);

        fragment.setArguments(args);
        return fragment;
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
            mStoryStaus = getArguments().getInt(ARG_PARAM2);
            mDocumentId = getArguments().getString(ARG_PARAM3);

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
        com.google.firebase.firestore.Query query = null;

        if (mStoryStaus == 0) {
            query = rootRef.collection(getString(R.string.fs_UserStories))
                    .whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId).whereEqualTo("userStoryId", null)
                    .orderBy("timestamp", Query.Direction.DESCENDING);
        } else if (mStoryStaus == 1) {

            query = rootRef.collection(getString(R.string.fs_UserStories))
                    .whereEqualTo("userStoryId", mDocumentId)
                    .orderBy("timestamp", Query.Direction.DESCENDING);

        }

        FirestoreRecyclerOptions<UserStories> options = new FirestoreRecyclerOptions.Builder<UserStories>()
                .setQuery(query, UserStories.class)
                .build();


        // com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_UserStories)).orderBy("Date").limit(1);

        UserStorageStoryObject userStorageStoryObject = new UserStorageStoryObject();
        userStorageStoryObject.setFb_Id(usrId);
        userStorageStoryObject.setUserName(mAuth.getCurrentUser().getDisplayName());
        userStorageStoryObject.setUserProfilePicUrl(mParam1);

        adapter = new showUserStoriesAdapter(options, userStorageStoryObject);




        /*
        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterUserStories(getString(R.string.fs_UserStories), query1, getContext(), userStorageStoryObject);*/

        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new showUserStoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                UserStories userStories = documentSnapshot.toObject(UserStories.class);
                String id = documentSnapshot.getId();

                ArrayList<String> arrayUserOneStory = new ArrayList<>();
                arrayUserOneStory.add(0, id);
                arrayUserOneStory.add(1, String.valueOf(userStories.getFb_Id()));
                arrayUserOneStory.add(2, String.valueOf(userStories.getUserProfilePicUrl()));
                arrayUserOneStory.add(3, String.valueOf(userStories.getUserName()));
                arrayUserOneStory.add(4, String.valueOf(userStories.getUserStory()));
                arrayUserOneStory.add(5, String.valueOf(userStories.getUserLikeCount()));
                arrayUserOneStory.add(6, String.valueOf(userStories.getUserCommentCount()));
                arrayUserOneStory.add(7, String.valueOf(userStories.getUserReshareCount()));


                Intent showUserOneStory = new Intent(getContext(), ActivityUserStory.class);
                Bundle mBundle4 = new Bundle();
                mBundle4.putString(getString(R.string.common_auth), getString(R.string.common_google));
                mBundle4.putStringArrayList(getString(R.string.Intent_ArrayOneStory), arrayUserOneStory);
                // mBundle4.putStringArrayList(getString(R.string.Intent_ArrayRating), (ArrayList<String>) fab.getTag());
                // mBundle4.putFloatArray(getString(R.string.Intent_ArrayRating), userRatingFloatArray);
                showUserOneStory.putExtras(mBundle4);
                startActivity(showUserOneStory);


            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }


}