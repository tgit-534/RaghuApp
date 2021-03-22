package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserCaptainRatings;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRateCaptain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRateCaptain extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText mEditText;
    private Button btnRate;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private RatingBar ratingBar;


    public FragmentRateCaptain() {
        // Required empty public constructor
    }

    public static FragmentRateCaptain newInstance(String title) {
        FragmentRateCaptain frag = new FragmentRateCaptain();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRateCaptain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRateCaptain newInstance(String param1, String param2) {
        FragmentRateCaptain fragment = new FragmentRateCaptain();
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
        View view = inflater.inflate(R.layout.fragment_rate_captain, container, false);


        btnRate = view.findViewById(R.id.btn_fm_rateCaptain);
        ratingBar = view.findViewById(R.id.rb_fm_captainRater);
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelperClass Db = new DbHelperClass();
                mAuth = FirebaseAuth.getInstance();
                firebaseFirestore = FirebaseFirestore.getInstance();

                CommonClass cls = new CommonClass();
                String usrId = cls.fetchUserId(mAuth);

                if (ratingBar.getRating() == 0) {
                    cls.callToast(getContext(), "You have not given rating for the Captain!");
                } else {
                    UserCaptainRatings ph = new UserCaptainRatings();
                    ph.setFb_Id(usrId);
                    ph.setCaptainRating(ratingBar.getRating());
                    ArrayList<String> activityArrayCaptains = getActivity().getIntent().getStringArrayListExtra((getString(R.string.Intent_ArrayCaptain)));
                    if (activityArrayCaptains != null && activityArrayCaptains.size() > 0)
                        ph.setUserCaptainFb_Id(activityArrayCaptains.get(1));
                    ph.setUserComments(mEditText.getText().toString());
                    ph.setTimestamp(null);
                    Db.insertFireCaptainRatings(getString(R.string.fs_UserCaptainRatings), getContext(), ph, firebaseFirestore);

                    float[] userRatingFloatArray = (float[])getActivity().getIntent().getFloatArrayExtra((getString(R.string.Intent_ArrayRating)));
                    float userRating = userRatingFloatArray[Constants.UserProfile_Array_Rating];
                    int noOfRatings = (int)userRatingFloatArray[Constants.UserProfile_Array_NoOfRatings];
                    userRating = userRating + ratingBar.getRating();
                    noOfRatings = noOfRatings + 1;
                    Db.updateFireUserProfileRating(getString(R.string.fs_UserProfile), getContext(), usrId, userRating, noOfRatings, firebaseFirestore);
                    clearForm();

                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.et_fm_captainComments);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


    }


    private void clearForm() {
        ratingBar.setRating(0);
        mEditText.setText("");
    }
}