package app.actionnation.actionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import app.actionnation.actionapp.Database_Content.Personal_Book;
import app.actionnation.actionapp.Database_Content.Personal_Habit;
import app.actionnation.actionapp.data.DbHelperClass;

public class ActivityReading extends BaseClassUser implements View.OnClickListener {
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth mAuth;
    Button btnInsertBook;
    EditText etBookName;
    private FirebaseFirestore db;
    Spinner spnReadingChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        generatePublicMenu();
        db = FirebaseFirestore.getInstance();

        btnInsertBook = findViewById(R.id.btn_rd_Submit);
        etBookName = findViewById(R.id.et_rd_BookName);
        btnInsertBook.setOnClickListener(this);
        spnReadingChoices = findViewById(R.id.spn_rd_category);
        setSpinnerStatus();

        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rv_rd_Books);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityReading.this));
        recyclerView.setHasFixedSize(false);
        fetch(Integer.parseInt(getString(R.string.ar_BookReading_Number)));

        spnReadingChoices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int bookStatus = 0;
                String strBookStatus = spnReadingChoices.getSelectedItem().toString();

                if (strBookStatus.equals(getString(R.string.ar_BookReading))) {
                    bookStatus = Integer.parseInt(getString(R.string.ar_BookReading_Number));
                } else if (strBookStatus.equals(getString(R.string.ar_BookToRead))) {
                    bookStatus = Integer.parseInt(getString(R.string.ar_BookToRead_Number));
                } else if (strBookStatus.equals(getString(R.string.ar_BookAlRead))) {
                    bookStatus = Integer.parseInt(getString(R.string.ar_BookAlRead_Number));
                }

                mAuth = FirebaseAuth.getInstance();
                recyclerView = findViewById(R.id.rv_rd_Books);
                recyclerView.setLayoutManager(new LinearLayoutManager(ActivityReading.this));
                recyclerView.setHasFixedSize(false);
                fetch(bookStatus);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void fetch(Integer status) {
        final FirebaseUser fbUser = mAuth.getCurrentUser();
        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        com.google.firebase.firestore.Query query1 = rootRef.collection(getString(R.string.fs_PersonBook)).whereEqualTo(getString(R.string.fb_Column_Fb_Id), usrId).whereEqualTo("status", status);

        DbHelperClass dbh = new DbHelperClass();
        adapter = dbh.GetFireStoreAdapterBooks(adapter, getString(R.string.fs_PersonBook), query1, spnReadingChoices.getSelectedItem().toString());

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();
        CommonClass cls = new CommonClass();

        String usrId = "";
        if (mAuth.getCurrentUser() != null) {
            usrId = fbUser.getUid();
        } else {
            return;
        }

        if (i == R.id.btn_rd_Submit) {
            DbHelperClass Db = new DbHelperClass();
            Personal_Book pb = new Personal_Book();
            pb.setFb_Id(usrId);
            //ph.setHabitDate(Timestamp.valueOf());
            pb.setBook_Name(etBookName.getText().toString());
            pb.setStatus(1);
            Db.insertFireBook(getString(R.string.fs_PersonBook), ActivityReading.this, pb, db);
        }
    }


    private void setSpinnerStatus() {

        String[] data = {
                getString(R.string.ar_BookReading),
                getString(R.string.ar_BookAlRead),
                getString(R.string.ar_BookToRead)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityReading.this, android.R.layout.simple_spinner_item, data);

        spnReadingChoices.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public static class ViewHolderReading extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdReading;
        public final CheckBox chkReading;

        public Personal_Habit mItem;

        public ViewHolderReading(@NonNull View view) {
            super(view);
            mView = view;
            mIdReading = view.findViewById(R.id.txt_BookName_name);
            chkReading = view.findViewById(R.id.chkBoookRead);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdReading.getText() + "'";
        }
    }
}



