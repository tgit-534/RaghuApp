package app.actionnation.actionapp.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.actionnation.actionapp.ActivityGameCreation;
import app.actionnation.actionapp.Database_Content.TeamGame;
import app.actionnation.actionapp.R;

public class DbHelperClass2 {


    String TAG = "DBHELPER CLASS2";

    public DbHelperClass2() {

    }

    public FirestoreRecyclerAdapter GetFireStoreAdapterSelectGame(com.google.firebase.firestore.Query query) {
        final FirestoreRecyclerOptions<TeamGame> options = new FirestoreRecyclerOptions.Builder<TeamGame>()
                .setQuery(query, TeamGame.class)
                .build();
        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<TeamGame, ActivityGameCreation.ViewHolderSelectGame>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ActivityGameCreation.ViewHolderSelectGame holder, int position, @NonNull TeamGame model) {
                //      holder.mIdSelectGame.setText(model.getGameName());
                holder.mIdGameName.setText(model.getGameName());
            }

            @NonNull
            @Override
            public ActivityGameCreation.ViewHolderSelectGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lf_gameselection, parent, false);
                return new ActivityGameCreation.ViewHolderSelectGame(view);
            }
        };
        return adapter;
    }


    public void updateFireUserData(String collectionReference, String documentId, ArrayList<String> dataVariables, ArrayList<Object> dataObjects, FirebaseFirestore db) {

        Map<String, Object> userVariable = new HashMap<>();

        int iterator = 0;
        for (String strVariable : dataVariables) {
            userVariable.put(strVariable, dataObjects.get(iterator));
            iterator = iterator + 1;
        }

        db.collection(collectionReference).document(documentId)
                .update(userVariable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void updateTeamGame(String collectionReference, String documentId, ArrayList<String> dataVariables, ArrayList<String> dataObjects, int teamCount, FirebaseFirestore db) {

        Map<String, Object> userVariable = new HashMap<>();
        userVariable.put(dataVariables.get(0), dataObjects);
        userVariable.put(dataVariables.get(1), teamCount);

        db.collection(collectionReference).document(documentId)
                .update(userVariable)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


    public void insertFireUserData(final DocumentReference documentReference, final Context ct, final Object dataObject) {


        documentReference.set(dataObject).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ct, "Insertion Done", Toast.LENGTH_LONG);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ct, "Insertion failure", Toast.LENGTH_LONG);
            }
        });
    }


}
