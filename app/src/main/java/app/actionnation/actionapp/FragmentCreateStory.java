package app.actionnation.actionapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserStories;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCreateStory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateStory extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "userUrl";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_DOCUMENTID = "documentId";
    private static final String ARG_FBID = "fbId";
    private static final String ARG_USERIMAGE = "userImage";
    private static final String ARG_USERNAME = "userName";
    private static final String ARG_SHARECOUNT = "shareCount";

//Name

    //Distance
    //Bodhpur


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String mDocumentId, mFbId, mUserImageUrl, mUserName, mCommentCount, mCommentId;
    private Button btnStorySubmit, btnCommentSubmit;
    private EditText etUserStory;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    public FragmentCreateStory() {
        // Required empty public constructor
    }

    public static FragmentCreateStory newInstance(String fragmentUserImageUrl) {
        FragmentCreateStory frag = new FragmentCreateStory();
        Bundle args = new Bundle();
        args.putString(ARG_USERIMAGE, fragmentUserImageUrl);
        frag.setArguments(args);
        return frag;
    }

    public static FragmentCreateStory newInstance(ArrayList<String> storyCommentObject) {
        FragmentCreateStory frag = new FragmentCreateStory();
        Bundle args = new Bundle();
        args.putString(ARG_DOCUMENTID, storyCommentObject.get(0));
        args.putString(ARG_FBID, storyCommentObject.get(1));
        args.putString(ARG_USERIMAGE, storyCommentObject.get(2));
        args.putString(ARG_USERNAME, storyCommentObject.get(3));
        args.putString(ARG_SHARECOUNT, storyCommentObject.get(4));

        frag.setArguments(args);
        return frag;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCreateStory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCreateStory newInstance(String param1, String param2) {
        FragmentCreateStory fragment = new FragmentCreateStory();
        Bundle args = new Bundle();
        args.putString(ARG_USERIMAGE, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserImageUrl = getArguments().getString(ARG_USERIMAGE);
            mDocumentId = getArguments().getString(ARG_DOCUMENTID);
            mCommentCount = getArguments().getString(ARG_SHARECOUNT);
            mUserName = getArguments().getString(ARG_USERNAME);
            mFbId = getArguments().getString(ARG_FBID);


        }
    }

    String userUrl;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // mEditText = (EditText) view.findViewById(R.id.et_fm_captainComments);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        userUrl = getArguments().getString(mParam1);


        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        //  mEditText.requestFocus();


        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_story, container, false);
        btnStorySubmit = view.findViewById(R.id.btn_fm_submitStory);
        etUserStory = view.findViewById(R.id.et_fm_story);
        btnCommentSubmit = view.findViewById(R.id.btn_fm_submitComment);


        if (mDocumentId != null) {
            if (!mDocumentId.isEmpty()) {
                btnStorySubmit.setVisibility(View.INVISIBLE);
                btnCommentSubmit.setVisibility(View.VISIBLE);
            } else {
                btnStorySubmit.setVisibility(View.VISIBLE);
                btnCommentSubmit.setVisibility(View.INVISIBLE);
            }
        }

        btnStorySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelperClass Db = new DbHelperClass();
                mAuth = FirebaseAuth.getInstance();
                firebaseFirestore = FirebaseFirestore.getInstance();

                CommonClass cls = new CommonClass();
                String usrId = cls.fetchUserId(mAuth);

                if (TextUtils.isEmpty(etUserStory.getText().toString())) {
                    cls.callToast(getContext(), "You have not given rating for the Captain!");
                } else {
                    UserStories US = new UserStories();
                    US.setFb_Id(usrId);
                    US.setTimestamp(null);
                    US.setUserName(mAuth.getCurrentUser().getDisplayName());
                    US.setUserStory(etUserStory.getText().toString());
                    US.setUserProfilePicUrl(mUserImageUrl);

                    Db.insertFireUserStories(getString(R.string.fs_UserStories), getContext(), US, firebaseFirestore);

                    etUserStory.setText("");

                }
            }
        });

        btnCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelperClass Db = new DbHelperClass();
                mAuth = FirebaseAuth.getInstance();
                firebaseFirestore = FirebaseFirestore.getInstance();


                CommonClass cls = new CommonClass();
                String usrId = cls.fetchUserId(mAuth);

                if (TextUtils.isEmpty(etUserStory.getText().toString())) {
                    cls.callToast(getContext(), "You have not given rating for the Captain!");
                } else {

                    UserStories usc = new UserStories();
                    usc.setFb_Id(mFbId);
                    usc.setUserProfilePicUrl(mUserImageUrl);
                    usc.setUserName(mUserName);
                    usc.setUserStory(etUserStory.getText().toString());
                    usc.setTimestamp(null);
                    usc.setUserStoryId(mDocumentId);

                    Db.insertFireUserComments(getString(R.string.fs_UserStories), getContext(), usc, firebaseFirestore, Integer.parseInt(mCommentCount));
                    etUserStory.setText("");

                }


            }
        });


        return view;


    }
}