package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import app.actionnation.actionapp.data.DbHelperClass2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSendGameInvitations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSendGameInvitations extends DialogFragment {

    //private OnFragmentCommunicationListener mListener;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etSendInvitations, etSendInvitations2, etSendInvitations3, etSendInvitations4, etSendInvitations5;
    Button btnAddEmails;
    LinearLayout llFirstLayout;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email = "";
    ImageButton imgCancel;

    public FragmentSendGameInvitations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSendGameInvitations.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSendGameInvitations newInstance(String param1, String param2) {
        FragmentSendGameInvitations fragment = new FragmentSendGameInvitations();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentSendGameInvitations newInstance(ArrayList<String> strGameObj) {
        FragmentSendGameInvitations fragment = new FragmentSendGameInvitations();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, strGameObj);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);

        if (getArguments() != null) {
            gameObj = getArguments().getStringArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ArrayList<String> gameObj = new ArrayList<>();

    ArrayList<String> strEmails = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_game_invitations, container, false);
        etSendInvitations = view.findViewById(R.id.etInviteForTeamGame);
        etSendInvitations2 = view.findViewById(R.id.etInviteForTeamGame2);
        etSendInvitations3 = view.findViewById(R.id.etInviteForTeamGame3);
        etSendInvitations4 = view.findViewById(R.id.etInviteForTeamGame4);
        etSendInvitations5 = view.findViewById(R.id.etInviteForTeamGame4);
        imgCancel = view.findViewById(R.id.ImgBtnDialogSendGameInvitationCancel);

        llFirstLayout = view.findViewById(R.id.ll_sendInvitations);

        etSendInvitations5 = view.findViewById(R.id.etInviteForTeamGame5);

        btnAddEmails = view.findViewById(R.id.btn_teamGame_invite);

        btnAddEmails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> strEmailIds = getEmaildIds();
                if (strEmailIds != null) {
                    insertGame(strEmailIds);
                }
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });


        return view;
    }


    private ArrayList<String> getEmaildIds() {

        email = etSendInvitations.getText().toString().trim();
        if (email.matches(emailPattern) || email.length() == 0) {
            if (email.length() != 0) {
                strEmails.add(email);
            }


        } else {
            Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            etSendInvitations.setText("");
            return null;

        }

        email = etSendInvitations2.getText().toString().trim();
        if (email.matches(emailPattern) || email.length() == 0) {
            if (email.length() != 0) {
                strEmails.add(email);
            }
        } else {
            Toast.makeText(getContext(), "Invalid email address for Text Box 2", Toast.LENGTH_SHORT).show();
            etSendInvitations2.setText("");
            return null;

        }

        email = etSendInvitations3.getText().toString().trim();
        if (email.matches(emailPattern) || email.length() == 0) {
            if (email.length() != 0) {
                strEmails.add(email);
            }
        } else {
            Toast.makeText(getContext(), "Invalid email address for Text Box 3", Toast.LENGTH_SHORT).show();
            etSendInvitations3.setText("");
            return null;

        }

        email = etSendInvitations4.getText().toString().trim();
        if (email.matches(emailPattern) || email.length() == 0) {
            if (email.length() != 0) {
                strEmails.add(email);
            }
        } else {
            Toast.makeText(getContext(), "Invalid email address for Text Box 4", Toast.LENGTH_SHORT).show();
            etSendInvitations4.setText("");
            return null;

        }

        email = etSendInvitations5.getText().toString().trim();
        if (email.matches(emailPattern) || email.length() == 0) {
            if (email.length() != 0) {
                strEmails.add(email);
            }
        } else {
            Toast.makeText(getContext(), "Invalid email address for Text Box 5", Toast.LENGTH_SHORT).show();
            etSendInvitations5.setText("");
            return null;

        }

        return strEmails;
    }

    private void insertGame(ArrayList<String> players) {

        DbHelperClass2 dbc = new DbHelperClass2();
        ArrayList<String> dataVariables = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<Object> dataObjects = new ArrayList<>();
        ArrayList<String> gameDataObjects = gameObj;

        dataVariables.add(getString(R.string.fs_TeamGame_gamePlayers));
        dataVariables.add(getString(R.string.fs_TeamGame_noOfPlayers));


        if (gameDataObjects.contains(getString(R.string.fm_arrayListPartition))) {
            if (gameDataObjects != null) {
                int partitionLine = gameDataObjects.indexOf(getString(R.string.fm_arrayListPartition));
                List<String> alreadyGamePlayers = gameDataObjects.subList(0, partitionLine);
                String gameDocId = gameDataObjects.get(partitionLine + 1);
                alreadyGamePlayers.addAll(players);
                ArrayList<String> dataInsert = new ArrayList<>();

                for (String data : alreadyGamePlayers) {
                    dataInsert.add(data);
                }

                dataObjects.add(alreadyGamePlayers);
                dataObjects.add(alreadyGamePlayers.size());
                dbc.updateFireUserData(getString(R.string.fs_TeamGame), gameDocId, dataVariables, dataObjects, db);

            }
        } else {

            if (players != null) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                ArrayList<String> playersNew = new ArrayList<>();
                playersNew.add(email);
                playersNew.addAll(players);

                dataObjects.add(playersNew);
                dataObjects.add(playersNew.size());
                dbc.updateFireUserData(getString(R.string.fs_TeamGame), gameDataObjects.get(0), dataVariables, dataObjects, db);
            } else {
                CommonClass cls = new CommonClass();
                cls.makeSnackBar(llFirstLayout, "There are no new users to add!");
            }
        }
        clearForm(llFirstLayout);
    }


    private void clearForm(ViewGroup group) {

        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }


}