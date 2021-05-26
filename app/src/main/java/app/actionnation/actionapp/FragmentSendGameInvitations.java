package app.actionnation.actionapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import java.util.ArrayList;

import app.actionnation.actionapp.utils.recyclerCommonAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSendGameInvitations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSendGameInvitations extends Fragment {

    private OnFragmentCommunicationListener mListener;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText etSendInvitations;
    RecyclerView recyclerView, recyclerViewSelectGame;
    Button btnAddEmails;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    LinearLayout llFirstLayout, llSecondLayout;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email = "";

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ArrayList<String> strEmails = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_game_invitations, container, false);
        etSendInvitations = view.findViewById(R.id.etInviteForTeamGame);
        recyclerView = view.findViewById(R.id.listTeamMembers);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        btnAddEmails = view.findViewById(R.id.btn_teamGame_invite);

        btnAddEmails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmaildIds();
            }
        });


        etSendInvitations.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String email = etSendInvitations.getText().toString().trim();

                if (email.matches(emailPattern) && s.length() > 0) {
                    Toast.makeText(getContext(), "valid email address", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    //or
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        updateRecylclerView();

        return view;
    }

    private void updateRecylclerView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerCommonAdapter adapter = new recyclerCommonAdapter(getContext(), strEmails);
        recyclerView.setAdapter(adapter);
    }


    private ArrayList<String> getEmaildIds() {

        email = etSendInvitations.getText().toString().trim();
        if (email.matches(emailPattern) && email.length() > 0) {
            strEmails.add(etSendInvitations.getText().toString().trim());
            updateRecylclerView();
            mListener.onPlayersSend(strEmails);
        } else {
            Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }
        etSendInvitations.setText("");

        return strEmails;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView emailId;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            emailId = view.findViewById(R.id.belief_name);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentCommunicationListener) {
            mListener = (OnFragmentCommunicationListener) context;
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
    public interface OnFragmentCommunicationListener {
        void onPlayersSend(ArrayList<String> listPlayers);
    }


}