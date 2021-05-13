package app.actionnation.actionapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGameDefine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGameDefine extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText etGameName, etGameDate, etGameEnds;
    Button btnNewGameSubmit;
    Button btnPickStartDate, btnPickEndDate;
    int mYear, mMonth, mDay;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    Date dt;
    Long dateInMilli, getDateInMilli2;


    public FragmentGameDefine() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGameDefine.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGameDefine newInstance(String param1, String param2) {
        FragmentGameDefine fragment = new FragmentGameDefine();
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
        View view = inflater.inflate(R.layout.fragment_game_define, container, false);

        etGameName = view.findViewById(R.id.et_fm_gameName);
        etGameDate = view.findViewById(R.id.et_fm_gameDate);
        etGameEnds = view.findViewById(R.id.et_fm_gameEnds);

        btnPickStartDate = view.findViewById(R.id.btn_fm_gameStartDate);
        btnPickEndDate = view.findViewById(R.id.btn_fm_gameEndDate);
        btnNewGameSubmit = view.findViewById(R.id.btn_fm_gameSubmit);

        btnPickStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etGameDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();




            }
        });

        btnPickEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etGameEnds.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnNewGameSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                mAuth = FirebaseAuth.getInstance();
                int startDayOfTheYear = 0, startYear = 0, endDayOftheYear = 0, endYear = 0;
                ArrayList<String> strUser = fetchUserArray();

                DbHelperClass dbh = new DbHelperClass();
                TeamGame ut = new TeamGame();

                ut.setFb_Id(strUser.get(0));
                ut.setStatus(Constants.Status_One);
                ut.setTimestamp(null);
                ut.setGameName(etGameName.getText().toString());


                String dtStartDate = etGameDate.getText() + " " + "00:00:00";
                String dtEndDate = etGameEnds.getText() + " " + "00:00:00";


                try {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    //  String format = simpleDateFormat.format(new Date());
                    dt = simpleDateFormat.parse(dtStartDate);
                    dateInMilli = dt.getTime();


                    Calendar calToday = Calendar.getInstance();
                    calToday.setTimeInMillis(dateInMilli);
                    startYear = calToday.get(Calendar.YEAR);
                    startDayOfTheYear = calToday.get(Calendar.DAY_OF_YEAR);

                    dt = simpleDateFormat.parse(dtEndDate);
                    getDateInMilli2 = dt.getTime();


                    calToday.setTimeInMillis(getDateInMilli2);
                    endYear = calToday.get(Calendar.YEAR);
                    endDayOftheYear = calToday.get(Calendar.DAY_OF_YEAR);

                    //date.date.gettime.gettime
                } catch (ParseException e) {


                }

                ut.setStartDate(dateInMilli);

                ut.setEndDate(getDateInMilli2);
                ut.setStartDay(startDayOfTheYear);
                ut.setStartYear(startYear);
                ut.setEndDay(endDayOftheYear);
                ut.setEndYear(endYear);
                ut.setGameMasterName(mAuth.getCurrentUser().getDisplayName());
                dbh.insertFireTeamGame(getString(R.string.fs_TeamGame), getContext(), ut, db);
            }
        });


        return view;
    }


    protected ArrayList<String> fetchUserArray() {
        ArrayList<String> strUser = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
            strUser.add(usrId);
            strUser.add(fbUser.getDisplayName());
            strUser.add(fbUser.getEmail());
        }
        return strUser;
    }

}