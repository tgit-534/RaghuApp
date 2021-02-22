package app.actionnation.actionapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import app.actionnation.actionapp.Database_Content.Personal_Excercise;
import app.actionnation.actionapp.Database_Content.Personal_Habit;
import app.actionnation.actionapp.data.DbHelper;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityExercise extends BaseClassUser implements View.OnClickListener {
    private Button btnSubmit, btnRoutine, btnExeShow;
    private EditText etExcerciseName, etRoutineName;
    Spinner spnRoutines;
    ListView lv;
    private FirebaseFirestore db;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    DbHelper dbsqllite;
    String[] strExeSinglePattern;
    ViewFlipper vf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        generatePublicMenu();

        lv = findViewById(R.id.listExe);
        etRoutineName = findViewById(R.id.et_exe_routine);
        spnRoutines = findViewById(R.id.spnExeRoutines);
        btnSubmit = findViewById(R.id.btn_exe_Submit);
        btnRoutine = findViewById(R.id.btn_exe_Routine);
        etExcerciseName = findViewById(R.id.et_exe_name);
        btnExeShow = findViewById(R.id.btn_exeRoutine_Submit);
        vf = findViewById(R.id.vfExcercise);


        btnExeShow.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnRoutine.setOnClickListener(this);
        loadSpinnerData();

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rv_ex_Excercis);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityExercise.this));
        recyclerView.setHasFixedSize(false);
        fetch();
    }


    private void fetch() {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_PersonalExcercise)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId);

        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterExcercise(adapter, getString(R.string.fs_PersonalExcercise), query1, ActivityExercise.this);

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        dbsqllite = new DbHelper(ActivityExercise.this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();
        CommonClass cls = new CommonClass();

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }

        if (i == R.id.btn_exe_Submit) {
            DbHelperClass Db = new DbHelperClass();
            Personal_Excercise pb = new Personal_Excercise();
            pb.setFb_Id(usrId);
            pb.setExcerciseName(etExcerciseName.getText().toString());
            pb.setStatus(1);
            Db.insertFireExcercise(getString(R.string.fs_PersonalExcercise), ActivityExercise.this, pb, db);
        } else if (i == R.id.btn_exe_Routine) {
            if (TextUtils.isEmpty(etRoutineName.getText()))
            {
                cls.callToast(ActivityExercise.this, getString(R.string.Toast_Textbox_Empty));
            }


            Cursor res = dbsqllite.getDataExcercise();
            String strExcercisePattern = "";
            ArrayList<String> strExePattern = new ArrayList<>();
            if (res.getCount() == 0) {
                cls.callToast(ActivityExercise.this, getString(R.string.Activity_Excercise_Toast_SelectRoutine));
                return;
            } else {
                while (res.moveToNext()) {
                    strExePattern.add(res.getString(2));
                    strExcercisePattern = res.getString(2) + "&" + strExcercisePattern;
                }
                dbsqllite.insertExcerciseRoutine(usrId, strExcercisePattern, etRoutineName.getText().toString());
                dbsqllite.deleteExcercise();
            }
        } else if (i == R.id.btn_exeRoutine_Submit) {
            //First View
            Cursor res1 = dbsqllite.getDataRoutine(spnRoutines.getSelectedItem().toString());
            String strExcercisePattern = "";
            ArrayList<String> strExePattern = new ArrayList<>();
            if (res1.getCount() == 0) {
                cls.callToast(ActivityExercise.this, getString(R.string.Toast_No_Data));
                return;

            } else {
                while (res1.moveToNext()) {
                    strExePattern.add(res1.getString(2));
                    strExePattern.add(res1.getString(3));
                    strExcercisePattern = res1.getString(2);

                }
            }

            strExeSinglePattern = strExcercisePattern.split("&");

            MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, strExeSinglePattern);
            lv.setAdapter(adapter);
        }

    }

    public void SetExcercise(View view) {
        vf.showNext();
        if (vf.getDisplayedChild() == 1) {
            dbsqllite = new DbHelper(ActivityExercise.this);
            dbsqllite.deleteExcercise();
        }
    }

    public void RemoveExcercise(View view) {
        dbsqllite = new DbHelper(ActivityExercise.this);
        int i = dbsqllite.deleteRoutine(spnRoutines.getSelectedItem().toString());
        if(i>0)
            Toast.makeText(ActivityExercise.this,"Deleted",Toast.LENGTH_LONG);
    }


    private void loadSpinnerData() {
        // database handler
        DbHelper db = new DbHelper(getApplicationContext());

        Cursor res1 = db.getDataRoutine();
        ArrayList<String> lables = new ArrayList<>();
        if (res1.getCount() == 0) {
        } else {
            while (res1.moveToNext()) {
                lables.add(res1.getString(3));
            }
        }

        // Spinner Drop down elements

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnRoutines.setAdapter(dataAdapter);
    }

    public static class ViewHolderExcercise extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdExcercise, mIdMessage;
        public final Button btnExeRoutineSubmit;

        public final Spinner spnExe;


        public Personal_Habit mItem;

        public ViewHolderExcercise(@NonNull View view) {
            super(view);
            mView = view;
            mIdExcercise = view.findViewById(R.id.txt_Excercise_name);

            spnExe = view.findViewById(R.id.spnExcerciseLayout);
            mIdMessage = view.findViewById(R.id.txt_exe_Message);
            btnExeRoutineSubmit = view.findViewById(R.id.btn_exe_RoutineSubmit);


        }


        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdExcercise.getText() + "'";
        }
    }

    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MySimpleArrayAdapter(Context context, String[] values) {
            super(context, R.layout.excerciselayout, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.excerciselayout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label1);
            TextView textView2 = (TextView) rowView.findViewById(R.id.label2);
            final Button btnClock = rowView.findViewById(R.id.btn_el_Alarm);
            btnClock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonClass cls = new CommonClass();
                    //    cls.callToast(context, "Hi");

                    cls.setTime(ActivityExercise.this, 0, Integer.parseInt(btnClock.getTag().toString()));

                }
            });

            String[] s = values[position].split(",");
            textView.setText(s[0]);
            textView2.setText(s[1]);
            btnClock.setTag(s[1]);
            return rowView;
        }


    }

}