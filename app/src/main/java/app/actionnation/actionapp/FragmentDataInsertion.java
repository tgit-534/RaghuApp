package app.actionnation.actionapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import app.actionnation.actionapp.Database_Content.Personal_Distraction;
import app.actionnation.actionapp.Database_Content.Personal_Habit;
import app.actionnation.actionapp.Database_Content.Personal_Statement;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDataInsertion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDataInsertion extends DialogFragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ViewFlipper vf;
    ImageButton imageButtonHabitCancel, imageButtonDistractionCancel, imageButtonTractionCancel, imageButtonGratitudeCancel, imageButtonBeliefCancel, imageButtonReframeCancel;
    Button btnSave, btnSaveHabit, btnVisualizationSubmit, btnImgSave, btnDistractionSubmit, btnTractionSubmit, btnSaveGratitude, btnReframe, btnBeliefSubmit;
    ImageButton btnImgChoose, btnPrev, btnNxt;
    Spinner spnHabitExpertise;
    RadioGroup rgInspire;
    EditText etPurpose, etMission, etVision, etHabit, etHabitDescription, etVisualization, etDistraction, etTraction, etGratitudeName, etReframe, etBeliefName, etBeliefDesc;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    TextView txtInspireHabit;
    RadioButton rbInspireYes, rbInspireNo;

    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    Bitmap bitmap;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    String usrId;
    FirebaseUser fbUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListenFromActivity mListener;
    Boolean boolCloseStatus = false;


    public FragmentDataInsertion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDataInsertion.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDataInsertion newInstance(String insertionTile) {
        FragmentDataInsertion frag = new FragmentDataInsertion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, insertionTile);
        frag.setArguments(args);
        return frag;
    }


    public static FragmentDataInsertion newInstance(String param1, String param2) {
        FragmentDataInsertion fragment = new FragmentDataInsertion();
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
        View view = inflater.inflate(R.layout.fragment_data_insertion, container, false);

        vf = view.findViewById(R.id.vf_fm_PersonalDetails);

        btnSave = view.findViewById(R.id.btnPdSubmit);
        btnPrev = view.findViewById(R.id.btnPdPrev);
        btnNxt = view.findViewById(R.id.btnPdNext);
        btnSaveGratitude = view.findViewById(R.id.btn_fm_SaveGratitude);
        imageButtonHabitCancel = view.findViewById(R.id.ImgBtnDialogHabitCancel);
        imageButtonDistractionCancel = view.findViewById(R.id.ImgBtnDialogDistractionCancel);
        imageButtonTractionCancel = view.findViewById(R.id.ImgBtnDialogTractionCancel);
        imageButtonGratitudeCancel = view.findViewById(R.id.ImgBtnDialogGratitudeCancel);
        imageButtonBeliefCancel = view.findViewById(R.id.ImgBtnDialogBeliefCancel);
        imageButtonReframeCancel = view.findViewById(R.id.ImgBtnDialogReframeCancel);

        rgInspire = view.findViewById(R.id.rg_fm_inspire);

        etReframe = view.findViewById(R.id.et_fm_ReframeName);
        btnReframe = view.findViewById(R.id.btn_fm_SaveReframe);
        btnReframe.setOnClickListener(this);
        imageButtonHabitCancel.setOnClickListener(cancel_click);
        imageButtonDistractionCancel.setOnClickListener(cancel_click);
        imageButtonTractionCancel.setOnClickListener(cancel_click);
        imageButtonGratitudeCancel.setOnClickListener(cancel_click);
        imageButtonBeliefCancel.setOnClickListener(cancel_click);
        imageButtonReframeCancel.setOnClickListener(cancel_click);

        spnHabitExpertise = view.findViewById(R.id.spn_fm_HabitExpertise);
        txtInspireHabit = view.findViewById(R.id.txt_fm_habitInspire);

        btnSaveHabit = view.findViewById(R.id.btn_fm_SaveHabit);
        btnVisualizationSubmit = view.findViewById(R.id.btnPdSaveVisualization);
        btnImgSave = view.findViewById(R.id.btnPdSaveImages);
        btnImgChoose = view.findViewById(R.id.btnPdChooseFiles);
        btnDistractionSubmit = view.findViewById(R.id.btn_fm_PdSaveDistration);
        btnDistractionSubmit.setOnClickListener(this);

        btnTractionSubmit = view.findViewById(R.id.btn_fm_SaveTraction);
        btnTractionSubmit.setOnClickListener(this);
        btnSaveGratitude.setOnClickListener(this);
        btnSaveHabit.setOnClickListener(this);

        btnBeliefSubmit = view.findViewById(R.id.btn_fm_SaveBelief);
        etBeliefName = view.findViewById(R.id.et_fm_BelieveName);
        btnBeliefSubmit.setOnClickListener(this);


        etTraction = view.findViewById(R.id.et_fm_TractionName);
        etGratitudeName = view.findViewById(R.id.et_fm_Gratitude);
        rbInspireYes = view.findViewById(R.id.rb_inspire_Yes);
        rbInspireNo = view.findViewById(R.id.rb_inspire_No);


        etMission = view.findViewById(R.id.txtPdMission);
        etPurpose = view.findViewById(R.id.txtPdPurpose);
        etHabit = view.findViewById(R.id.et_fm_Habit);
        etVisualization = view.findViewById(R.id.txtPdVisualizationText);
        etDistraction = view.findViewById(R.id.et_fm_Distration);
        btnBeliefSubmit.setTag(false);

        etVision = view.findViewById(R.id.txtPdVision);

        loadSpinner();

        spnHabitExpertise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        rgInspire.setVisibility(View.INVISIBLE);
                        txtInspireHabit.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        rgInspire.setVisibility(View.INVISIBLE);
                        txtInspireHabit.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        rgInspire.setVisibility(View.VISIBLE);
                        txtInspireHabit.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rgInspire.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);

            }
        });


        if (mParam1 != null) {
            if (mParam1.equals(getString(R.string.Page_Redirect_Visualization))) {
                vf.setDisplayedChild(2);
            } else if (mParam1.equals(getString(R.string.Page_Redirect_Habit))) {
                vf.setDisplayedChild(1);
            } else if (mParam1.equals(getString(R.string.Page_Redirect_Attention))) {
                vf.setDisplayedChild(2);
            } else if (mParam1.equals(getString(R.string.Page_Redirect_Traction))) {
                vf.setDisplayedChild(3);
            } else if (mParam1.equals(getString(R.string.Page_Redirect_Gratitude))) {
                vf.setDisplayedChild(4);
            } else if (mParam1.equals(getString(R.string.Page_Redirect_Abundance))) {
                vf.setDisplayedChild(5);
            } else if (mParam1.equals(getString(R.string.Page_Redirect_Belief))) {
                vf.setDisplayedChild(6);
            }
        }
        return view;
    }

    private View.OnClickListener cancel_click = new View.OnClickListener() {
        public void onClick(View v) {

            if ((boolean) btnBeliefSubmit.getTag()) {

                refreshHome();
                btnBeliefSubmit.setTag(false);
            }
            getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mListener.doSomethingInFragment(vf.getDisplayedChild());
                    boolCloseStatus = true;

                }
            });

            getDialog().dismiss();

        }
    };

    private void refreshHome() {
        Intent i = null;
        if (mParam1.equals(getString(R.string.Page_Redirect_Attention))) {


        } else if (mParam1.equals(getString(R.string.Page_Redirect_Traction))) {

            if (getActivity().getApplication().getClass().getName().equals(Constants.ClassName_Attention)) {
                i = new Intent(getContext(), ActivityAttention.class);

                Bundle mBundle = new Bundle();
                mBundle.putString(Constants.common_auth, Constants.common_google);
                mBundle.putIntegerArrayList(Constants.Intent_ArrayGameScore, getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore))));
                mBundle.putString(Constants.Intent_DataInsertion, getString(R.string.Page_Redirect_Traction));

                i.putExtras(mBundle);
                getContext().startActivity(i);
            }

        } else if (mParam1.equals(getString(R.string.Page_Redirect_Gratitude))) {

            if (getActivity().getApplication().getClass().getName().equals(Constants.ClassName_Happiness)) {

                i = new Intent(getContext(), ActivityHappiness.class);

                Bundle mBundle = new Bundle();
                mBundle.putString(Constants.common_auth, Constants.common_google);
                mBundle.putIntegerArrayList(Constants.Intent_ArrayGameScore, getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore))));
                mBundle.putString(Constants.Intent_DataInsertion, getString(R.string.Page_Redirect_Gratitude));

                i.putExtras(mBundle);
                getContext().startActivity(i);
            }
        } else if (mParam1.equals(getString(R.string.Page_Redirect_Abundance))) {

            if (getActivity().getApplication().getClass().getName().equals(Constants.ClassName_Happiness)) {

                i = new Intent(getContext(), ActivityHappiness.class);

                Bundle mBundle = new Bundle();
                mBundle.putString(Constants.common_auth, Constants.common_google);
                mBundle.putIntegerArrayList(Constants.Intent_ArrayGameScore, getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore))));
                mBundle.putString(Constants.Intent_DataInsertion, getString(R.string.Page_Redirect_Abundance));
                i.putExtras(mBundle);
                getContext().startActivity(i);
            }
        } else if (mParam1.equals(getString(R.string.Page_Redirect_Belief))) {

            if (getActivity().getApplication().getClass().getName().equals(Constants.ClassName_Belief)) {

                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();

                startActivity(intent);

            }

            /*
            i = new Intent(getContext(), ActivityOurBelief.class);
            Bundle mBundle = new Bundle();
            mBundle.putString(Constants.common_auth, Constants.common_google);
            mBundle.putIntegerArrayList(Constants.Intent_ArrayGameScore, getActivity().getIntent().getIntegerArrayListExtra((getString(R.string.Intent_ArrayGameScore))));
            i.putExtras(mBundle);
            getContext().startActivity(i);*/
        }


    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (!boolCloseStatus) {
            mListener.doSomethingInFragment(vf.getDisplayedChild());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!boolCloseStatus) {
            mListener.doSomethingInFragment(vf.getDisplayedChild());
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        CommonClass cls = new CommonClass();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        usrId = "";
        DbHelper dbh = new DbHelper(getContext());
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        if (i == R.id.btnPdSubmit) {
            if (TextUtils.isEmpty(etMission.getText()) || TextUtils.isEmpty(etPurpose.getText()) || TextUtils.isEmpty(etVision.getText())) {
                cls.callToast(getContext(), "The Edit Text boxes are empty!");
            } else {
                CollectionReference dbPersonalStatements = db.collection(getString(R.string.fs_Personal_statements));
                Personal_Statement ps = new Personal_Statement();
                ps.setFb_Id(usrId);
                ps.setPersonalMission(etMission.getText().toString());
                ps.setPersonnalPurpose(etPurpose.getText().toString());
                ps.setPersonalVision(etVision.getText().toString());
                ps.setStatus(1);
                dbPersonalStatements.add(ps).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        CommonClass cls = new CommonClass();

                        cls.callToast(getContext(), "Inserting Done!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
                    }
                });
                clearForm((ViewGroup) getView().findViewById(R.id.vfPersonalDetails));
            }

        } else if (i == R.id.btn_fm_SaveHabit) {
            DbHelperClass Db = new DbHelperClass();

            if (TextUtils.isEmpty(etHabit.getText().toString())) {
                cls.callToast(getContext(), "The Edit Text boxes are empty!");
            } else {
                Personal_Habit ph = new Personal_Habit();
                ph.setFb_Id(usrId);
                Calendar c = Calendar.getInstance();
                long presentDate = c.getTimeInMillis();
                int dayOfYear = c.get(Calendar.DAY_OF_YEAR);


                ph.setHabit(etHabit.getText().toString());
                ph.setHabitWorks(etHabit.getText().toString());
                ph.setStatus(Integer.parseInt(getString(R.string.Status_Active_Number)));
                ph.setHabitDayOfTheYear(dayOfYear);
                ph.setHabitDays(Integer.parseInt(getString(R.string.ht_HabitDaysInitialization)));
                ph.setHabitDate(presentDate);
                ph.setHabitLevel(spnHabitExpertise.getSelectedItem().toString());
                if (spnHabitExpertise.getSelectedItemPosition() == 3) {
                    RadioButton rb = (RadioButton) rgInspire.findViewById(rgInspire.getCheckedRadioButtonId());

                    if (rb.getText().equals("Yes")) {
                        ph.setHabitInspire(Constants.Status_One);
                    } else {
                        ph.setHabitInspire(Constants.Status_Zero);
                    }

                }

                Db.insertFireStoreData(getString(R.string.fs_PersonalHabits), getContext(), ph, db);
                dbh.insertHabit(usrId, etHabit.getText().toString(), 0, "Dummy");


            }
            clearForm((ViewGroup) getView().findViewById(R.id.vf_fm_PersonalDetails));
        } else if (i == R.id.btn_fm_PdSaveDistration) {
            DbHelperClass Db = new DbHelperClass();
            Personal_Distraction pd = new Personal_Distraction();
            pd.setFb_Id(usrId);
            pd.setDistrationName(etDistraction.getText().toString());
            pd.setStatus(Constants.Status_One);
            Db.insertFireDistraction(getString(R.string.fs_PersonalDistraction), getContext(), pd, db);
            clearForm((ViewGroup) getView().findViewById(R.id.vf_fm_PersonalDetails));
        } else if (i == R.id.btn_fm_SaveTraction) {
            dbh.insertTractionList(etTraction.getText().toString(), 1, usrId);
            clearForm((ViewGroup) getView().findViewById(R.id.vf_fm_PersonalDetails));
        } else if (i == R.id.btn_fm_SaveGratitude) {
            dbh.insertGratitudeList(etGratitudeName.getText().toString(), 1);
            clearForm((ViewGroup) getView().findViewById(R.id.vf_fm_PersonalDetails));


        } else if (i == R.id.btn_fm_SaveReframe) {

            dbh.insertAbundance(etReframe.getText().toString(), usrId, 1);
            clearForm((ViewGroup) getView().findViewById(R.id.vf_fm_PersonalDetails));
        } else if (i == R.id.btn_fm_SaveBelief) {
            dbh.insertBeliefList(etBeliefName.getText().toString(), etBeliefName.getText().toString(), 1, usrId);
            clearForm((ViewGroup) getView().findViewById(R.id.vf_fm_PersonalDetails));

        }

    }

    private void loadSpinner() {
        String[] arraySpinner = new String[]{
                getString(R.string.fm_Habit_SelectItem), getString(R.string.fm_Habit_SelectItem_Beginner), getString(R.string.fm_Habit_SelectItem_Medium)
                , getString(R.string.fm_Habit_SelectItem_Advanced)

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHabitExpertise.setAdapter(adapter);


    }

    private void clearForm(ViewGroup group) {

        btnBeliefSubmit.setTag(true);
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener =
                    (ListenFromActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement LogoutUser");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface ListenFromActivity {
        void doSomethingInFragment(int windowNumber);
    }
}