package app.actionnation.actionapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelperClass2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentStakeGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentStakeGame extends DialogFragment {

    private OnFragmentSuccessBarListener mListener;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner spnPercentage;
    Button button;
    ImageButton imageButtonCancel;

    public FragmentStakeGame() {
        // Required empty public constructor
    }

    public static FragmentStakeGame newInstance(String param1) {
        FragmentStakeGame fragment = new FragmentStakeGame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentStakeGame.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentStakeGame newInstance(String param1, String param2) {
        FragmentStakeGame fragment = new FragmentStakeGame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private void loadSpinner() {
        String[] arraySpinner = new String[]{
                getString(R.string.fm_stakeGame_SelectItem), getString(R.string.fm_stakeGame_70), getString(R.string.fm_stakeGame_80)
                , getString(R.string.fm_stakeGame_90)

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPercentage.setAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_stake_game, container, false);
        spnPercentage = view.findViewById(R.id.spn_fm_target);
        button = view.findViewById(R.id.btn_fm_submitStake);
        imageButtonCancel= view.findViewById(R.id.ImgBtnDialogstakeGameCancel);
        loadSpinner();

       imageButtonCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getDialog().cancel();
           }
       });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelperClass2 db = new DbHelperClass2();


                final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser fbUser = mAuth.getCurrentUser();

                Calendar cNew = Calendar.getInstance();

                String usrId = "";
                if (mAuth.getCurrentUser() != null) {
                    usrId = fbUser.getUid();
                }


                String gameBar = spnPercentage.getSelectedItem().toString();
                int numberGameBar = 0;

                switch (gameBar) {
                    case Constants
                            .StakeGame_70:
                        numberGameBar = Constants.StakeGame_70_Int;

                        break;
                    case Constants
                            .StakeGame_80:
                        numberGameBar = Constants.StakeGame_80_Int;

                        break;
                    case Constants
                            .StakeGame_90:
                        numberGameBar = Constants.StakeGame_90_Int;
                        break;

                }
                ArrayList<String> dataVariables = new ArrayList<>();
                ArrayList<Object> dataObjects = new ArrayList<>();

                dataVariables.add(getString(R.string.fs_UserProfile_userExellenceBar));

                dataObjects.add(numberGameBar);


                db.updateFireUserData(getString(R.string.fs_UserProfile),usrId,dataVariables,dataObjects, firebaseFirestore);

                mListener.onSuccessBarSend(numberGameBar);


            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentSuccessBarListener) {
            mListener = (OnFragmentSuccessBarListener) context;
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
    public interface OnFragmentSuccessBarListener {
        void onSuccessBarSend(int coins);
    }


}