package app.actionnation.actionapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.UserProfile;
import app.actionnation.actionapp.Storage.Constants;
import app.actionnation.actionapp.data.DbHelperClass;
import app.actionnation.actionapp.data.DbHelperClass2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ProfileData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ProfileData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button btnSubmit, btnUpdate;
    private EditText etFirstName, etLastName, etUniqueUserName, etDream, etChallengeDream, etCountry, etState;
    private final String TAG = getClass().getSimpleName();
    String usrId;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private LinearLayout ll;
    ImageButton imgCheckUniqueUserName;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> mProfileObject = new ArrayList<>();

    public Fragment_ProfileData() {
        // Required empty public constructor
    }


    public Fragment_ProfileData(ArrayList<String> profileObject) {
        mProfileObject = profileObject;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ProfileData.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ProfileData newInstance(String param1, String param2) {
        Fragment_ProfileData fragment = new Fragment_ProfileData();
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
        View view = inflater.inflate(R.layout.fragment__profile_data, container, false);


        etFirstName = view.findViewById(R.id.et_FPdFirstName);
        etLastName = view.findViewById(R.id.et_FPDLastName);
        etUniqueUserName = view.findViewById(R.id.et_FPDHandleName);
        etDream = view.findViewById(R.id.et_FPDDream);
        etChallengeDream = view.findViewById(R.id.et_FPDChallenge);
        etCountry = view.findViewById(R.id.et_FPDCountry);
        etState = view.findViewById(R.id.et_FPDState);
        btnSubmit = view.findViewById(R.id.btnFPDSubmit);
        btnUpdate = view.findViewById(R.id.btnFPDUpdate);
        imgCheckUniqueUserName = view.findViewById(R.id.imgBtnSearchUniquename);
        ll = view.findViewById(R.id.ll_FPD);
        imgCheckUniqueUserName.setTag(false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if (mProfileObject != null) {
            btnSubmit.setVisibility(View.GONE);
            etCountry.setVisibility(View.GONE);
            etState.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            String[] userName = mProfileObject.get(Constants.profileObject_fullName).split(Constants.common_emptySpace);
            etFirstName.setText(userName[0]);
            etLastName.setText(userName[1]);
            etDream.setText(mProfileObject.get(Constants.profileObject_Dream));
            etChallengeDream.setText(mProfileObject.get(Constants.profileObject_Challange));
            etUniqueUserName.setText(mProfileObject.get(Constants.profileObject_userHandle));

        }

        imgCheckUniqueUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelperClass Db = new DbHelperClass();
                mAuth = FirebaseAuth.getInstance();
                CommonClass cls = new CommonClass();
                db = FirebaseFirestore.getInstance();
                checkHandleName(db);
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelperClass2 db = new DbHelperClass2();

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser fbUser = mAuth.getCurrentUser();

              if(!mProfileObject.get(Constants.profileObject_userHandle).equals(etUniqueUserName.getText().toString().trim())) {
                  boolean boolHandle = checkHandleName(firebaseFirestore);

                  if (!boolHandle) {
                      return;
                  }
              }

                String usrId = "";
                if (mAuth.getCurrentUser() != null) {
                    usrId = fbUser.getUid();
                }

                ArrayList<String> dataVariables = new ArrayList<>();
                ArrayList<Object> dataObjects = new ArrayList<>();

                dataVariables.add(getString(R.string.fs_UserProfile_userHandle));
                dataVariables.add(getString(R.string.fs_UserProfile_userFirstName));
                dataVariables.add(getString(R.string.fs_UserProfile_userLastName));
                dataVariables.add(getString(R.string.fs_UserProfile_userDream));
                dataVariables.add(getString(R.string.fs_UserProfile_userChallenge));


                dataObjects.add(etUniqueUserName.getText().toString().trim());
                dataObjects.add(etFirstName.getText().toString().trim());
                dataObjects.add(etLastName.getText().toString().trim());
                dataObjects.add(etDream.getText().toString().trim());
                dataObjects.add(etChallengeDream.getText().toString().trim());


                db.updateFireUserData(getString(R.string.fs_UserProfile), usrId, dataVariables, dataObjects, firebaseFirestore);
                CommonClass cls = new CommonClass();
                cls.makeSnackBar(ll,getString(R.string.tv_fg_ProfileData_dataChanged));


                clearForm((ViewGroup) ll);


            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelperClass Db = new DbHelperClass();
                mAuth = FirebaseAuth.getInstance();

                CommonClass cls = new CommonClass();
                usrId = cls.fetchUserId(mAuth);

                if (TextUtils.isEmpty(etFirstName.getText().toString()) || TextUtils.isEmpty(etLastName.getText().toString()) || TextUtils.isEmpty(etUniqueUserName.getText().toString()) || TextUtils.isEmpty(etDream.getText().toString())) {
                    cls.callToast(getContext(), "The Edit Text boxes are empty!");
                } else {
                    UserProfile ph = new UserProfile();
                    boolean boolHandle = checkHandleName(db);

                    if(!boolHandle)
                    {
                        return;
                    }


                    ph.setFb_Id(usrId);

                    ph.setUserFirstName(etFirstName.getText().toString());
                    ph.setUserLastName(etLastName.getText().toString());
                    ph.setUserDream(etDream.getText().toString());
                    ph.setUserHandle(etUniqueUserName.getText().toString());
                    ph.setUserChallenge(etChallengeDream.getText().toString());
                    ph.setUserCountry(etCountry.getText().toString());
                    ph.setUserState(etState.getText().toString());
                    ph.setStatus(Integer.parseInt(getString(R.string.Status_Active_Number)));
                    Db.insertFireUserProfile(getString(R.string.fs_UserProfile), getContext(), ph, db);
                    clearForm((ViewGroup) ll);

                    Intent homepage = new Intent(getContext(), ActivityPreLaunch.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(getString(R.string.common_auth), getString(R.string.common_google));
                    homepage.putExtras(mBundle);
                    startActivity(homepage);


                }
            }
        });


        return view;
    }

    private boolean checkHandleName(FirebaseFirestore db) {

        db.collection(getString(R.string.fs_UserProfile)).whereEqualTo(getString(R.string.fs_UserProfile_userHandle), etUniqueUserName.getText().toString().trim()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                CommonClass cls = new CommonClass();
                imgCheckUniqueUserName.setTag(true);
                cls.makeSnackBar(ll, getString(R.string.tv_fg_ProfileData_alreadyHandleExist));

            }
        });

        boolean boolReturn = (boolean) imgCheckUniqueUserName.getTag();
        return boolReturn;
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