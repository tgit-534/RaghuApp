package app.actionnation.actionapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
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
import app.actionnation.actionapp.adapters.ValidationData;
import app.actionnation.actionapp.data.DbHelperClass;
import app.actionnation.actionapp.data.DbHelperClass2;
import app.actionnation.actionapp.data.OnFragmentRefreshMainListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGameDefine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGameDefine extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static FragmentManager fragmentManager;
    private OnFragmentRefreshMainListener mListener;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText etGameName, etGameDate, etGameEnds, etCoinsStake;
    Button btnNewGameSubmit;
    Button btnPickStartDate, btnPickEndDate;
    int mYear, mMonth, mDay;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    Date dt;
    Long dateInMilli, getDateInMilli2;
    LinearLayout linearLayout;
    ImageButton imgButtonCancel;
    TextView tvValidateText;


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
    public static FragmentGameDefine newInstance(String param1) {
        FragmentGameDefine fragment = new FragmentGameDefine();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_define, container, false);

        etGameName = view.findViewById(R.id.et_fm_gameName);
        etGameDate = view.findViewById(R.id.et_fm_gameDate);
        etGameEnds = view.findViewById(R.id.et_fm_gameEnds);
        linearLayout = view.findViewById(R.id.ll_gameDefine);
        etCoinsStake = view.findViewById(R.id.et_fm_gameCoins);
        tvValidateText = view.findViewById(R.id.tv_fm_gameDefineValidate);

        btnPickStartDate = view.findViewById(R.id.btn_fm_gameStartDate);
        btnPickEndDate = view.findViewById(R.id.btn_fm_gameEndDate);
        btnNewGameSubmit = view.findViewById(R.id.btn_fm_gameSubmit);
        imgButtonCancel = view.findViewById(R.id.ImgBtnDialogGameDefineCancel);

        imgButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();

            }
        });

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
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

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
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        btnNewGameSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] strGameObj = mParam1.split(getString(R.string.fm_fieldPartition));
                int startDayOfTheYear = 0, startYear = 0, endDayOftheYear = 0, endYear = 0;

                long dtExistingStartDate = Constants.Status_Zero, dtExistingEndDate = Constants.Status_Zero;
                if (strGameObj.length > Constants.Status_One) {
                    dtExistingStartDate = Long.parseLong(strGameObj[1]);
                    dtExistingEndDate = Long.parseLong(strGameObj[2]);
                }
                String dtStartDate = etGameDate.getText() + " " + "00:00:00";
                String dtEndDate = etGameEnds.getText() + " " + "00:00:00";

                Calendar calExistingStartGame = Calendar.getInstance();
                Calendar calExistingEndGame = Calendar.getInstance();

                try {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    //  String format = simpleDateFormat.format(new Date());
                    dt = simpleDateFormat.parse(dtStartDate);
                    dateInMilli = dt.getTime();
                    ValidationData validationData = new ValidationData();

                    if (strGameObj.length > Constants.Status_One) {

                        calExistingStartGame.setTimeInMillis(dtExistingStartDate);
                        calExistingEndGame.setTimeInMillis(dtExistingEndDate);
                    }

                    Calendar calToday = Calendar.getInstance();
                    calToday.setTimeInMillis(dateInMilli);
                    startYear = calToday.get(Calendar.YEAR);
                    startDayOfTheYear = calToday.get(Calendar.DAY_OF_YEAR);

                    final int startYeardialog = calToday.get(Calendar.YEAR);
                    final int startDayOfTheYeardialog = calToday.get(Calendar.DAY_OF_YEAR);


                    dt = simpleDateFormat.parse(dtEndDate);
                    getDateInMilli2 = dt.getTime();


                    calToday.setTimeInMillis(getDateInMilli2);
                    endYear = calToday.get(Calendar.YEAR);
                    endDayOftheYear = calToday.get(Calendar.DAY_OF_YEAR);

                    final int endYeardialog = calToday.get(Calendar.YEAR);
                    final int endDayOftheYeardialog = calToday.get(Calendar.DAY_OF_YEAR);


                    if (etGameName.getText().toString().isEmpty()) {
                        tvValidateText.setText(getString(R.string.validate_GameName));
                        tvValidateText.setVisibility(View.VISIBLE);
                        return;
                    }

                    if (etCoinsStake.getText().toString().isEmpty()) {
                        tvValidateText.setText(getString(R.string.validate_textBoxCoins));
                        tvValidateText.setVisibility(View.VISIBLE);
                        return;
                    }

                    boolean boolDateTime = validationData.validateDateForGame(dateInMilli);
                    if (!boolDateTime) {
                        tvValidateText.setText(getString(R.string.validate_dateSelect));
                        tvValidateText.setVisibility(View.VISIBLE);
                        return;
                    }

                    boolDateTime = validationData.validateDateForGame(getDateInMilli2);
                    if (!boolDateTime) {
                        tvValidateText.setText(getString(R.string.validate_dateSelect));
                        tvValidateText.setVisibility(View.VISIBLE);
                        return;
                    }

                    if (getDateInMilli2 < dateInMilli) {
                        tvValidateText.setText(getString(R.string.validate_dateLessotherdate));
                        tvValidateText.setVisibility(View.VISIBLE);
                        return;
                    }

                    if ((dtExistingStartDate != Constants.Status_Zero) || (dtExistingStartDate >= dateInMilli && dtExistingStartDate <= getDateInMilli2) || (dtExistingEndDate >= dateInMilli && dtExistingEndDate <= getDateInMilli2)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle(getString(R.string.fm_AlertDialog_Replace));
                        builder.setMessage(getString(R.string.act_MainObjective_AlertDialog_Now));

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                insetData(startDayOfTheYeardialog, startYeardialog, endDayOftheYeardialog, endYeardialog);

                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Snackbar snackbar1 = Snackbar.make(linearLayout, getString(R.string.fm_AlertDialog_NoReplace), Snackbar.LENGTH_SHORT);
                                snackbar1.show();

                                // Do nothing
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                    } else {
                        insetData(startDayOfTheYear, startYear, endDayOftheYear, endYear);

                    }
                } catch (
                        ParseException e) {
                }
            }


        });


        return view;
    }

    public void insetData(int startDayOfTheYear, int startYear, int endDayOftheYear, int endYear) {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        ArrayList<String> strUser = fetchUserArray();

        TeamGame ut = new TeamGame();

        DbHelperClass dbh = new DbHelperClass();


        ut.setFb_Id(strUser.get(0));
        ut.setStatus(Constants.Status_One);
        ut.setTimestamp(null);
        ut.setGameName(etGameName.getText().toString());

        ut.setStartDate(dateInMilli);
        ut.setEndDate(getDateInMilli2);
        ut.setStartDay(startDayOfTheYear);
        ut.setStartYear(startYear);
        ut.setEndDay(endDayOftheYear);
        ut.setEndYear(endYear);
        int coinsStake = Integer.parseInt(etCoinsStake.getText().toString());
        ut.setCoinsAtStake(coinsStake);
        ut.setGameMasterName(mAuth.getCurrentUser().getDisplayName());
        String strGameFbid = ut.getFb_Id() + String.valueOf(ut.getStartDay()) + String.valueOf(ut.getStartYear()) + getString(R.string.fm_fieldPartition) + String.valueOf(ut.getStartDate()) + getString(R.string.fm_fieldPartition) + String.valueOf(ut.getStartDate());
        ut.setGameDocumentId(strGameFbid.split(getString(R.string.fm_fieldPartition))[0]);

        dbh.insertFireTeamGame(getString(R.string.fs_TeamGame), getContext(), ut, db);

        // Update Profile with GameId
        ArrayList<String> dataVariables = new ArrayList<>();
        ArrayList<Object> dataObjects = new ArrayList<>();

        dataVariables.add(getString(R.string.fs_UserProfile_gameDocId));
        dataVariables.add(getString(R.string.fs_UserProfile_userCoinsPerDay));
        dataObjects.add(strGameFbid);
        dataObjects.add(coinsStake);
        DbHelperClass2 dbc = new DbHelperClass2();
        dbc.updateFireUserData(getString(R.string.fs_UserProfile), ut.getFb_Id(), dataVariables, dataObjects, db);

        mListener.refreshMain(ut);

        CommonClass cls = new CommonClass();
        cls.makeSnackBar(linearLayout, getString(R.string.fm_GameDefine_DataInserted));
        clearForm(linearLayout);
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentRefreshMainListener) {
            mListener = (OnFragmentRefreshMainListener) context;
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




}