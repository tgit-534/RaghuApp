package app.actionnation.actionapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import app.actionnation.actionapp.Storage.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUserProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tvUserName, tvDream, tvChallenge, tvHandleName;
    Button buttonEdit;

    private OnFragmentUpdateProfileListener mListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> listProfileObject = new ArrayList<>();

    public FragmentUserProfile() {
        // Required empty public constructor
    }

    public FragmentUserProfile(ArrayList<String> profileObject) {
        listProfileObject = profileObject;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUserProfile newInstance(String param1, String param2) {
        FragmentUserProfile fragment = new FragmentUserProfile();
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
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        tvUserName = view.findViewById(R.id.tv_fg_userName);
        tvDream = view.findViewById(R.id.tv_fg_yourDream);
        tvChallenge = view.findViewById(R.id.tv_fg_yourChallenge);
        tvHandleName = view.findViewById(R.id.tv_fg_userUniqueName);
        buttonEdit = view.findViewById(R.id.btn_fg_editProfile);

        if (listProfileObject != null) {
            tvUserName.setText(listProfileObject.get(Constants.profileObject_fullName));
            tvDream.setText(listProfileObject.get(Constants.profileObject_Dream));

            tvChallenge.setText(listProfileObject.get(Constants.profileObject_Challange));
            tvHandleName.setText(listProfileObject.get(Constants.profileObject_userHandle));
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onProfileDataUpdate(listProfileObject);
            }
        });


        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentUpdateProfileListener) {
            mListener = (OnFragmentUpdateProfileListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentCommunicationListener");
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
     * to the activity
     */
    public interface OnFragmentUpdateProfileListener {
        void onProfileDataUpdate(ArrayList<String> profileDataObject);
    }

}